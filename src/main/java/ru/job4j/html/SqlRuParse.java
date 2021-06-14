package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class SqlRuParse implements Parse {
    private static final Logger LOG = LoggerFactory.getLogger(SqlRuParse.class.getName());
    private final ParsePost parsePost;
    private final String url = "https://www.sql.ru/forum/job-offers";

    public SqlRuParse(ParsePost parsePost) {
        this.parsePost = parsePost;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new LinkedList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                posts.add(parsePost.get(href.attr("href")));
            }
            return posts;
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки постов (list): {}", link, e);
            return posts;
        }
    }

    @Override
    public Post detail(String link) {
        try {
            return parsePost.get(link);
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки поста (detail): {}", link, e);
            return null;
        }
    }

    @Override
    public List<String> getLinks() {
        List<String> rsl = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            rsl.add(url + "/" + i);
        }
        return rsl;
    }
}
