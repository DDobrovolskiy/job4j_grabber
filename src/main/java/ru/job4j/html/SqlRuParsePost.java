package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.PsqlPost;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParsePost {
    private static final Logger LOG = LoggerFactory.getLogger(SqlRuParsePost.class.getName());
    private final String link;
    private final Element element;

    public SqlRuParsePost(String link) throws IOException {
        this.link = link;
        this.element = parsePost(link);
    }

    public Post get() {
        return new PsqlPost(parseName(), parseText(), link, parseDateTime());
    }

    private Element parsePost(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgTable");
        return row.get(0);
    }

    private String parseName() {
        try {
            return element.child(0).child(0).child(0).text(); //name
        } catch (Exception e) {
            LOG.warn("Расшифровка имени (parseName) не удалась", e);
            return null;
        }
    }

    private String parseText() {
        try {
            return element.child(0).child(1).child(1).text(); //text
        } catch (Exception e) {
            LOG.warn("Расшифровка текста (parseText) не удалась", e);
            return null;
        }
    }

    private LocalDateTime parseDateTime() {
        try {
            return new SqlRuDateTimeParser().parse(element.child(0).child(2).child(0).text());
        } catch (Exception e) {
            LOG.warn("Расшифровка вермени (parseDateTime) не удалась: ", e);
            return null;
        }
    }
}
