package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CandidateDbStore {
    private final static Logger LOG = LoggerFactory.getLogger(CandidateDbStore.class.getName());
    private final static String SELECT_ALL_CANDIDATE = "SELECT * FROM candidate";
    private final static String INSERT_INTO_CANDIDATE = "INSERT INTO candidate(name, description, visible, city_id, photo, created) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_CANDIDATE_BY_ID = "UPDATE candidate SET name = ?, description = ?, visible = ?, city_id = ?, photo = ?, created = ? WHERE id = ?";
    private final static String SELECT_CANDIDATE_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL_CANDIDATE)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidates;
    }

    private Candidate getCandidate(ResultSet rs) throws SQLException {
        return new Candidate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("visible"),
                new City(rs.getInt("city_id")),
                rs.getBytes("photo"),
                rs.getTimestamp("created").toLocalDateTime());
    }

    public boolean add(Candidate candidate) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_INTO_CANDIDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setBoolean(3, candidate.isVisible());
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.setTimestamp(6, Timestamp.valueOf(candidate.getCreated()));
            rsl = ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    public boolean update(Candidate candidate) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_CANDIDATE_BY_ID)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setBoolean(3, candidate.isVisible());
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.setTimestamp(6, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(7, candidate.getId());
            rsl = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    public Optional<Candidate> findById(int id) {
        Optional<Candidate> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_CANDIDATE_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(getCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }
}
