package ru.bot.valera.bot.service.handler.mining;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.crypta.MinerstateGPU;
import ru.bot.valera.bot.model.crypta.coins.DataProfit;
import ru.bot.valera.bot.model.crypta.coins.NoHashrateCoin;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.to.UpdateTO;
import ru.bot.valera.bot.util.JsonUtil;
import ru.bot.valera.bot.util.Util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import static ru.bot.valera.bot.model.Command.CALLBACK_MINING;

@Slf4j
@Component
@RequiredArgsConstructor
public class MiningCallBack extends AbstractContent implements Runnable {

    private static final String API_MINERSTATE_URI_GPU = "https://api.minerstat.com/v2/hardware?type=gpu";
    private static final String API_NO_HASHRATE_URI_ESTIMATES = "https://api.hashrate.no/v1/gpuEstimates?powerCost=0&apiKey=";
    private static final String API_NO_HASHRATE_URI_COINS = "https://api.hashrate.no/v1/coins?apiKey=";

    @Value("${tokens.hashrate_no}" )
    private String hashrateNoToken;

    List<MinerstateGPU> gpusMinerstate = new CopyOnWriteArrayList<>();
    List<NoHashrateCoin> noHashrateCoins = new CopyOnWriteArrayList<>();
    Map<String, DataProfit> dataProfits = new ConcurrentHashMap<>();

    @Override
    public Content handle(UpdateTO updateTO) {
        if (dataProfits.isEmpty() || noHashrateCoins.isEmpty()) {
            return getContent("не удается обработать запрос, обратитесь к разработчикам", updateTO);
        }

        String gpyType = updateTO.getCallBackData()[0];
        DataProfit dataProfit = dataProfits.get(gpyType);

        setPower(dataProfit, gpyType);

        return getContent(getFormatMessage(dataProfit, gpyType), updateTO);
    }

    private String getFormatMessage(DataProfit dataProfit, String gpyType) {
        return String.format("""
                        %s %s %s
                        %s %s %s
                        %s %s
                        %s %s %s
                        %s %s %s
                        %s %s
                        %s %s %s""",
                "на сегодня доход в сутки на ", gpyType, " без учета розетки такой\n",
                "текущий, макс.: ", Util.formatterBigDecimal(dataProfit.getProfit().getProfitUSD()), "$",
                "монета: ", dataProfit.getProfit().getCoin(),
                "расход ЭЭ: ", dataProfit.getProfit().getPower(), "\n\n",
                "суточный, макс.: ", Util.formatterBigDecimal(dataProfit.getProfit24().getProfitUSD24()), "$",
                "монета: ", dataProfit.getProfit24().getCoin(),
                "расход ЭЭ: ", dataProfit.getProfit24().getPower(), "\n" );
    }

    private void setPower(DataProfit dataProfit, String gpuType) {
        String profitCoin = dataProfit.getProfit().getCoin();
        String profit24Coin = dataProfit.getProfit24().getCoin();

        String algo = getAlgo(profitCoin);
        String algo24 = getAlgo(profit24Coin);

        dataProfit.getProfit().setPower(getPower(algo, gpuType));
        dataProfit.getProfit24().setPower(getPower(algo24, gpuType));
    }

    private String getAlgo(String coin) {
        return noHashrateCoins.stream()
                .filter(c -> c.getCoin().equals(coin))
                .findFirst()
                .map(NoHashrateCoin::getCoin)
                .orElse("" );
    }

    private String getPower(String algo, String gpuType) {
        MinerstateGPU minerstateGPU = gpusMinerstate.stream()
                .filter(g -> g.getName().toLowerCase().contains(gpuType))
                .findFirst()
                .orElse(new MinerstateGPU());

        if (minerstateGPU.getAlgorithms() != null && minerstateGPU.getAlgorithms().get(algo) != null) {
            return minerstateGPU.getAlgorithms().get(algo).getPower() * 24 / 1000 + "кВт";
        }
        return "не определено";
    }


    private double getProfit(double hashrate, double difficult, double blockReward) {
        return blockReward * hashrate * 3_600 / difficult;
    }

    @Override
    public void run() {
        while (true) {
            gpusMinerstate = JsonUtil.readValuesList(API_MINERSTATE_URI_GPU, new TypeReference<>() {
            });
            noHashrateCoins = JsonUtil.readValuesList(API_NO_HASHRATE_URI_COINS + hashrateNoToken, new TypeReference<>() {
            });
            dataProfits = JsonUtil.readValuesMap(API_NO_HASHRATE_URI_ESTIMATES + hashrateNoToken, new TypeReference<>() {
            });

            try {
                TimeUnit.SECONDS.sleep(50_400);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(CALLBACK_MINING);
    }
}
