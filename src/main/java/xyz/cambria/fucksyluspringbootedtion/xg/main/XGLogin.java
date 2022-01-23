package xyz.cambria.fucksyluspringbootedtion.xg.main;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XGLogin {
    public static String login(String id , String pwd , HttpClientContext context , CloseableHttpClient httpClient) throws IOException {
        List<NameValuePair> list = new ArrayList<>();
        String page = EntityUtils.toString(httpClient.execute(new HttpGet("http://xg.sylu.edu.cn/SPCP/Web/")).getEntity());
        int reSubmiteFlag = page.indexOf("ReSubmiteFlag");
        list.add(new BasicNameValuePair("ReSubmiteFlag" , page.substring(reSubmiteFlag+36 , reSubmiteFlag+72)));

        list.add(new BasicNameValuePair("StuLoginMode" , "1"));
        list.add(new BasicNameValuePair("txtUid" , id));
        list.add(new BasicNameValuePair("txtPwd" , pwd));
//        list.add(new BasicNameValuePair("ReSubmiteFlag" , "a1e3a5aa-0ebb-4c5a-bf59-db6b65a294cd"));
        HttpPost httpPost = new HttpPost("http://xg.sylu.edu.cn/SPCP/Web/");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CloseableHttpResponse response = httpClient.execute(httpPost , context);

        if (EntityUtils.toString(response.getEntity()).contains("用户名或者密码错误")) {
            log.error("{} login failed" , id);
            return null;
        }

        Header[] headers = response.getHeaders("Set-Cookie");

        //System.out.println(EntityUtils.toString(response.getEntity()));

        String raw1 = headers[0].toString();
        String raw2 = headers[1].toString();

        String[] split1 = raw1.split(":|;");
        String[] split2 = raw2.split(":|;");

//        System.out.println(split[1].trim());

        split1[1] = split1[1].trim();
        split2[1] = split2[1].trim();

        log.info("{} login success with cookie {}" , id , split1[1] + "; " + split2[1]);

        return split1[1] + "; " + split2[1];

    }
}
