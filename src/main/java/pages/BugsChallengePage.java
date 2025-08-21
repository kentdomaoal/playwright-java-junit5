package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

public class BugsChallengePage extends BasePage {

    private final Page page;
    private final Locator firstNameTxtBox;
    private final Locator lastNameTxtBox;
    private final Locator phoneNumberTxtBox;
    private final Locator countryDropdown;
    private final Locator emailTxtBox;
    private final Locator passwordTxtBox;
    private final Locator agreeCheckBox;
    private final Locator registerBtn;
    private final Locator alertMessage;
    private final Locator firstNameDisplay;
    private final Locator lastNameDisplay;
    private final Locator phoneNumberDisplay;
    private final Locator countryDisplay;
    private final Locator emailDisplay;


    public BugsChallengePage(Page page) {
        super(page);
        this.page = page;
        this.firstNameTxtBox = page.getByPlaceholder("Enter first name");
        this.lastNameTxtBox = page.getByPlaceholder("Enter last name");
        this.phoneNumberTxtBox = page.getByPlaceholder("Enter phone number");
        this.countryDropdown =  page.locator("#countries_dropdown_menu");
        this.emailTxtBox = page.getByPlaceholder("Enter email");
        this.passwordTxtBox = page.getByPlaceholder("Password");
        this.agreeCheckBox = page.locator("#exampleCheck1");
        this.registerBtn = page.locator("#registerBtn");
        this.alertMessage = page.locator("#message");
        this.firstNameDisplay = page.locator("#resultFn");
        this.lastNameDisplay = page.locator("#resultLn");
        this.phoneNumberDisplay = page.locator("#resultPhone");
        this.countryDisplay = page.locator("#country");
        this.emailDisplay = page.locator("#resultEmail");
    }

    public void fillupForm(String firstName, String lastName, String phoneNumber,
                           String country, String email, String password){
        firstNameTxtBox.fill(firstName);
        lastNameTxtBox.fill(lastName);
        phoneNumberTxtBox.fill(phoneNumber);
        countryDropdown.selectOption(country);
        emailTxtBox.fill(email);
        passwordTxtBox.fill(password);
        if(agreeCheckBox.isEnabled()){
            agreeCheckBox.click();
        }
        registerBtn.click();
    }

    public void clearForm() {
        firstNameTxtBox.clear();
        lastNameTxtBox.clear();
        phoneNumberTxtBox.clear();
        countryDropdown.selectOption("Select a country...");
        emailTxtBox.clear();
        passwordTxtBox.clear();
        registerBtn.click();
    }

    public Boolean isValidPhoneNumberFormat(String phoneNumber) {
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        return patternMatches(phoneNumber, patterns);
    }

    public Boolean isValidEmailFormat(String emailAddress) {
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return patternMatches(emailAddress, pattern);
    }

    public static boolean patternMatches(String value, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }

    public Locator getAgreeCheckBox() {
        return agreeCheckBox;
    }

    public String getAlertMessage() {
        return alertMessage.textContent();
    }

    public String getFirstNameDisplay() {
        return cleanupDisplayedValue(firstNameDisplay);
    }

    public String getLastNameDisplay() {
        return cleanupDisplayedValue(lastNameDisplay);
    }

    public String getPhoneNumberDisplay() {
        return cleanupDisplayedValue(phoneNumberDisplay);
    }

    public String getCountryDisplay() {
        return cleanupDisplayedValue(countryDisplay);
    }

    public String getEmailDisplay() {
        return cleanupDisplayedValue(emailDisplay);
    }

    private String cleanupDisplayedValue(Locator locator){
        String originalString = locator.textContent();
        int colonIndex = locator.textContent().indexOf(":");
        return originalString.substring(colonIndex + 1).trim();
    }
}
