package xyz.cambria.fucksyluspringbootedtion.xg.main;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import xyz.cambria.common.FilePathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @Author Cambria
 * @creat 2021/7/30 11:28
 */
@Slf4j
public class Punch {

    public static void punch() throws IOException {
        String FILE_PATH = FilePathUtil.getTemperaturePath();
        File[] files = new File(FILE_PATH).listFiles();
        log.info("Upload InfoCollect Form at {}" , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info("Found Files:");
        if (files == null)
            return;

        for (File file : files) {
            log.info("{}" , file.getName());
        }
        Properties properties = new Properties();

        for (File file : files) {

            properties.load(new FileReader(file));

            RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
            HttpClientContext context = HttpClientContext.create();
            CookieStore cookieStore = new BasicCookieStore();
            context.setCookieStore(cookieStore);
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                    .setDefaultCookieStore(cookieStore).build();

            String cookie = XGLogin.login(properties.getProperty("id"), properties.getProperty("pwd") , context , httpClient);

            if (cookie == null) {
                properties.clear();
                continue;
            }

            if (properties.getProperty("IDNum") == null) {
                log.info("no userinfo for infocollect.");
                properties.clear();
                continue;
            }
            try {
                boolean result = InfoCollect.run(properties, GetInfoCollectForm.run(context , httpClient) , context , httpClient);
                log.info("Info Collect {}" , result?"success":"failed");
                while (!result) {
                    log.warn("Retrying.");
                    Thread.sleep(1000L);
                    result = InfoCollect.run(properties, GetInfoCollectForm.run(context , httpClient) , context , httpClient);
                }
                properties.clear();
            } catch (Exception e) {
                e.printStackTrace();
                properties.clear();
                log.error("{} get infocollect form failed." , file.getName());
            }

        }
    }

    public static void temp() throws IOException {
        String FILE_PATH = FilePathUtil.getTemperaturePath();
        File[] files = new File(FILE_PATH).listFiles();
        log.info("Upload Temperature info at {}" , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info("Found Files:");
        if (files == null)
            return;

        for (File file : files) {
//            System.out.println(file.getName());
            log.info("{}" , file.getName());
        }
        Properties properties = new Properties();

        for (File file : files) {

            properties.load(new FileReader(file));

            RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
            HttpClientContext context = HttpClientContext.create();
            CookieStore cookieStore = new BasicCookieStore();
            context.setCookieStore(cookieStore);
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                    .setDefaultCookieStore(cookieStore).build();

            String cookie = XGLogin.login(properties.getProperty("id"), properties.getProperty("pwd") , context , httpClient);

            if (cookie == null) {
                properties.clear();
                continue;
            }
             try {
                 Temperature.run("5" , null , cookie);
                 Temperature.run("5" , null , cookie);
                 Temperature.run("5" , null , cookie);
                 log.info("{} upload temperatrue success" , file.getName());

             } catch (Exception e) {
                 log.error("{} upload temperatrue Failed." , file.getName());
                 properties.clear();
                 e.printStackTrace();
             }
        }
    }

}
