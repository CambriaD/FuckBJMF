package xyz.cambria.fucksyluspringbootedtion.xg.web.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import xyz.cambria.fucksyluspringbootedtion.xg.main.Punch;

import java.io.IOException;

@Configuration
@EnableScheduling
public class AutoTempSchedule {

    @Scheduled(cron = "0 5 12 * * 1-7")
//    @Scheduled(cron = "0/30 * * * * 1-7")
    private void punch() throws IOException {
        Punch.punch(null);
    }

}
