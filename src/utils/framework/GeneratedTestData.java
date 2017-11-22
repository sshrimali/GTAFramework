package utils.framework;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

/**
 * Created by sshrimali on 8/24/16.
 */
public class GeneratedTestData {
        String generatedTestDataLocation="work_area/GeneratedTestData.txt";
        public static Properties generatedTestData;
        public GeneratedTestData()
        {
                        try
                        {
                                generatedTestData = new Properties();
                                // Read Test data as property and pass to utilities
                                FileInputStream userDataFis = new FileInputStream(generatedTestDataLocation);
                                generatedTestData.load(userDataFis);
                                userDataFis.close();
                        }
                        catch (Exception e)
                        {
                                e.printStackTrace();
                        }

                }

        public String getData(String key)
        {
                if (generatedTestData.containsKey(key))
                {
                     return generatedTestData.getProperty(key);
                }
                else
                {
                        return null;
                }
        }
    public void writeData(String key,String value) throws Exception
    {
        FileWriter writer = new FileWriter(generatedTestDataLocation,true);
        writer.append("\n" + key + "=" + value + "\n");
        writer.close();
    }

}
