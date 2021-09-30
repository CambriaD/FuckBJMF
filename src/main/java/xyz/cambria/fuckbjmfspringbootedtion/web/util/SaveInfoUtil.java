package xyz.cambria.fuckbjmfspringbootedtion.web.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import xyz.cambria.common.FilePathUtil;

import java.io.*;

@Component
@Data
@Slf4j
public class SaveInfoUtil implements Serializable {

    private String PATH;

    public int saveInfo(String resp , String cookie , String fileName) throws IOException {
        PATH = FilePathUtil.getBJMFPath();

        if (fileName.isEmpty()) {
            return 2;
        }

        String temp = resp.split("/")[2];
        temp = temp.substring(0 , temp.length()-2);
        String loginUrl = "http://banjimofang.com/student/" + temp;

//        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setRedirectsEnabled(false).build()).build();
        HttpGet login = new HttpGet(loginUrl);
        login.setProtocolVersion(HttpVersion.HTTP_1_1);
        login.setHeader(HttpHeaders.REFERER , "http://banjimofang.com/weixin/qrlogin/student");
        login.setHeader(HttpHeaders.USER_AGENT , "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.31");
        login.setHeader(HttpHeaders.HOST , "banjimofang.com");
        login.setHeader("Proxy-Connection" , "keep-alive");
        login.setHeader("Upgrade-Insecure-Requests" , "1");
        login.setHeader("Cookie" , cookie);
        login.setHeader("DNT" , "1");
        CloseableHttpResponse response = client.execute(login);
//        String loginCookie = response.getFirstHeader("Set-Cookie").toString().substring(11).trim();

        Header[] headers = response.getHeaders("Set-Cookie");
        StringBuilder loginCookieBuilder = new StringBuilder();
        for (Header header : headers) {
            loginCookieBuilder.append(header.toString().substring(11).trim().split(";")[0]).append(";");
        }
        
        String filePayload = "cookie=" + loginCookieBuilder.substring(0, loginCookieBuilder.length()-1);

        log.info("{} login with cookie:{}" , fileName , filePayload);

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
