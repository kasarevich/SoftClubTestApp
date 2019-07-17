package util;
import entity.Message;
import entity.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DataHelper {

    private final static String PROPERTIES_FILENAME = "system.properties";
    private final static String QUERY_USERNAME = "login";
    private final static String QUERY_PASSWORD = "password";
    private final static String QUERY_RECIPIENT = "recipient";
    private final static String QUERY_SUBJECT = "subject";
    private final static String QUERY_MESSAGE = "message";
    private final static String QUERY_DRIVER_PROPERTY = "driver_property";
    private final static String QUERY_DRIVER_PATH = "driver_path";
    private final static String QUERY_DRIVER_CAPABILITY_KEY = "capability_key";
    private final static String QUERY_DRIVER_EMPTY_PAGE = "empty_start_page";
    private static final String QUERY_DRIVER_BASE_URL = "base_url";
    private static final String QUERY_DRIVER_FULLSCREEN = "full_screen";

    private static DataHelper instance;
    private DataHelper(){}
    public static DataHelper getInstance(){
        if(instance == null){
            instance = new DataHelper();
        }
        return instance;
    }
    private Properties properties = new Properties();

    public User getUser(){
     return new User(getProperty(QUERY_USERNAME),
             getProperty(QUERY_PASSWORD));
    }
    public Message getMessage(){
        return new Message(getProperty(QUERY_RECIPIENT), getProperty(QUERY_SUBJECT), getProperty(QUERY_MESSAGE));
    }

    public String getDriverProperty() {
        return getProperty(QUERY_DRIVER_PROPERTY);
    }

    public String getDriverPath() {
        return getProperty(QUERY_DRIVER_PATH);
    }

    public String getDriverEmptyPage() {
        return getProperty(QUERY_DRIVER_EMPTY_PAGE);
    }

    public String getCapabilityKey() {
        return getProperty(QUERY_DRIVER_CAPABILITY_KEY);
    }

    public String getBaseURL() {
        return getProperty(QUERY_DRIVER_BASE_URL);
    }

    public String getFullscreenArgument() {
        return getProperty(QUERY_DRIVER_FULLSCREEN);
    }

    private String getProperty(String param){
        try {
            properties.load(new FileInputStream(PROPERTIES_FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(param);
    }
}
