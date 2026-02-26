package ge.tbc.testautomation.tbcbankapp.pages;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CommonPage {

    public Locator forMeButton,
                    forMyBusinessButton,
                    tbcButton,
                    dropDownMenuContainer,
                    burgerMenu,
                    cookieAcceptButton;

    public CommonPage(Page page) {
        this.forMeButton = page.getByRole(AriaRole.BANNER)
                .getByRole(AriaRole.LINK,
                        new Locator.GetByRoleOptions().setName("ჩემთვის"));
        this.forMyBusinessButton = page.locator("//div[contains(@class,'navigation-item') and normalize-space(text())='ჩემი ბიზნესისთვის'");
        this.tbcButton = page.locator("//div[contains(@class,'navigation-item') and normalize-space(text())='თიბისი'");
        this.dropDownMenuContainer = page.locator("//div[contains(@class,'megaMenuBackgroundAnimation')]");
        this.burgerMenu = page.locator("//button[contains(@class,'hamburger-menu')]");
        this.cookieAcceptButton = page.locator("//tbcx-pw-cookie-consent//button[contains(text(),'თანხმობა')]");
    }
}
