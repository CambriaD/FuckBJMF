package xyz.cambria.fuckbjmfspringbootedtion.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cambria.fuckbjmfspringbootedtion.web.util.SaveInfoUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Author Cambria
 * @creat 2021/9/22 22:30
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    SaveInfoUtil util;

    @GetMapping({"bjmfWeb/loginQRCode"})
    public void loginQRCode(HttpServletResponse response , HttpServletRequest request) throws IOException {
//        ImageIO.write(, "png", response.getOutputStream());

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet preConfigGet = new HttpGet("http://banjimofang.com/student/login?ref=%2Fstudent");
        HttpGet get = new HttpGet("http://banjimofang.com/weixin/qrlogin/student");
        HttpGet firstCheckLogin = new HttpGet("http://banjimofang.com/weixin/qrlogin/student?op=checklogin");

        CloseableHttpResponse preConfigResp = client.execute(preConfigGet);

        String cookie = preConfigResp.getFirstHeader("Set-Cookie").toString().substring(11).trim();

        get.setHeader("Cookie" , cookie);
        firstCheckLogin.setHeader("Cookie" , cookie);
        CloseableHttpResponse resp = client.execute(get);

        String respBody = EntityUtils.toString(resp.getEntity());
        //System.out.println(respBody);
        respBody = respBody.substring(respBody.indexOf("https://mp.weixin.qq.com/cgi-bin/showqrcode"));
        String qRCodePath = respBody.substring(0 , respBody.indexOf("\""));

        //System.out.println(qRCodePath);

//        response.sendRedirect(qRCodePath);
        /*ServletOutputStream outputStream = response.getOutputStream();
        resp.getEntity().getContent();
        outputStream.flush();
        outputStream.close();*/

        String p1 = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <img src=\"";
        String p2 = "\"/>\n" +
                "    <form action=\"saveInfo\" method=\"post\">\n" +
                "        <input type=\"text\" name=\"fileName\" placeholder=\"Your Name in Pinyin\">\n" +
                "        <input type=\"submit\" value=\"Submit\">\n" +
                "    </form>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        PrintWriter printWriter = response.getWriter();
        printWriter.println(p1 + qRCodePath + p2);

        //System.out.println(cookie);
        request.getSession().setAttribute("cookie" , cookie);

        client.execute(firstCheckLogin);
    }

    @PostMapping({"bjmfWeb/saveInfo"})
    public void saveInfo(String fileName , HttpServletRequest request , HttpServletResponse response) throws IOException, InterruptedException {

        String cookie = (String) request.getSession().getAttribute("cookie");

        if (cookie.isEmpty()) {
            return;
        }

        CloseableHttpClient cli = HttpClients.createDefault();
        for (int i = 0; i < 11; i++) {
            HttpGet loginget = new HttpGet("http://banjimofang.com/weixin/qrlogin/student?op=checklogin");
            loginget.setHeader("Cookie" , cookie);

            CloseableHttpResponse loginStatus = cli.execute(loginget);

            String stat = EntityUtils.toString(loginStatus.getEntity());
//            System.out.println("Response Info:\n" + loginStatus.getFirstHeader("Set-Cookie").toString() + stat);
            log.info("Response Info:\n\t{}\n\t{}" , loginStatus.getFirstHeader("Set-Cookie").toString() , stat);
            if (stat.contains("false")) {
                log.info("Failed, Current cookie:{}" , cookie);
//                System.out.println(String.valueOf(i) + "\tfalse");
                log.info("Failed {} time(s)" , String.valueOf(i));

                cookie = loginStatus.getFirstHeader("Set-Cookie").toString().substring(11).trim();
                TimeUnit.MILLISECONDS.sleep(2000L);
            } else {

                int rtn = util.saveInfo(stat, cookie, fileName);

                response.setCharacterEncoding("GBK");
                switch (rtn) {
                    case 1 :
                        response.getWriter().write("登录成功，21:45-22:45每15分钟执行一次签到，结果请15分钟后查看");
                        break;
                    case 2 :
                        response.getWriter().write("姓名不能为空");
                        break;
                    default:
                        response.getWriter().write("出大问题，服务炸了，回首页联系作者解决");
                }

                break;
            }
        }
    }

}
//Cambria   yxktmf=5o7u41wPl91IZ1x6h47idEVGXe3lBwzpYr2FVajb
//lzw       yxktmf=sJ0lwKkFqpJNa80HbW7WlVk3sGhGQYMqZfdlyoBp