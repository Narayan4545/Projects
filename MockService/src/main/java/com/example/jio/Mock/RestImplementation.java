package com.example.jio.Mock;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestImplementation {
    private String id;
    private String loc;
    private String request;
   private Map<String,String> config;
   private String Url = "C:\\Users\\naray\\OneDrive\\Documents\\";
   private   Object obj;
    Logger log = LoggerFactory.getLogger(RestImplementation.class);
    @Autowired
    ConfigFile file;


    public JSONObject postMethod(String id,String loc, String request )throws IOException, JSONException, ParseException {

        //Create Map
        config = new HashMap<>();
        String receivedValue = null;

        // Create Hashmap for Key Value pair in properties file separated by delimeter "="



        config = file.configtoHashMap(Url,id,loc);

        //Get value for JsonPath
        String Key = config.get("JPath_To");

        //Create List of All values in JSONPath
        List<String> getKeys = Arrays.stream(Key.split(",")).collect(Collectors.toList());

        log.info("Key" + getKeys.toString());

        //  Iterate to find given value
        try {
            for (String str : getKeys) {

                receivedValue = JsonPath.read(request, "$." + str);

                log.info(receivedValue);
                if (receivedValue != null && receivedValue != "") {

                    System.out.println(config.get(receivedValue));
                    break;
                }
            }
        } catch (Exception e) {

            log.info("No Match Found for key|| Returning Default Response");
            Object obj = new JSONParser().parse(new FileReader(Url+"\\"+id+"\\"+loc+"\\MockResponse\\"+config.get("AnyResponse")+".json"));

            JSONObject obj1 = (JSONObject) obj;

            return obj1;

        }

        if(config.get(receivedValue)!=null) {
            log.info("Return File Name" + config.get(receivedValue));
            //Parse Json and Return
            obj = new JSONParser().parse(new FileReader(Url+"\\" + id + "\\" + loc + "\\MockResponse\\" + config.get(receivedValue) + ".json"));
        }
        else{
            log.info("No Match Found return Default Response");
             obj = new JSONParser().parse(new FileReader(Url+"\\"+id+"\\"+loc+"\\MockResponse\\"+config.get("AnyResponse")+".json"));

        }
        JSONObject obj1 = (JSONObject) obj;

        return obj1;

    }


}