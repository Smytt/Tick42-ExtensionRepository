package com.tick42.quicksilver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping()
    public void test() {
        HttpClient client = HttpClientBuilder.create().build();
        HttpHead request = new HttpHead("https://api.github.com/repos/eugenp/REST-With-Spring/pulls?per_page=1");
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            Header[] headers = response.getHeaders("link");

            if (headers.length == 0) {
                System.out.println(0);
            }

            else {
                String value = headers[0].getElements()[1].getValue();
                Pattern pattern = Pattern.compile("&page=(\\d+)>");
                Matcher matcher = pattern.matcher(value);
                while (matcher.find()) {
                    System.out.println(matcher.group(1));
                }
            }
//            if (entity != null) {
//                try (InputStream stream = entity.getContent()) {
//                    BufferedReader reader =
//                            new BufferedReader(new InputStreamReader(stream));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line);
////                        List<String> test = om.readValue(line, ArrayList.class);
////                        System.out.println(test.size());
//                    }
//                }
//            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
