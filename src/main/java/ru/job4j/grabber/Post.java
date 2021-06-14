package ru.job4j.grabber;

import java.time.LocalDateTime;

public interface Post {
    int getId();

    String getName();

    String getText();

    String getLink();

    LocalDateTime getLocalDateTime();
}
