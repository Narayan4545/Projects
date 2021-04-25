package com.example.jio.Mock;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@RestController

@RequestMapping("/mockService/v1/api")
public class Controller {

    @Autowired
    RestImplementation restMock;

    @Autowired
    XmlImplementation xmlImplementation;

    @PostMapping("mockJson/{id}/{loc}")
    public JSONObject MockJson(@PathVariable("id") String id, @PathVariable("loc") String loc, @RequestBody String request) throws JSONException, ParseException, IOException {

        return restMock.postMethod(id,loc,request);

        
    }

    @PostMapping(value = "mockXml/{id}/{loc}",consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
    public String MockXml(@PathVariable("id") String id, @PathVariable("loc") String loc, @RequestBody String request) throws ParseException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerException {

        return xmlImplementation.xmlMocK(id,loc,request);


    }



}
