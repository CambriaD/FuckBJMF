package xyz.cambria.fuckbjmfspringbootedtion.bjmf;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author Cambria
 * @creat 2021/9/22 21:53
 */
public class GetPunchId {

    static String getPunchId(String cookie) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = new StringBuilder().append("http://banjimofang.com/student/course/").append(GetClassId.getClassId(cookie)).append("/punchs").toString();
        HttpPost post = new HttpPost(url);
        post.setHeader("cookie" , cookie);
        CloseableHttpResponse response = client.execute(post);

        String resp = EntityUtils.toString(response.getEntity());

        int idIndex = resp.indexOf("id=\"punchcard_");
        String id = resp.substring(idIndex+14 , idIndex+20);

        return id;
    }

}
