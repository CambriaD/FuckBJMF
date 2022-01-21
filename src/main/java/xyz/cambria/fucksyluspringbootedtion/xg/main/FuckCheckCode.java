package xyz.cambria.fucksyluspringbootedtion.xg.main;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Base64;

@Slf4j
public class FuckCheckCode {

/*    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("StuInfos/Cambria.properties"));

        String cookie = XGLogin.login(properties.getProperty("id"), properties.getProperty("pwd"));

        HttpGet post = new HttpGet("http://xg.sylu.edu.cn/SPCP/Web/Report/GetLoginVCode");
        post.setHeader("Cookie" , cookie);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);

        byte[] imgBytes = StreamUtils.copyToByteArray(response.getEntity().getContent());
        FileOutputStream out = new FileOutputStream("test.jpg");
        out.write(imgBytes);
        out.close();

        String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
        System.out.println(imgBase64);
        System.out.println(new String(Base64.getEncoder().encode(imgBytes) , StandardCharsets.US_ASCII));

//        imgBase64 = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAdADwDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiudk8WpHftaHR9RLC5ltt4aDaWSPzM/wCtyAy9MgH1AouB0VFUFmm1PQvPtxPYz3Fvuj3hDJCzLxkfMuRx6iuL0PV9V1PUoLVta1Dc0VjO26G3A+aN2lT/AFeedq56EFhjHNO2tg6XPQ6KrahfRaZp1xezhzHChcqgyzY7Aep6VSsNR1Oa+Fvf6M1pG6NJHMlwsqgZGEfAG1+SSBleOGbsgNaiuf8AFlxqFtY2r2WoCxhku4YLiVYBJKqySKgKFsqpyQCWVuD2rlvEPirxd4S1IadBpsevwmMSx3krrDJgkja4UBSwIPKhRgjjIJKcopXk0l5lRhKTtFXZ6TXm3i7Tbxtdu5LOJiyPb3UbBsDzJv8ARXz64jDH8RXpNFPqmTcRVCKFUYAGAK5bSPB40vWYb/zy+x7ttu48iV1KDGMHaqke2a6qijrcOliG7ihns5oriBbiF0KvCyhhIMcqQeDnpzXH6PPAdcsv7CbxEIJSxvItRhuzAI9jFWVrgfKwbaAI2wQTkEAFe2ooWjB7WMbxXa3V74U1O3sYXmvHgb7OiMqkyDlOWIAw2DnPasC/0bxprcyXRvtJ0fEYT7MkbXme+4ufLwecYAI46nPHcUVMoRmrSV0VGUo6xdmf/9k=";

        HttpPost fuckckcd = new HttpPost("http://192.168.1.2:9898/ocr/b64/text");
//        fuckckcd.setHeader("Content-Type" , "application/json");
//        fuckckcd.setEntity(new StringEntity("{\"images\":[\"" + imgBase64 + "\"]}" , "UTF-8"));
        fuckckcd.setEntity(new ByteArrayEntity(Base64.getEncoder().encode(imgBytes)));

        CloseableHttpResponse resp = client.execute(fuckckcd);
        String result = EntityUtils.toString(resp.getEntity());

//        result = result.substring(result.indexOf("\"text\":\"") + 8);

        System.out.println(result);
    }*/

    public static String getCheckcode(String cookie , Long timestamp) {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet getCheckcode = new HttpGet("http://xg.sylu.edu.cn/SPCP/Web/Report/GetLoginVCode?dt=" + timestamp);
        getCheckcode.setHeader("Cookie" , cookie);

        CloseableHttpResponse checkcodeResp = null;
        try {
            checkcodeResp = client.execute(getCheckcode);
        } catch (IOException e) {
            log.error("Get Checkcode Failed.");
            return null;
        }
        try {
            byte[] bytes = StreamUtils.copyToByteArray(checkcodeResp.getEntity().getContent());
            HttpPost ddddocrReq = new HttpPost("http://192.168.1.2:9898/ocr/b64/text");
            ddddocrReq.setEntity(new ByteArrayEntity(Base64.getEncoder().encode(bytes)));
            CloseableHttpResponse ddddocrResp = client.execute(ddddocrReq);
            return EntityUtils.toString(ddddocrResp.getEntity());
        } catch (IOException e) {
            log.error("Cast Image Error.");
            return null;
        }
    }

}
