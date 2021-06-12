package ru.job4j.html;

import ru.job4j.grabber.Post;

import java.io.IOException;

public interface ParsePost {
    Post get(String link) throws IOException;
}
