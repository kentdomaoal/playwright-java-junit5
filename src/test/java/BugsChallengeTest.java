import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.BugsChallengePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BugsChallengeTest extends TestFixtures {
    BugsChallengePage bugsChallengePage;

    @BeforeEach
    void navigateToBugsChallengePage(){
        bugsChallengePage = new BugsChallengePage(page);
        bugsChallengePage.clickLink("Spot the BUGS CHALLENGE");
    }

    @DisplayName("Validate All Fields are Existing")
    @ParameterizedTest(name = " - Validate if {1} Field is existing ")
    @CsvFileSource(resources = "/testdata/fieldName.csv", numLinesToSkip = 1)
    void validateAllFieldsAreExisting(int index, String fieldName){
        assertThat(page.locator("//label[contains(text(),'"+fieldName+"')]")).isVisible();
    }

    @DisplayName("Validate Field Names have Correct Spelling")
    @ParameterizedTest(name = " - Verify correct spelling of {1} Field ")
    @CsvFileSource(resources = "/testdata/fieldName.csv", numLinesToSkip = 1)
    void validateFieldNamesHaveCorrectSpelling(int index, String fieldName){
        Locator fieldNameLabel = page.locator("//label[not(contains(text(),'I agree'))]");

        SoftAssertions softAssertions = new SoftAssertions();
        String actualValue = fieldNameLabel.nth(index).textContent().replace("*","");
        softAssertions.assertThat(actualValue).isEqualTo(fieldName);
        softAssertions.assertAll();
    }

    @DisplayName("Validate Fields")
    @ParameterizedTest(name = " - Testing {0} ")
    @CsvFileSource(resources = "/testdata/registerForm.csv", numLinesToSkip = 1)
    void validateFields(String testName, String firstName, String lastName, String phoneNumber,
                                   String country, String email, String password, String expectedMessage) {
        bugsChallengePage.fillupForm(firstName, lastName, phoneNumber, country, email, password);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(bugsChallengePage.getAlertMessage()).isEqualTo(expectedMessage);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Validate Checkbox if Clickable")
    void validateCheckboxIfClickable(){
        assertThat(bugsChallengePage.getAgreeCheckBox()).not().isDisabled();
    }

    @DisplayName("Validate if inputted values displayed correctly after Register")
    @ParameterizedTest(name = "{displayName}")
    @CsvFileSource(resources = "/testdata/registerFormDisplayedValues.csv", numLinesToSkip = 1)
    void validateDisplayedValues(String testName, String firstName, String lastName, String phoneNumber,
                                  String country, String email, String password, String expectedMessage){

        bugsChallengePage.fillupForm(firstName, lastName, phoneNumber, country, email, password);
        assertAll(
                () -> assertEquals(expectedMessage, bugsChallengePage.getAlertMessage()),
                () -> assertEquals(firstName, bugsChallengePage.getFirstNameDisplay()),
                () -> assertEquals(lastName, bugsChallengePage.getLastNameDisplay()),
                () -> assertEquals(phoneNumber, bugsChallengePage.getPhoneNumberDisplay()),
                () -> assertEquals(country, bugsChallengePage.getCountryDisplay()),
                () -> assertEquals(email, bugsChallengePage.getEmailDisplay())
        );
    }

    @DisplayName("Validate if Navigation links are working and if the page is Properly Loaded ")
    @ParameterizedTest(name = " - Testing {0} link ")
    @CsvFileSource(resources = "/testdata/navigation.csv", numLinesToSkip = 1)
    void validateNavigationLinks(String linkName, String headerContent){
        bugsChallengePage.clickLink(linkName);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        assertThat(bugsChallengePage.getHeaderContent(headerContent)).isVisible();
    }

    @DisplayName("Validate if Already Registered user can no longer be Registered")
    @ParameterizedTest(name = "{displayName}")
    @CsvFileSource(resources = "/testdata/registerFormDisplayedValues.csv", numLinesToSkip = 1)
    void validateRegisteredUser(String testName, String firstName, String lastName, String phoneNumber,
                                String country, String email, String password, String expectedMessage) {

        bugsChallengePage.fillupForm(firstName, lastName, phoneNumber, country, email, password);
        bugsChallengePage.clearForm();
        assertEquals("The password should contain between [6,20] characters!", bugsChallengePage.getAlertMessage());

        // Fill up the form with the same values
        bugsChallengePage.fillupForm(firstName, lastName, phoneNumber, country, email, password);
        assertEquals("User Already Registered!", bugsChallengePage.getAlertMessage());
    }

}
