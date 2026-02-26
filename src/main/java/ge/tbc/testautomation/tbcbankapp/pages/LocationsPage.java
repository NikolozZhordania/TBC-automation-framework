package ge.tbc.testautomation.tbcbankapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LocationsPage extends CommonPage {
    Page page;

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
        this.pageHeader = page.locator("h2");
        this.atmServiceButton = page.locator("//button[normalize-space()='ბანკომატები']");
        this.atmTitle = page.getByText("ATM - 24/7 ₾ $").first();
        this.cityDropdown = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("აირჩიე ქალაქი"));
        this.locationInput = page.locator("//input[contains(@placeholder,'ლოკაცია')]");
        this.atmListItems = page.getByText("ATM - 24/7 ₾ $");
        this.map = page.locator("google-map");
        this.activeMapMarker = this.map.locator("gmp-advanced-marker[tabindex='-1']");
        this.currentUserLocation = page.locator("//gmp-advanced-marker[contains(@class,'internal-visible')]");
    }

    public Locator cityOption(String city) {
        return page.getByRole(AriaRole.BUTTON).getByText(city);
    }

    public Locator atmOption(String atm){
        return page.getByText(atm);
    }

}