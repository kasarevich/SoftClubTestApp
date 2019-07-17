package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInPage {

    private final static String INITIAL_VIEW_ID = "initialView";
    private final static String ERROR_TEXT_WRONG_PAGE = "Wrong site page!";
    private final static String ERROR_TEXT_USERNAME_INPUT = "Cannot find username input field";
    private final static String ERROR_TEXT_PASSWORD_INPUT = "Cannot find password input field";
    private final static String ERROR_TEXT_NEXT_BUTTON = "Cannot find button \"Next\" ";
    private final static String ERROR_TEXT_LOGIN_BUTTON = "Cannot find button \"LogIn\"";

    @FindBy(id = INITIAL_VIEW_ID)
    private WebElement initialView;
    @FindBy(id = "identifierId")
    private WebElement usernameInputField;
    @FindBy(id = "identifierNext")
    private WebElement nextButton;
    @FindBy(name = "password")
    private WebElement passwordInputField;
    @FindBy(id = "passwordNext")
    private WebElement loginButton;
    @FindBy(id = "profileIdentifier")
    private WebElement profileButton;

    private WebDriverWait wait;
    private WebDriver driver;

    public LogInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
        if (!isItLoginPage()) {
            throw new IllegalStateException(ERROR_TEXT_WRONG_PAGE);
        }
    }

    /**
     * Признаком того, что контекст на странице авторизации принимаю
     * видимость контейнера для данных пользователя с id "initialView"
     * @return true, если элемент найден, Exception, если элемента нет
     */
    public boolean isItLoginPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(INITIAL_VIEW_ID)));
        return true;
    }
    public LogInPage setUsername(String username){
        waitForElement(usernameInputField,ERROR_TEXT_USERNAME_INPUT);
        usernameInputField.clear();
        usernameInputField.sendKeys(username);
        return this;
    }
    public LogInPage clickNext(){
        waitForElement(nextButton,ERROR_TEXT_NEXT_BUTTON);
        nextButton.click();
        return this;
    }
    public LogInPage setPassword(String password){
        waitForElement(passwordInputField,ERROR_TEXT_PASSWORD_INPUT);
        passwordInputField.clear();
        passwordInputField.sendKeys(password);
        return this;
    }
    public LogInPage clickLogin(){
        waitForElement(loginButton,ERROR_TEXT_LOGIN_BUTTON);
        loginButton.click();
        return this;
    }

    /**
     * Обрабатывается только позитивный сценарий с валидными данными
     * Если hasAccount() возвращает true, пропускается шаг ввода имени аккаунта
     * @param username
     * @param password
     * @return InboxPage - в случае успешной авторизации возвращается PageObject
     * траницы входящих сообщений
     */
    public InboxPage logInWithValidData(String username, String password){
        if(!hasAccount(username)){
            setUsername(username).clickNext();
        }
        setPassword(password).clickLogin();
        return PageFactory.initElements(driver, InboxPage.class);
    }

    /**
     * Если вход в аккаунт уже осуществлялся и если имя пользователя совпадает с искомым,
     * @return true
     * Не самый элегантный вариант, но сработало только это.
     */
    private boolean hasAccount(String username){
        String name;
        try{
           name = profileButton.getText();
        }catch (NoSuchElementException e){
            return false;
        }
        if(name.equalsIgnoreCase(username)){
            return true;
        }
        return false;
    }

    private void waitForElement(WebElement e, String message){
        wait.withMessage(message).until(ExpectedConditions.visibilityOf(e));
    }

}
