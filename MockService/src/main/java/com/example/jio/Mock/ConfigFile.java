package com.example.jio.Mock;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Configuration
@EnableSwagger2
public class ConfigFile{

    @Bean
        public DocumentBuilder XmlParser() throws ParserConfigurationException {
        DocumentBuilderFactory db = DocumentBuilderFactory.newDefaultInstance();
        db.setValidating(false);
        db.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = db.newDocumentBuilder();
        return builder;
    }

    public HashMap<String,String> configtoHashMap(String Url,String Id,String loc) throws IOException {
         HashMap<String,String> config = new HashMap<>();
        Files.lines(Paths.get(Url + "\\" + Id + "\\" + loc + "\\" + loc + ".properties"))
                .filter(line -> line.contains("="))
                .forEach(x -> {
                    config.putIfAbsent(x.split("=")[0], x.split("=")[1]);
                });
        return config;
    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiinfo())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiinfo(){
        return new ApiInfoBuilder().title("Mock Service Api")
                .description("This Api can be used to Mock Soap and Json Services")
                .version("V_1.0.0").build();
    }
}