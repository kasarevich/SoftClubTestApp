import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.InboxPage;
import pages.LogInPage;

public class LogInTest extends BaseTest {

    private static final String BASE_URL = "http://gmail.com";
    InboxPage inboxPage;

    @Test
    public void testAuth(){
        driver.get(BASE_URL);
        LogInPage logInPage = PageFactory.initElements(driver, LogInPage.class);
        inboxPage = logInPage.logInWithValidData("kasarevich1994@gmail.com", "123");
        Assert.assertTrue(inboxPage.isItInboxPage());
    }

    @AfterClass
    public void afterClass(){
      inboxPage.logout();
    }
}
