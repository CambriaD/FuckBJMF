package xyz.cambria.fuckbjmfspringbootedtion.bjmf;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import xyz.cambria.common.FilePathUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class UpdateCookie {

    public static void updateCookie() throws IOException {
        String FILE_PATH = FilePathUtil.getBJMFPath();

        /*Login.login("15804201356" , "");*/

        File[] files = new File(FILE_PATH).listFiles();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
//        System.out.println(FILE_PATH + " Found Files:");
        log.info("Updating cookies at {}" , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
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
            CloseableHttpClient client = HttpClients.createDefault();
            String url = "http://banjimofang.com/student/course/" + GetClassId.getClassId(cookie) + "/punchs";
            HttpPost post = new HttpPost(url);
            post.setHeader("cookie" , cookie);
            CloseableHttpResponse response = client.execute(post);
            String updateCookie = response.getFirstHeader("Set-Cookie").toString().substring(11).trim().split(";")[0];

            try {
                log.debug("Class ID:{}" , GetClassId.getClassId(updateCookie));
                Integer.parseInt(GetClassId.getClassId(updateCookie));
            } catch (Exception e) {
                log.error("User {} Error:" , file.getName());
                e.printStackTrace();
            }

            String[] currentCookies = cookie.split(";");

            if (currentCookies.length == 2) {
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write("cookie=" + updateCookie + ";" + currentCookies[1]);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    log.error("File {} update failed", file.getName());
                    e.printStackTrace();
                }
            } else {
                //对于1.0.2版本存储cookie的兼容
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write("cookie=" + updateCookie);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    log.error("File {} update failed", file.getName());
                    e.printStackTrace();
                }
            }

            log.info("File {} update success" , file.getName());
            log.debug("Current cookie:{}" , updateCookie);
        }
    }

}
