package ru.bot.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.Currencyes;
import ru.bot.valera.bot.model.crypta.coinmarketcap.CryptoCurrencies;
import ru.bot.valera.bot.model.crypta.coinmarketcap.CryptoCurrencyUSD;
import ru.bot.valera.bot.model.crypta.coinmarketcap.FiatRub;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static ru.bot.valera.bot.util.JsonUtil.readValue;
import static ru.bot.valera.bot.util.Util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Currency extends AbstractContent implements Runnable {

    public static CryptoCurrencies coinMarketCapCryptoCurrencyModel = new CryptoCurrencies();
    public static FiatRub fiatToRub = new FiatRub();
    public static CryptoCurrencyUSD cryptoCurrencyToUSD = new CryptoCurrencyUSD();

    private static final String COINMARKETCAP_CRYPTO_CURRENCY_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=5000&CMC_PRO_API_KEY=";
    private static final String COINMARKETCAP_FIAT_CONVERT_RUB_URL = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?id=1,2781,2787,2790&convert=RUB&CMC_PRO_API_KEY=";
    private static final String COINMARKETCAP_CRYPTOCURRENCY_USD_URL = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?id=1&CMC_PRO_API_KEY=";

    @Value("${tokens.coinmarketcap_token_api}" )
    private String currencyToken;

    @Override
    public Content handle(UpdateTO updateTO) {
        String messageText = getCurrencyExchangeRate();
        if (updateTO.getMessageId() == 0) {
            return getContent("Курсы основных валют: \\n\\n" + getCurrencyExchangeRate(), updateTO);
        }
        return getContent(getCurrencyExchangeRate(), updateTO);
    }

    private String getCurrencyExchangeRate() {

        FiatRub.Coin coinEuro = fiatToRub.getData().get(ID_EUR_COINMARKETCAP);
        FiatRub.Coin coinUSD = fiatToRub.getData().get(ID_USD_COINMARKETCAP);
        FiatRub.Coin coinYUAN = fiatToRub.getData().get(ID_YUAN_COINMARKETCAP);
        CryptoCurrencyUSD.Coin coinBTC = cryptoCurrencyToUSD.getData().get(ID_BTC_COINMARKETCAP);

        Currencyes currencyesModel = new Currencyes();
        currencyesModel.setEuro(coinEuro.getSymbol(), formatterBigDecimal(coinEuro.getQuote().getRub().getPrice()));
        currencyesModel.setUSD(coinUSD.getSymbol(), formatterBigDecimal(coinUSD.getQuote().getRub().getPrice()));
        currencyesModel.setYUAN(coinYUAN.getSymbol(), formatterBigDecimal(coinYUAN.getQuote().getRub().getPrice()));
        currencyesModel.setBTC(coinBTC.getSymbol(), formatterBigDecimal(coinBTC.getQuote().getUsd().getPrice()),
                formatterBigDecimal(coinBTC.getQuote().getUsd().getVolume_24h()));

        return currencyesModel.toString();
    }

    @Override
    public void run() {
        while (true) {
            fiatToRub = readValue(COINMARKETCAP_FIAT_CONVERT_RUB_URL + currencyToken, FiatRub.class);
            cryptoCurrencyToUSD = readValue(COINMARKETCAP_CRYPTOCURRENCY_USD_URL + currencyToken, CryptoCurrencyUSD.class);

            try {
                TimeUnit.SECONDS.sleep(560);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.CURRENCY);
    }
}
