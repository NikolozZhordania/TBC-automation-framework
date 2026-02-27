package ge.tbc.testautomation.tbcbankapp.api.data.constants;

public class ExchangeRateConstants {

    public static class URI {
        public static final String BASE = "https://apigw.tbcbank.ge";
    }

    public static class Paths {
        public static final String BASE = "/api/v1";
    }

    public static class Endpoints {
        public static final String GET_EXCHANGE_RATE = "/exchangeRates/getExchangeRate";
    }

    public static class Params {
        public static final String ISO1 = "Iso1";
        public static final String ISO2 = "Iso2";
    }

    public static class CurrencyPairs {
        public static final String USD = "USD";
        public static final String GEL = "GEL";
        public static final String EUR = "EUR";
        public static final String GBP = "GBP";
        public static final String RUB = "RUB";
    }

    public static class InvalidCurrencies {
        public static final String INVALID_ISO1        = "XXX";
        public static final String INVALID_ISO2        = "ZZZ";
        public static final String INVALID_BOTH_ISO1   = "XXX";
        public static final String INVALID_BOTH_ISO2   = "YYY";
        public static final String NUMERIC_CODE        = "123";
        public static final String EMPTY_CODE          = "";
    }

    public static class LowercaseCurrencies {
        public static final String USD_LOWER = "usd";
        public static final String GEL_LOWER = "gel";
    }

    public static class ExpectedValues {
        public static final double USD_GEL_BUY_RATE_MIN     = 2.0;
        public static final double USD_GEL_BUY_RATE_MAX     = 5.0;
        public static final double USD_GEL_SELL_RATE_MIN    = 2.0;
        public static final double USD_GEL_SELL_RATE_MAX    = 5.0;
        public static final double EXPECTED_CURRENCY_WEIGHT = 1.0;
        public static final int    EXPECTED_CONVERSION_TYPE = 2;
        public static final long   MAX_RESPONSE_TIME_MS     = 2000L;
        public static final int    EXPECTED_STATUS_200      = 200;
        public static final int    EXPECTED_STATUS_400      = 400;
    }

    public static class SeoTitles {
        public static final String TREASURY_PRODUCTS_KA = "სახაზინო პროდუქტები";
    }
}