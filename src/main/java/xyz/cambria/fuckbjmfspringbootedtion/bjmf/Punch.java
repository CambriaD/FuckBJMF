package xyz.cambria.fuckbjmfspringbootedtion.bjmf;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import xyz.cambria.common.FilePathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @Author Cambria
 * @creat 2021/9/22 7:55
 */
@Slf4j
public class Punch {

    public static void punch() throws IOException {
        String FILE_PATH = FilePathUtil.getBJMFPath();

        /*Login.login("15804201356" , "");*/

        File[] files = new File(FILE_PATH).listFiles();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
//        System.out.println(FILE_PATH + " Found Files:");
        log.info("Punching at {}" , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info("Found Files:");

        if (files == null)
            return;

        for (File file : files) {
            System.out.println(file.getName());
        }
        Properties properties = new Properties();

        for (File file : files) {
                properties.load(new FileReader(file));

                String cookie = properties.getProperty("cookie");
                List<BasicNameValuePair> payload = new ArrayList<>();

                String id = GetPunchId.getPunchId(cookie);
                String lat = "41.731407";
                String lng = "123.50237";
                String acc = "15.0";
                String gps_addr = "辽宁省沈阳市浑南区汇泉东路";
                String res = "";

                payload.add(new BasicNameValuePair("id" , id));
                payload.add(new BasicNameValuePair("lat" , lat));
                payload.add(new BasicNameValuePair("lng" , lng));
                payload.add(new BasicNameValuePair("acc" , acc));
                payload.add(new BasicNameValuePair("gps_addr" , gps_addr));
                payload.add(new BasicNameValuePair("res" , res));

            try {
                Integer.parseInt(GetClassId.getClassId(cookie));
            } catch (NumberFormatException e) {
                log.error("{} punch Failed: Get ClassID Failed" , file.getName());

                continue;
            }

            String url = new StringBuilder().append("http://banjimofang.com/student/course/").append(GetClassId.getClassId(cookie)).append("/punchs").toString();
                HttpPost post = new HttpPost(url);

                CloseableHttpClient client = HttpClients.createDefault();

                post.setHeader("Cookie" , cookie);
                post.setEntity(new UrlEncodedFormEntity(payload));

                CloseableHttpResponse response = client.execute(post);

            String result = EntityUtils.toString(response.getEntity());

            log.debug("Current cookie:{}" , cookie);

            if (result.contains("成功")) {
                log.info("{} punch success" , file.getName());
            } else if (result.contains("已签到")){
                log.info("{} punch success" , file.getName());
            } else {
                log.warn("{} punch Failed" , file.getName());
                log.error(result);
            }

        }
    }

}
