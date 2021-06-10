package ru.job4j.grabber;

import java.io.IOException;
import java.util.List;

public interface Parse {
    List<IPost> list(String link);

    IPost detail(String link);
}
