package entity;

public class Message {
    private String recipient;
    private String subject;
    private String messageText;

    public Message() {
    }

    public Message(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.messageText = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String message) {
        this.messageText = message;
    }
}
