package ge.tbc.testautomation.tbcbankapp.api.data.constants;

public class PagesConstants {

    public static class URI {
        public static final String BASE = "https://apigw.tbcbank.ge";
    }

    public static class Paths {
        public static final String BASE = "/api/v1";
    }

    public static class Endpoints {
        public static final String PAGES = "/sites/pages/{pageId}";
    }

    public static class PathParams {
        public static final String PAGE_ID = "pageId";
    }

    public static class QueryParams {
        public static final String LOCALE = "locale";
        public static final String KA     = "ka-GE";
    }

    public static class PageIds {
        public static final String TREASURY_PRODUCTS = "7FGRRxeJzcfEVgTrT5ppxi";
        public static final String INVALID           = "invalid-page-xyz-404";
    }

    public static class SeoTitles {
        public static final String TREASURY_PRODUCTS_KA = "სახაზინო პროდუქტები";
    }

}