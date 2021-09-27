package xyz.cambria.fuckbjmfspringbootedtion.web.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
public class PunchSchedule {

//    @Scheduled(cron = "0/10 * * * * 1-7")
    @Scheduled(cron = "0 0/15 22 * * 1-7")
    private void punch() throws IOException {
        xyz.cambria.fuckbjmfspringbootedtion.bjmf.Main.main(null);
    }

    @Scheduled(cron = "0 0 * * * 1-7")
    private void updateCookie() throws IOException {
        xyz.cambria.fuckbjmfspringbootedtion.bjmf.UpdateCookie.updateCookie();
    }

}
