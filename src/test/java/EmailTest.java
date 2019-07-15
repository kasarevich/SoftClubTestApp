import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InboxPage;
import pages.LogInPage;

public class EmailTest extends BaseTest {

    private static final String BASE_URL = "http://gmail.com";

    InboxPage inboxPage;

    @BeforeClass
    public void beforeClass(){
        driver.get(BASE_URL);
        inboxPage = PageFactory.initElements(driver, LogInPage.class).logInWithValidData("kasarevich1994@gmail.com","124");

    @Test
    public void testLogout(){
        inboxPage.writeMessage("kasarevich1994@gmail.com", "shfdajf", "HELLOOOOOOOOOOOOO");
        inboxPage.clickSendButton();
        Assert.assertTrue(inboxPage.checkForMessage("shfdajf")); //FIXME
    }

    @AfterClass
    public void afterClass(){
        inboxPage.logout();
    }
}
