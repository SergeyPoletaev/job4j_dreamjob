package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDbStoreTest {
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
        try (PreparedStatement ps = pool.getConnection().prepareStatement("delete from users")) {
            ps.execute();
        }
    }

    @Test
    void whenRegisterUserWithUniqueEmail() {
        UserDbStore store = new UserDbStore(pool);
        User user = new User(0, "Java Job", "anna@ya");
        store.add(user);
        User userInDb = store.findById(user.getId()).orElseThrow();
        assertThat(userInDb.getName()).isEqualTo(user.getName());
    }

    @Test
    void whenRegisterUserWithDuplicateEmail() {
        UserDbStore store = new UserDbStore(pool);
        User user1 = new User(0, "Java Job", "anna@ya");
        store.add(user1);
        User user2 = new User(0, "Java Job", "anna@ya");
        store.add(user2);
        List<User> usersInDb = store.findAll();
        assertThat(usersInDb).isEqualTo(List.of(user1));
    }

}