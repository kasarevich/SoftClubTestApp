import entity.User;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InboxPage;
import pages.LogInPage;
import util.DataHelper;

public class LogOutTest extends BaseTest {

    /**
     Предусловие
     Вы авторизованы в почтовом сервисе.
     1. Осуществить выход из почтового сервиса, проверить что Вы на странице авторизации.
     */

    private InboxPage inboxPage;
    private User user;

    @BeforeClass
    public void beforeClass() {
        user = DataHelper.getInstance().getUser();
        driver.get(DataHelper.getInstance().getBaseURL());
        inboxPage = PageFactory.initElements(driver, LogInPage.class).logInWithValidData(user.getUsername(), user.getPassword());
    }
    @Test
    public void testLogout(){
        Assert.assertTrue(inboxPage.logout().isItLoginPage());
    }

}
