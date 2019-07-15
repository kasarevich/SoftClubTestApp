import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private static final String DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String DRIVER_PATH = "C:/java_projects/tools/chromedriver_75/chromedriver.exe";
    private static final String CAPABILITY_KEY = "chrome.switches";
    private static final String EMPTY_START_PAGE = "--homepage=about:blank";

    protected static WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setUp(){
        System.setProperty(DRIVER_PROPERTY, DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CAPABILITY_KEY, EMPTY_START_PAGE);
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }
}