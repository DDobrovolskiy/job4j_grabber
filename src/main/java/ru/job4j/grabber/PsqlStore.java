package ru.job4j.grabber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.connect.ConnectSQL;
import ru.job4j.connect.ConfigSQL;
import ru.job4j.properties.PropertyFactory;

public class PsqlStore implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private Connection cnn;

    public PsqlStore(ConfigSQL properties) {
        cnn = new ConnectSQL().get(properties);
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement(
                             "insert into posts (name, text, link, date) values (?, ?, ?, ?)")) {
            statement.setString(1, post.getName());
            statement.setString(2, post.getText());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getLocalDateTime()));
            if (statement.execute()) {
                LOG.warn("Данные поста не добавились в таблицу SQL: {}", post);
            } else {
                LOG.debug("Запись поста добавлена: {}", post);
            }
        } catch (Exception e) {
            LOG.error("Ошибка добавления в таблицу SQL: {}", post, e);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new LinkedList<>();
        try (PreparedStatement statement =
                     cnn.prepareStatement(
                             "SELECT post_id, name, text, link, date FROM posts")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(new PsqlPost(
                            resultSet.getInt("post_id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("date").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка получения данных SQL: ", e);
        }
        return posts;
    }

    @Override
    public Post findById(Integer postId) {
        Post post = null;
        try (PreparedStatement statement =
                     cnn.prepareStatement(
                             "SELECT post_id, name, text, link, date "
                                    + "FROM posts "
                                    + "WHERE post_id = ?")) {
            statement.setInt(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    LOG.warn("Не найдена запись поста с post_id = {}", postId);
                } else {
                    post = new PsqlPost(
                            resultSet.getInt("post_id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("date").toLocalDateTime());
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка получения данных SQL: ", e);
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        PsqlStore psqlStore =
                new PsqlStore(
                        new PsqlProperties(PropertyFactory.load("app.properties")));
        Post post = new PsqlPost("test", "test", "http://test.com", LocalDateTime.now());
        psqlStore.save(post);
        psqlStore.getAll().forEach(System.out::println);
        System.out.println("----");
        System.out.println(psqlStore.findById(1));
    }
}

