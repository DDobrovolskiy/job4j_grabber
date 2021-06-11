package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.IPost;
import ru.job4j.grabber.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class SqlRuParse implements Parse {
    private static final Logger LOG = LoggerFactory.getLogger(SqlRuParsePost.class.getName());
    private static String url = "https://www.sql.ru/forum/job-offers";

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url).append("/").append(i);
            System.out.println(stringBuilder);
            Document doc = Jsoup.connect(stringBuilder.toString()).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                IPost post = new SqlRuParsePost(href.attr("href"));
                System.out.println(post.getName());
                System.out.println(post.getText());
                System.out.println(post.getLink());
                System.out.println(post.getLocalDateTime());
            }
        }
    }

    @Override
    public List<IPost> list(String link) {
        List<IPost> posts = new LinkedList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                posts.add(new SqlRuParsePost(href.attr("href")));
            }
            return posts;
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки постов (list): {}", link, e);
            return posts;
        }
    }

    @Override
    public IPost detail(String link) {
        try {
            return new SqlRuParsePost(link);
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки поста (detail): {}", link, e);
            return null;
        }
    }
}
