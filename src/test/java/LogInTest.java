import entity.User;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.InboxPage;
import pages.LogInPage;
import util.DataHelper;

/**
 1. Открыть страницу gmail.com, заполнить поле логин, продолжить.
 2. Заполнить поле пароль, продолжить.
 3. Дождаться пока выполнится авторизация и проверить, что Вы находитесь на главной странице почтового сервиса.
 */
public class LogInTest extends BaseTest {

    private InboxPage inboxPage;
    private User user;

    @Test
    public void testAuth(){
        user = DataHelper.getInstance().getUser();
        driver.get(DataHelper.getInstance().getBaseURL());
        LogInPage logInPage = PageFactory.initElements(driver, LogInPage.class);
        inboxPage = logInPage.logInWithValidData(user.getUsername(), user.getPassword());
        Assert.assertTrue(inboxPage.isItInboxPage());
    }

    @AfterClass
    public void afterClass(){
      inboxPage.logout();
    }
}
