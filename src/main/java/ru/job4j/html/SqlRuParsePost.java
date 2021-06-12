package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.Post;
import ru.job4j.utils.DateTimeParser;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParsePost implements Post {
    private static final Logger LOG = LoggerFactory.getLogger(SqlRuParsePost.class.getName());
    private String name;
    private String text;
    private String link;
    private LocalDateTime localDateTime;
    private DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
    private Element element;

    public SqlRuParsePost(String link) throws IOException {
        this.link = link;
        this.element = parsePost();
        this.name = parseName(element);
        this.text = parseText(element);
        this.localDateTime = parseDateTime(element);
    }

    public SqlRuParsePost(String link, String name, String dataTime) throws IOException {
        this.link = link;
        this.name = name;
        dateTimeParser = new SqlRuDateTimeParser();
        this.localDateTime = dateTimeParser.parse(dataTime);
        this.element = parsePost();
        this.text = parseText(element);
    }

    private Element parsePost() throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgTable");
        return row.get(0);
    }

    private String parseName(Element element) {
        try {
            return element.child(0).child(0).child(0).text(); //name
        } catch (Exception e) {
            LOG.warn("Расшифровка имени (parseName) не удалась", e);
            return null;
        }
    }

    private String parseText(Element element) {
        try {
            return element.child(0).child(1).child(1).text(); //text
        } catch (Exception e) {
            LOG.warn("Расшифровка текста (parseText) не удалась", e);
            return null;
        }
    }

    private LocalDateTime parseDateTime(Element element) {
        try {
            return dateTimeParser.parse(element.child(0).child(2).child(0).text());
        } catch (Exception e) {
            LOG.warn("Расшифровка вермени (parseDateTime) не удалась: ", e);
            return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return "SqlRuParsePost{"
                + "name='" + name + '\''
                + ", text='" + text + '\''
                + ", link='" + link + '\''
                + ", localDateTime=" + localDateTime
                + '}';
    }
}
