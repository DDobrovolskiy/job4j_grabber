package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.html.ParsePost;
import ru.job4j.html.SqlRuParse;
import ru.job4j.html.SqlRuParsePost;
import ru.job4j.properties.PropertyFactory;
import ru.job4j.utils.DateTimeParser;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {
    private static final Logger LOG = LoggerFactory.getLogger(Grabber.class.getName());
    private final Properties cfg = PropertyFactory.load("app.properties");

    public Store store() {
        return new PsqlStore(new PsqlProperties(cfg));
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("grabber.time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public void web(Store store) {
        LOG.debug("Запуск сервера");
        new Thread(() -> {
            try (ServerSocket server =
                         new ServerSocket(Integer.parseInt(cfg.getProperty("grabber.port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(),
                                    "WINDOWS-1251"))) {
                        LOG.debug("Загрузка на сервер");
                        out.write("HTTP/1.1 200 OK\r\n\r\n");
                        out.write("СПИСОК:");
                        out.write(System.lineSeparator());
                        out.flush();
                        int i = 0;
                        for (Post post : store.getAll()) {
                            LOG.debug("Запись: {} - {}", i++, post);
                            out.write(post.toString());
                            out.write(System.lineSeparator());
                        }
                        out.flush();
                        LOG.debug("Данные отправлены");
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            LOG.debug("Загрузка постов начата");
            parse.getLinks().forEach(link -> {
                LOG.debug("Загрузка сайта для парсинга {}", link);
                parse.list(link).forEach(store::save);
            });
            LOG.debug("Загрузка постов завершена");
        }
    }

    public static void main(String[] args) throws Exception {
        //Thread.sleep(5000);
        Grabber grab = new Grabber();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        //utils
        DateTimeParser dateTimeParser = new  SqlRuDateTimeParser();
        //html
        ParsePost parsePost = new SqlRuParsePost(dateTimeParser);
        Parse parse = new SqlRuParse(parsePost);
        //init
        grab.init(parse, store, scheduler);
        grab.web(store);
    }
}
