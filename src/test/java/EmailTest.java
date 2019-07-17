import entity.Message;
import entity.User;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InboxPage;
import pages.LogInPage;
import util.DataHelper;

/**
 Предусловие
 Вы авторизованы в почтовом сервисе.

 1. Написать сообщение (самому себе)
 2. Заполнить обязательные поля (кому, тема, текст)
 3. Отправить сообщение.
 4. Дождаться отображения письма в Вашем почтовом сервисе, проверить что письмо получено.

 Постусловие
 Осуществить выход из почтового сервиса.
 */

public class EmailTest extends BaseTest {

    private InboxPage inboxPage;
    private Message message;
    private User user;

    @BeforeClass
    public void beforeClass() {
        user = DataHelper.getInstance().getUser();
        message = DataHelper.getInstance().getMessage();
        driver.get(DataHelper.getInstance().getBaseURL());
        inboxPage = PageFactory.initElements(driver, LogInPage.class).logInWithValidData(user.getUsername(), user.getPassword());
    }
    @Test
    public void testMessage(){
        inboxPage.writeMessage(message.getRecipient(), message.getSubject(), message.getMessageText());
        inboxPage.clickSendButton();
        Assert.assertTrue(inboxPage.isMessageDelivered(message.getMessageText()));
    }

    @AfterClass
    public void afterClass(){
        inboxPage.logout();
    }
}
