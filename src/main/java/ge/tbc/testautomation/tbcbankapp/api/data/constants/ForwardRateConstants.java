package ge.tbc.testautomation.tbcbankapp.api.data.constants;

public class ForwardRateConstants {

    public static class URI {
        public static final String BASE = "https://apigw.tbcbank.ge";
    }

    public static class Paths {
        public static final String BASE = "/api/v1";
    }

    public static class Endpoints {
        public static final String GET_FORWARD_RATES = "/forwardRates/getForwardRates";
    }

    public static class Params {
        public static final String LOCALE = "locale";
    }

    public static class Locales {
        public static final String KA_GE = "ka-GE";
        public static final String EN_US = "en-US";
    }

    public static class Currencies {
        public static final String EUR = "EUR";
        public static final String USD = "USD";
        public static final String GEL = "GEL";
    }

    public static class ExpectedPeriods {
        public static final int EXPECTED_PERIOD_COUNT = 9;
        public static final int[] EXPECTED_DAYS = {7, 14, 31, 60, 91, 181, 273, 365, 730};
    }

    public static class ExpectedRates {
        public static final double MIN_FORWARD_RATE = 1.0;
        public static final double MAX_FORWARD_RATE = 10.0;
        public static final double MIN_FORWARD_INTEREST = 0.0;
        public static final double MAX_FORWARD_INTEREST = 20.0;
        public static final double MIN_FORWARD_POINT = 0.0;
        public static final double MAX_FORWARD_POINT = 10000.0;
    }
}
