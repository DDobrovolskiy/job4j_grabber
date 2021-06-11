package ru.job4j.grabber;

import java.util.List;

public interface Store {
    void save(IPost post);

    List<IPost> getAll();

    IPost findById(Integer postId);
}
