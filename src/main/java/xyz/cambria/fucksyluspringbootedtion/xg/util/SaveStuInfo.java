package xyz.cambria.fucksyluspringbootedtion.xg.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.cambria.common.FilePathUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author Cambria
 * @creat 2021/8/1 13:05
 */
@Component
@Slf4j
public class SaveStuInfo {
    private String PATH;

    public int saveStuInfo(String StuID , String pwd) {
        PATH = FilePathUtil.getTemperaturePath();
        String info = "id=" + StuID + "\npwd=" + pwd;
        String fileName = PATH + StuID + ".properties";
//        System.out.println(fileName + " Created");
        log.info("{} Created." , fileName);
        try {
            new File(fileName).createNewFile();
            FileWriter fw = new FileWriter(fileName);
            fw.write(info);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            log.error("File {} Creat Failed." , fileName);
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
