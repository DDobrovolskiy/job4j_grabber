package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.connect.ConnectSQL;

import java.sql.*;
import java.time.LocalDateTime;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbitNew {
    public static void main(String[] args) {
        try (Connection connection = new ConnectSQL().get(new RabbitProperties())) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10500);
            scheduler.shutdown();
            System.out.println("STOP");
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println("Rabbit");
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("LOAD TIME");
            Connection connection =
                    (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement =
                         connection.prepareStatement("insert into rabbit (created_date) values (?)")
            ) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

