package Utility;

import java.util.Properties;

/**
 * Created by utkarshc on 18/4/16.
 */
public class PropertiesGetter {
    private static final Properties properties;

    static {
        String propertyFiles[] = new String[]{"cron"};
        properties = new Properties();
        try {
            properties.load(PropertiesGetter.class.getResourceAsStream("/application.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

    public static Properties getProperties(){
        return properties;
    }
}
