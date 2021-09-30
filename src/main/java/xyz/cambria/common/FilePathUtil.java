package xyz.cambria.common;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author Cambria
 * @creat 2021/9/30 22:37
 */
@Slf4j
public class FilePathUtil {

    @Getter
    @Setter
    private static String BJMFPath;

    @Getter
    @Setter
    private static String TemperaturePath;

    private static Properties properties;
    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BJMFPath = properties.getProperty("BJMFStuInfoSavePath");
        log.info("BJMF: Student Info Save Path:{}" , BJMFPath);

        TemperaturePath = properties.getProperty("XGStuInfoSavePath");
        log.info("XGTemperature: Student Info Save Path:{}" , TemperaturePath);
    }
}
