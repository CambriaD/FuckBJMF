package xyz.cambria.fucksyluspringbootedtion.xg.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cambria.fucksyluspringbootedtion.xg.web.util.SaveStuInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Cambria
 * @creat 2021/8/1 12:34
 */
@RestController
@Slf4j
public class TemperatureController {

    @Autowired
    SaveStuInfo util;

    @PostMapping(value = "FuckSYLU/XGWeb/uploadStuInfo")
    public void uploadStuInfo(String StuID, String pwd, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("GBK");
//        System.out.println(StuID);

        if (util.saveStuInfo(StuID, pwd)!=1) {
            response.getWriter().write("信息上传失败，请联系作者解决");
            return;
        }
//        System.out.println("Done");
        log.info("Stu {} has saved info." , StuID);
        response.getWriter().write("记录上传成功，这玩意每天12:05自动执行一次，填报体温36.0，时间0:00 8:00 16:00");
        return;
    }

}
