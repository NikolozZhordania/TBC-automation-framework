package ge.tbc.testautomation.tbcbankapp.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LocationsPage extends CommonPage {

    private final Page page;

    public Locator
            pageHeader,
            atmServiceButton,
            atmTitle,
            cityDropdown,
            locationInput,
            atmListItems,
            map,
            activeMapMarker,
            currentUserLocation;

    public LocationsPage(Page page) {
        super(page);
        this.page = page;

        this.pageHeader = page.locator("h2").first();

        this.atmServiceButton = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("ბანკომატები"))
                .first();

        this.atmTitle = page.locator("//div")
                .filter(new Locator.FilterOptions().setHasText("ATM"))
                .first();

        this.cityDropdown = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("აირჩიე ქალაქი"));

        this.locationInput = page.locator("input[placeholder*='ლოკაცია']").first();

        this.atmListItems = page.locator("app-atm-branches-section-list-item");

        this.map = page.locator("google-map");

        this.activeMapMarker = this.map.locator("gmp-advanced-marker[tabindex='-1']");

        this.currentUserLocation = page.locator("gmp-advanced-marker.internal-visible");

    }

    public Locator cityOption(String city) {
        return page.getByRole(AriaRole.BUTTON).getByText(city);
    }

    public Locator atmOption(String atmName) {
        return atmListItems.filter(new Locator.FilterOptions().setHasText(atmName)).first();
    }
}