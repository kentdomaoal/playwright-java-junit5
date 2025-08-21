import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class TestFixtures {

    protected Properties properties;

    Playwright playwright;
    Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    void launchBrowser() {
        initializeBrowser();
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close(); // shuts down playwright engine
    }

    @BeforeEach
    void createBrowserContext() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        page = context.newPage();

        properties = initializeConfigProperties();
        page.navigate(properties.getProperty("BASE_URL").trim());
    }

    @AfterEach
    void closeBrowserContext(TestInfo testInfo) {
        String traceName = testInfo.getTestClass().get().getSimpleName() +
                "-" + testInfo.getTestMethod().get().getName() + "-trace.zip";
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/traces/" + traceName)));

        page.close();
        context.close();
    }

    public void initializeBrowser() throws IllegalArgumentException {
        if(System.getProperty("browser") == null) {
            System.setProperty("browser", "chrome");
        }

        if(System.getProperty("headless") == null) {
            System.setProperty("headless", "false");
        }

        String browserName = System.getProperty("browser");
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless"));

        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(isHeadless));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            default:
                throw new IllegalArgumentException("Please provide a valid browser name (chrome, firefox, webkit or chromium).");
        }
    }

    public Properties initializeConfigProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String configUrl = classLoader.getResource("config/config.properties").getPath();
            FileInputStream fileInputStream = new FileInputStream(configUrl);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}