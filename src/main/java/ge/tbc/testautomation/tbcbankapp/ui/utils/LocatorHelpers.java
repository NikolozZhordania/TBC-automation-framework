package ge.tbc.testautomation.tbcbankapp.ui.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LocatorHelpers {
    public static Locator getValueByLabelAndSymbol(Page page, String labelText, String symbol) {
        return page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(labelText))
                .locator("xpath=following-sibling::div[contains(text(),'" + symbol + "')]")
                .first();
    }

    public static Locator cityOption(Page page, String city) {
        return page.getByRole(AriaRole.BUTTON).getByText(city);
    }

    public static Locator atmOption(Locator atmListItems, String atmName) {
        return atmListItems.filter(new Locator.FilterOptions().setHasText(atmName)).first();
    }
}
