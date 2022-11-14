package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostDbStoreTest {
    private static BasicDataSource pool;

    @BeforeAll
    static void initPool() {
        pool = new Main().loadPool();
    }

    @AfterAll
    static void close() throws SQLException {
        pool.close();
    }

    @AfterEach
    void cleanDb() throws SQLException {
        try (PreparedStatement ps = pool.getConnection().prepareStatement("delete from post")) {
            ps.execute();
        }
    }

    @Test
    void whenCreatePost() {
        PostDbStore store = new PostDbStore(pool);
        Post post = new Post(0, "Java Job", new City());
        store.add(post);
        Post postInDb = store.findById(post.getId()).orElseThrow();
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    void whenUpdatePost() {
        PostDbStore store = new PostDbStore(pool);
        Post post = new Post(0, "Java Job", new City());
        store.add(post);
        Post newPost = new Post(post.getId(), "Java Job2", new City());
        store.update(newPost);
        Post postInDb = store.findById(post.getId()).orElseThrow();
        assertThat(postInDb.getName()).isEqualTo(newPost.getName());
    }

    @Test
    void whenFindAllPosts() {
        PostDbStore store = new PostDbStore(pool);
        Post post1 = new Post(0, "Java Job", new City());
        store.add(post1);
        Post post2 = new Post(0, "Java Job2", new City());
        store.add(post2);
        List<Post> rsl = store.findAll();
        assertThat(List.of(rsl.get(0).getName(), rsl.get(1).getName()))
                .isEqualTo(List.of(post1.getName(), post2.getName()));
    }
}