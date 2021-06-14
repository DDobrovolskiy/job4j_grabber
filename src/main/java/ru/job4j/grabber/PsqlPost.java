package ru.job4j.grabber;

import java.time.LocalDateTime;

public class PsqlPost implements Post {
    private int id;
    private String name;
    private String text;
    private String link;
    private LocalDateTime localDateTime;

    public PsqlPost(String name, String text, String link, LocalDateTime localDateTime) {
        this.name = name;
        this.text = text;
        this.link = link;
        this.localDateTime = localDateTime;
    }

    public PsqlPost(int id, String name, String text, String link, LocalDateTime localDateTime) {
        this.name = name;
        this.text = text;
        this.link = link;
        this.localDateTime = localDateTime;
    }

    @Override
    public int getId() {
        return id;
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
        return "Post{"
                + "id='" + id + '\''
                + "name='" + name + '\''
                + ", text='" + text + '\''
                + ", link='" + link + '\''
                + ", localDateTime=" + localDateTime
                + '}';
    }
}
