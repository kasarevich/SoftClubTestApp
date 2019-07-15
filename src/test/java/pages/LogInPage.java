package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;

public class LogInPage {

    private final static String START_TITLE = "Gmail";

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
    private final WebDriver driver;

    public LogInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
        if (!isItLoginPage()) {
            throw new IllegalStateException("Wrong site page!");
        }
    }
    public boolean isItLoginPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view_container")));
        return driver.getTitle().equals(START_TITLE);
    }

    public LogInPage setUsername(String username){
        usernameInputField.clear();
        usernameInputField.sendKeys(username);
        return this;
    }
    public LogInPage clickNext(){
        nextButton.click();
        return this;
    }
    public LogInPage setPassword(String password){
        passwordInputField.clear();
        passwordInputField.sendKeys(password);
        return this;
    }
    public LogInPage clickLogin(){
        loginButton.click();
        return this;
    }

    // только позитивный сценарий, при неверных данных перегруженный метод будет возвращать LoginPage
    public InboxPage logInWithValidData(String username, String password){
        if(!hasAccount(username)){                                                              //если аккаунта нет, вводится логин, если введен, проскакивает этот блок
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
            setUsername(username).clickNext();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        setPassword(password).clickLogin();
        return PageFactory.initElements(driver, InboxPage.class);
    }


    private boolean hasAccount(String username){
        String name;
        try{
           name = profileButton.getText();
        }catch (NoSuchElementException e){
            return false;
        }
        if(name.equalsIgnoreCase(username)){
            return true;
        }else {
            return false;
        }
    }


}
