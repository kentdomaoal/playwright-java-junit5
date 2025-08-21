package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BasePage {

    private final Page page;
    private final Locator homeLnk;
    private final Locator contactLnk;

    public BasePage(Page page) {
        this.page = page;
        this.homeLnk = page.locator("a#home");
        this.contactLnk = page.locator("a#contact");
    }

    public void clickLink(String linkName) {
        this.page.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions().setHasText(linkName))
                .click();
    }

    public Locator getHeaderContent(String headerContent) {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(headerContent));
    }

}
