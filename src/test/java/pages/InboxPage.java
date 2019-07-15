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

public class InboxPage {

    private final static String URL_SUFFIX = "#inbox";


    @FindBy(xpath = "//*[@id=\"gb\"]/div[2]/div[3]/div/div[2]/div/a")
    private WebElement profileButton;
    @FindBy(id = "gb_71")
    private WebElement logoutButton;
    @FindBy(xpath = "//*[@role='button' and contains (text(), 'Compose')]")
    private WebElement composeButton;
    @FindBy(name = "to")
    private WebElement recipientInput;
    @FindBy(name = "subjectbox")
    private WebElement subjectInput;
    @FindBy(xpath = "//*[@aria-label=\"Message Body\" and contains (@role, \"textbox\")]")
    private WebElement messagetInput;
    @FindBy(xpath = "//*[@role='button' and contains (text(), 'Send')]")
    private WebElement sendButton;
    @FindBy(xpath = "//*[@class='UI']//table/tbody/tr[1]")
    private WebElement lastMessageButton;
    @FindBy(xpath = "//h2[@class='hP']")
    private WebElement lastMessageSubject;


    private WebDriverWait wait;
    private final WebDriver driver;

    public InboxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        if(!isItInboxPage()){
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean isItInboxPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gb")));
        return driver.getCurrentUrl().endsWith(URL_SUFFIX);
    }

    public LogInPage logout(){
        profileButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gb_71")));
        logoutButton.click();
        return PageFactory.initElements(driver, LogInPage.class);
    }

    public InboxPage clickCompose(){
        composeButton.click();
        return this;
    }

    public InboxPage writeRecipient(String recipient){
        recipientInput.clear();
        recipientInput.sendKeys(recipient);
        return this;
    }

    public InboxPage writeSubject(String subject){
        subjectInput.clear();
        subjectInput.sendKeys(subject);
        return this;
    }

    public InboxPage writeMessage(String message){
        messagetInput.clear();
        messagetInput.sendKeys(message);
        return this;
    }

    public InboxPage clickSendButton(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@role='button' and contains (text(), 'Send')]")));
        sendButton.click();
        return this;
    }

    public InboxPage clickLastMessageButton(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='UI']//table/tbody/tr[1]")));
        lastMessageButton.click();
        return this;
    }

    public String getSubject(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='hP']")));
        return lastMessageSubject.getText();
    }

    public void writeMessage(String recipient, String subject, String message){
        clickCompose();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("to")));
        writeRecipient(recipient)
                .writeSubject(subject)
                .writeMessage(message);
    }

    public boolean checkForMessage(String subject){
        driver.navigate().refresh();
        return subject.equalsIgnoreCase(clickLastMessageButton().getSubject());
    }

}

