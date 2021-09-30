package xyz.cambria.fuckbjmfspringbootedtion.web.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import xyz.cambria.fuckbjmfspringbootedtion.bjmf.Punch;
import xyz.cambria.fuckbjmfspringbootedtion.bjmf.UpdateCookie;

import java.io.IOException;

@Configuration
@EnableScheduling
public class PunchSchedule {

//    @Scheduled(cron = "0/10 * * * * 1-7")
    @Scheduled(cron = "0 0,15,30,44 22 * * 1-7")
    private void punch() throws IOException {
        Punch.punch();
    }

    @Scheduled(cron = "0 50 21 * * 1-7")
    private void punch2() throws IOException {
        Punch.punch();
    }


    @Scheduled(cron = "0 0 * * * 1-7")
//    @Scheduled(cron = "0/30 * * * * 1-7")
    private void updateCookie() throws IOException {
        UpdateCookie.updateCookie();
    }

}
