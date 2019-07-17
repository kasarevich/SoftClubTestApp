import entity.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import util.DataHelper;

public class BaseTest {

    public static WebDriver driver;

    @BeforeSuite
    public void setUp(){
        System.setProperty(DataHelper.getInstance().getDriverProperty(), DataHelper.getInstance().getDriverPath());
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(DataHelper.getInstance().getCapabilityKey(), DataHelper.getInstance().getDriverEmptyPage());
        chromeOptions.addArguments(DataHelper.getInstance().getFullscreenArgument());
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterSuite
    public void tearDown() {
        driver.close();
    }
}