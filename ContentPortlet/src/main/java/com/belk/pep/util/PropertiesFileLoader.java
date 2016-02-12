package com.belk.pep.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesFileLoader {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(PropertiesFileLoader.class.getName());
    /**
     * Instance variable to hold the configuration loader instance.
     */
    private volatile static PropertiesFileLoader instance = null; 
    
   
        
    /**
     * This method returns an instance of ConfigurationLoader.
     *
     * @return ConfigurationLoader: The instance of configuration loader.
     */
    public static PropertiesFileLoader getInstance()
    {      
                if (instance == null)
                {
                    instance = new PropertiesFileLoader();
                }
           // }
        return instance;
    }
    /**
     * 
     * @param file
     * @return
     */
    public  Properties getConnectionProperties(String file)    {            
        LOGGER.info("file--->"+file);
        Properties prop = null;
        InputStream inputStream = null;
        if(prop==null){
            prop = new Properties();        
            try
            {
                inputStream = new FileInputStream(new File(file));
                prop.load(inputStream);
            }
            catch (FileNotFoundException e)
            {
                LOGGER.severe("Inside FileNotFoundException");
            }
            catch (IOException e)
            {
                LOGGER.severe("Inside IOException");
            }     
          
          if(inputStream != null) {
              try {
                  inputStream.close();
              } catch (IOException e) {}
          }
            
        }
        return prop;
    }   
    
    public static Properties getPropertyLoader(String fileName){   
        Properties properties = new Properties();
        FileInputStream fileInput = null;
        InputStream input = null;
        try {
            
            input =PropertiesFileLoader.class.getClassLoader().getResourceAsStream(fileName);           

            LOGGER.info("getPropertyLoader");            
            properties.load(input);
            LOGGER.info("properties"+properties);        

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(fileInput!=null){
                try {
                    fileInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

}
