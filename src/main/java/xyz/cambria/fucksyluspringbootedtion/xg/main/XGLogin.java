package xyz.cambria.fucksyluspringbootedtion.xg.main;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XGLogin {
    public static String login(String id , String pwd) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("StuLoginMode" , "1"));
        list.add(new BasicNameValuePair("txtUid" , id));
        list.add(new BasicNameValuePair("txtPwd" , pwd));
        HttpPost httpPost = new HttpPost("http://xg.sylu.edu.cn/SPCP/Web/");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CloseableHttpResponse response = httpClient.execute(httpPost);

        if (EntityUtils.toString(response.getEntity()).contains("用户名或者密码错误")) {
            log.error("{} login failed" , id);
            return null;
        }

        Header[] headers = response.getHeaders("Set-Cookie");

        //System.out.println(EntityUtils.toString(response.getEntity()));

        String raw = headers[0].toString();

        String[] split = raw.split(":|;");

//        System.out.println(split[1].trim());

        log.info("{} login success" , id);
        return split[1].trim();
    }
}
