package utils.framework;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import static utils.framework.GeneratedTestData.generatedTestData;

/**
 * Created by sshrimali on 8/24/16.
 */
public class TestDataReader {
    private final Properties suiteProperties;
    public String testcaseId;


    public TestDataReader(Properties suiteProperties,String testcaseId)
    {
        this.testcaseId=testcaseId;
        this.suiteProperties = suiteProperties;
    }

    public String getData(String key)
    {
        if (key.startsWith("_"))
        {
            key=key.substring(1);
        }
        String value = null;
        if (suiteProperties.containsKey(testcaseId + "_" + key))
        {
            value = suiteProperties.getProperty(testcaseId + "_" + key);
            if (generatedTestData.containsKey(value))
            {
                value = generatedTestData.getProperty(value);
            }
            value=dataSmartizer(value);
        }
        else if(suiteProperties.containsKey("defaultTestdata_" + key))
        {
            value = suiteProperties.getProperty("defaultTestdata_" + key);
        }
        return value;
    }
    public int getInt(String key)
    {
        int value;
        String data=getData(key);
        if (data != null) {
            value = Integer.valueOf(data);
        }
        else {
            value=0;
        }
        return value;
    }
    private String dataSmartizer(String data)
    {
        if (data.contains("_CURRENT_YEAR_"))
        {
            data = data.replace("_CURRENT_YEAR_", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
         }
         return data;
    }
    public Boolean containsKey(String key)
    {
        if (key.startsWith("_"))
        {
            key=key.substring(1);
        }

        if (suiteProperties.containsKey(testcaseId + "_" + key))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getData(String key, String defaultValue) {
        String value=getData(key);
        if (value == null)
        {
            value=defaultValue;
        }
            return value;
    }
    public List<String> getDataList(String data)
    {
        List<String> listOfData = null;
        for (int itertor = 0;itertor< 100 ;itertor++)
        {

            if (getData(itertor + "_" + data) != null) {
                listOfData.add(getData(itertor + "_" + data));
            }
            else
            {
                break;
            }
        }
        return listOfData;
    }
}
