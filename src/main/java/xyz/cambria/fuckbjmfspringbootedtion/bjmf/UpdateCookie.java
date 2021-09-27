package xyz.cambria.fuckbjmfspringbootedtion.bjmf;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
public class UpdateCookie {

    private static Properties properties;
    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Student Info Save Path:{}" , properties.getProperty("stuInfoSavePath"));
    }

    public static void updateCookie() throws IOException {
        String FILE_PATH = properties.getProperty("stuInfoSavePath");

        /*Login.login("15804201356" , "");*/

        File[] files = new File(FILE_PATH).listFiles();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
//        System.out.println(FILE_PATH + " Found Files:");
        log.info("Run at {}" , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info("Found Files:");

        if (files == null)
            return;

        for (File file : files) {
            System.out.println(file.getName());
        }
        Properties properties = new Properties();

        for (File file : files) {
            properties.load(new FileReader(file));

            String cookie = properties.getProperty("cookie");
            List<BasicNameValuePair> payload = new ArrayList<>();

            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet get = new HttpGet("http://banjimofang.com/student");
            get.setHeader("Cookie" , cookie);
            CloseableHttpResponse response = client.execute(get);
            String updateCookie = response.getFirstHeader("Set-Cookie").toString().substring(11).trim();

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(updateCookie);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                log.error("File {} update failed" , file.getName());
                e.printStackTrace();
            }

            log.info("File {} update success" , file.getName());
        }
    }

}
