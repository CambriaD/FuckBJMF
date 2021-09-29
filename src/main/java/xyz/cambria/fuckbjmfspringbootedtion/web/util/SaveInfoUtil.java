package xyz.cambria.fuckbjmfspringbootedtion.web.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
@Data
@Slf4j
public class SaveInfoUtil implements Serializable {

    private String PATH;
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(properties.getProperty("stuInfoSavePath"));
        log.info("Students Info Save Path:{}" , properties.getProperty("stuInfoSavePath"));
    }

    public int saveInfo(String resp , String cookie , String fileName) throws IOException {
        PATH = properties.getProperty("stuInfoSavePath");

        if (fileName.isEmpty()) {
            return 2;
        }

        String temp = resp.split("/")[2];
        temp = temp.substring(0 , temp.length()-2);
        String loginUrl = "http://banjimofang.com/student/" + temp;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet login = new HttpGet(loginUrl);
        login.setHeader("Cookie" , cookie);
        CloseableHttpResponse response = client.execute(login);
        String loginCookie = response.getFirstHeader("Set-Cookie").toString().substring(11).trim();

        /*Header[] headers = response.getHeaders("Set-Cookie");
        StringBuilder loginCookieBuilder = new StringBuilder();
        for (Header header : headers) {
            loginCookieBuilder.append(header.toString().substring(11).trim().split(";")[0]).append(";");
        }*/
        
        String filePayload = new StringBuilder().append("cookie=").append(loginCookie).toString();

        File file = new File(PATH + fileName + ".properties");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(filePayload);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

//        System.out.println("File " + fileName + " Saved");
        log.info("File {} Saved" , fileName);

        return 1;
    }
}
