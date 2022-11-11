package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostDbStore {
    private final static Logger LOG = LoggerFactory.getLogger(PostDbStore.class.getName());
    private final static String SELECT_ALL_POST = "SELECT * FROM post";
    private final static String INSERT_INTO_POST = "INSERT INTO post(name, description, visible, city_id, created) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE_POST_BY_ID = "UPDATE post SET name = ?, description = ?, visible = ?, city_id = ?, created = ? WHERE id = ?";
    private final static String SELECT_POST_BY_ID = "SELECT * FROM post WHERE id = ?";
    private final BasicDataSource pool;

    public PostDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL_POST)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(getPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    private Post getPost(ResultSet rs) throws SQLException {
        return new Post(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("visible"),
                new City(rs.getInt("city_id")),
                rs.getTimestamp("created").toLocalDateTime());
    }

    public boolean add(Post post) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_INTO_POST, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDesc());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            rsl = ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    public boolean update(Post post) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_POST_BY_ID)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDesc());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.setInt(6, post.getId());
            rsl = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    public Optional<Post> findById(int id) {
        Optional<Post> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_POST_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(getPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }
}
