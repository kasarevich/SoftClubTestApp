package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DataHelper;


public class InboxPage {

    private final static String URL_SUFFIX = "#inbox";
    private final static String LAST_LOADED_ELEMENT_ID = "gb";
    private final static String ERROR_TEXT_WRONG_PAGE = "Wrong site page!";
    private final static String ERROR_TEXT_CANNOT_LOAD = "Cannot load email page";
    private final static String ERROR_TEXT_COMPOSE_BUTTON ="Cannot find compose button";
    private final static String ERROR_TEXT_PROFILE_BUTTON = "Cannot find profile button";
    private final static String ERROR_TEXT_LOGOUT_BUTTON = "Cannot find logout button";
    private final static String ERROR_TEXT_RECIPIENT_INPUT = "Cannot find recipient input field";
    private final static String ERROR_TEXT_SUBJECT_INPUT = "Cannot find subject input field";
    private final static String ERROR_TEXT_MESSAGE_INPUT = "Cannot find message input field";
    private final static String ERROR_TEXT_SEND_BUTTON = "Button \"Send\" is not clickable";


    @FindBy(xpath = "//*[@id='gb']/div[2]/div[3]/div/div[2]/div/a")
    private WebElement profileButton;
    @FindBy(id = "gb_71")
    private WebElement logoutButton;
    @FindBy(xpath = "//*[@role='button' and contains (@gh, 'cm')]")
    private WebElement composeButton;
    @FindBy(name = "to")
    private WebElement recipientInput;
    @FindBy(name = "subjectbox")
    private WebElement subjectInput;
    @FindBy(xpath = "//table[@class='iN']//div[@role='textbox']")
    private WebElement messagetInput;
    @FindBy(xpath = "//table[@class='iN']//div[@role='button' and contains (text(), 'Отправить')]")
    private WebElement sendButton;


    private WebDriverWait wait;
    private WebDriver driver;

    public InboxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        if(!isItInboxPage()){
            throw new IllegalStateException(ERROR_TEXT_WRONG_PAGE);
        }
    }

    /**
     * Признаком того, что контекст на странице ходящих сообщений принимаю
     * видимость одного из немногих элементов со статическим id,
     * а также то, что URL оканчивается на "#inbox"
     */
    public boolean isItInboxPage() {
        wait.withMessage(ERROR_TEXT_CANNOT_LOAD).until(ExpectedConditions.visibilityOfElementLocated(By.id(LAST_LOADED_ELEMENT_ID)));
        return driver.getCurrentUrl().endsWith(URL_SUFFIX);
    }


    public InboxPage clickCompose(){
        waitForElement(composeButton, ERROR_TEXT_COMPOSE_BUTTON);
        composeButton.click();
        return this;
    }

    public InboxPage clickProfileButton(){
        waitForElement(profileButton,ERROR_TEXT_PROFILE_BUTTON);
        profileButton.click();
        return this;
    }
    public InboxPage clickLogoutButton(){
        waitForElement(logoutButton, ERROR_TEXT_LOGOUT_BUTTON);
        logoutButton.click();
        return this;
    }

    public InboxPage writeRecipient(String recipient){
        waitForElement(recipientInput,ERROR_TEXT_RECIPIENT_INPUT);
        recipientInput.clear();
        recipientInput.sendKeys(recipient);
        return this;
    }

    public InboxPage writeSubject(String subject){
        waitForElement(subjectInput, ERROR_TEXT_SUBJECT_INPUT);
        subjectInput.clear();
        subjectInput.sendKeys(subject);
        return this;
    }

    public InboxPage writeMessage(String message){
        waitForElement(messagetInput,ERROR_TEXT_MESSAGE_INPUT);
        messagetInput.clear();
        messagetInput.sendKeys(message);
        return this;
    }

    /**
     * Нажимается кнопка профиля, чтобы сделать видимой кнопку logout
     * @return PageObject страницы авторизации, либо Exception по таймауту ожидания элемента
     */
    public LogInPage logout(){
        clickProfileButton()
                .clickLogoutButton();
        return PageFactory.initElements(driver, LogInPage.class);
    }

    private void waitForElement(WebElement e, String message){
        wait.withMessage(message).until(ExpectedConditions.visibilityOf(e));
    }
    public InboxPage clickSendButton(){
        wait.withMessage(ERROR_TEXT_SEND_BUTTON)
                .until(ExpectedConditions.elementToBeClickable(sendButton));
        sendButton.click();
        return this;
    }

    /**
     * Нажимается кнопка "Написать" и заполняются необходимые поля
     * @param recipient
     * @param subject
     * @param message
     */
    public void writeMessage(String recipient, String subject, String message){
        clickCompose();
        writeRecipient(recipient)
                .writeSubject(subject)
                .writeMessage(message);
    }

    /**
     * Критерием того, что сообщеение получено, принял факт того, что в течении таймаута
     * в строке с последним принятым сообщением появится элемент с полным совпадением текста сообщения.
     * Иначе Exception по таймауту с сообщением о том, что элемент не найден.
     */
    public boolean isMessageDelivered(String message){
          wait.withMessage("Message with text \"" + message +"\" is not found")
                  .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[1]/td/div/div/span[text()='" + message + "']")));
          return true;
    }
}

