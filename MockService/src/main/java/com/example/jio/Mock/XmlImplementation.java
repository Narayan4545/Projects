package com.example.jio.Mock;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class XmlImplementation {

    private String id;
    private String loc;
    private String request;
    private Map<String,String> config1 = new HashMap<>();
    private String Url = "C:\\Users\\naray\\OneDrive\\Documents";
    private   Object obj;
    private Document doc;
    private Object receivedValue;

    @Autowired
    ConfigFile file;
    Logger log = LoggerFactory.getLogger(XmlImplementation.class);

    public String xmlMocK(String id, String loc, String request) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, ParseException, TransformerException {


         config1 = file.configtoHashMap(Url,id,loc);
        //Get value for XPath
        String Key = config1.get("XPath_To");

        //Create List of All values in JSONPath
        List<String> getKeys = Arrays.stream(Key.split(",")).collect(Collectors.toList());

        log.info("Key" + getKeys.toString());

        String test = request.toString();
        InputSource input = new InputSource(new StringReader(test));
        XPath xpath = XPathFactory.newInstance().newXPath();

      try {

          for (String key : getKeys) {

                receivedValue =  xpath.evaluate(key,input);
              log.info(String.valueOf(receivedValue));
              if (receivedValue != null && receivedValue != "") {

                  System.out.println(config1.get(receivedValue));

                  break;
              }
          }
      } catch (Exception e) {
          log.info("No Match Found for key|| Returning Default Response");

          doc = file.XmlParser().parse(Url+"\\"+id+"\\"+loc+"\\MockResponse\\"+config1.get("AnyResponse")+".xml");
          return WriteResponse(doc);
      }

        if(config1.get(receivedValue)!=null) {
            log.info("Return File Name" + config1.get(receivedValue));
            String test1 = config1.get(receivedValue);
            //Parse Json and Return
             doc = file.XmlParser().parse(Url+"\\"+id+"\\"+loc+"\\MockResponse\\"+test1+".xml");

        }
        else{
            log.info("No Match Found return Default Response");
             doc = file.XmlParser().parse(new File(Url+"\\"+id+"\\"+loc+"\\MockResponse\\"+config1.get("AnyResponse")+".xml"));

        }


        return WriteResponse(doc);
    }
    public String WriteResponse(Document doc) throws TransformerException {
        StringWriter write = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(write));
        String output = write.getBuffer().toString();
        System.out.println("output" + output);

        return output;
    }
}
