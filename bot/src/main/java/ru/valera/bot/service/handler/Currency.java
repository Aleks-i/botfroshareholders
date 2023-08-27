package ru.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.Content;
import ru.valera.bot.model.Currencyes;
import ru.valera.bot.model.crypta.coinmarketcap.CryptoCurrencies;
import ru.valera.bot.model.crypta.coinmarketcap.CryptoCurrencyUSD;
import ru.valera.bot.model.crypta.coinmarketcap.FiatRub;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.valera.bot.util.JsonUtil.readValue;
import static ru.valera.bot.util.MessageUtil.getContent;
import static ru.valera.bot.util.Util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Currency implements Handler, Runnable {

    private static final long WAIT_DELAY = 560_000;

    public static CryptoCurrencies coinMarketCapCryptoCurrencyModel = new CryptoCurrencies();
    public static FiatRub fiatToRub = new FiatRub();
    public static CryptoCurrencyUSD cryptoCurrencyToUSD = new CryptoCurrencyUSD();

    private static final String COINMARKETCAP_CRYPTO_CURRENCY_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=5000&CMC_PRO_API_KEY=";
    private static final String COINMARKETCAP_FIAT_CONVERT_RUB_URL = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?id=1,2781,2787,2790&convert=RUB&CMC_PRO_API_KEY=";
    private static final String COINMARKETCAP_CRYPTOCURRENCY_USD_URL = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?id=1&CMC_PRO_API_KEY=";

    @Value("${tokens.coinmarketcap_token_api}")
    private String currencyToken;

    @Override
    public Content handle(UpdateTO updateTO) {
        return getContent(getCurrencyExchangeRate(), updateTO);
    }

    private String getCurrencyExchangeRate() {

        FiatRub.Coin coinEuro = fiatToRub.data.get(ID_EUR_COINMARKETCAP);
        FiatRub.Coin coinUSD = fiatToRub.data.get(ID_USD_COINMARKETCAP);
        FiatRub.Coin coinYUAN = fiatToRub.data.get(ID_YUAN_COINMARKETCAP);
        CryptoCurrencyUSD.Coin coinBTC = cryptoCurrencyToUSD.data.get(ID_BTC_COINMARKETCAP);

        Currencyes currencyesModel = new Currencyes();
        currencyesModel.setEuro(coinEuro.symbol, formatterBigDecimal(coinEuro.quote.rub.price));
        currencyesModel.setUSD(coinUSD.symbol, formatterBigDecimal(coinUSD.quote.rub.price));
        currencyesModel.setYUAN(coinYUAN.symbol, formatterBigDecimal(coinYUAN.quote.rub.price));
        currencyesModel.setBTC(coinBTC.symbol, formatterBigDecimal(coinBTC.quote.usd.price), formatterBigDecimal(coinBTC.quote.usd.volume_24h));

        return currencyesModel.toString();
    }

    @Override
    public void run() {
        while (true) {
            fiatToRub = readValue(COINMARKETCAP_FIAT_CONVERT_RUB_URL + currencyToken, FiatRub.class);
            cryptoCurrencyToUSD = readValue(COINMARKETCAP_CRYPTOCURRENCY_USD_URL + currencyToken, CryptoCurrencyUSD.class);

            try {
                Thread.sleep(WAIT_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.CURRENCY);
    }
}
