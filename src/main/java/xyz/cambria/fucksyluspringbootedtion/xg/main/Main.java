package xyz.cambria.fucksyluspringbootedtion.xg.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import xyz.cambria.common.FilePathUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @Author Cambria
 * @creat 2021/7/30 11:28
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        String FILE_PATH = FilePathUtil.getTemperaturePath();
        File[] files = new File(FILE_PATH).listFiles();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
//        System.out.println(FILE_PATH + " Found Files:");
        log.info("Upload Temperature info at {}" , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info("Found Files:");
        if (files == null)
            return;

        for (File file : files) {
            System.out.println(file.getName());
        }
        Properties properties = new Properties();

        for (File file : files) {
            try {
                properties.load(new FileReader(file));

//                System.out.println(properties.getProperty("id"));

                String cookie = XGLogin.login(properties.getProperty("id"), properties.getProperty("pwd"));

                if (cookie == null) {
                    continue;
                }

                Temperature.run("0" , null , cookie);
                Temperature.run("8" , null , cookie);
                Temperature.run("16" , null , cookie);
                log.info("{} upload temperatrue success" , file.getName());
                /*try {
                    InfoCollect.run(cookie , properties , GetInfoCollectForm.run(cookie));
                } catch (Exception e) {
                    System.out.println("没抓到健康状况表单信息,timestamp:" + System.currentTimeMillis());
                }*/
            } catch (Exception e) {
                log.error("{} upload temperatrue Failed." , file.getName());
                e.printStackTrace();
            }

        }
    }

}
