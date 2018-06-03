package com.qa.automation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utility {

    public String getPropeties(String key){

        Properties prop = new Properties();

        try {
            String filename = "config.properties";
            InputStream input ;
            input = Utility.class.getClassLoader().getResourceAsStream(filename);

            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("property file '" + filename + "' not found in the classpath");
            }


        } catch (IOException e){
            System.out.println(e.toString());
        }

        return prop.getProperty(key) ;

    }
}
