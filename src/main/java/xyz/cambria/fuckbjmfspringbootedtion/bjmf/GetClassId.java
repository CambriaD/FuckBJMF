package xyz.cambria.fuckbjmfspringbootedtion.bjmf;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GetClassId {
    public static String getClassId(String cookie) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://banjimofang.com/student/");
        get.setHeader("cookie" , cookie);
        CloseableHttpResponse response = client.execute(get);

        String resp = EntityUtils.toString(response.getEntity());

        int idIndex = resp.indexOf("<a href=\"/student/course/");
        String id = resp.substring(idIndex+25);
        id = id.substring(0 , id.indexOf("\""));

        return id;
    }
}
