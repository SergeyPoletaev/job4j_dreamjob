package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateDbStoreTest {
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
        try (PreparedStatement ps = pool.getConnection().prepareStatement("delete from candidate")) {
            ps.execute();
        }
    }

    @Test
    void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate = new Candidate(0, "Anna", new City());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId()).orElseThrow();
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    void whenUpdateCandidate() {
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate = new Candidate(0, "Anna", new City());
        store.add(candidate);
        Candidate newCandidate = new Candidate(candidate.getId(), "Sveta", new City());
        store.update(newCandidate);
        Candidate candidateInDb = store.findById(candidate.getId()).orElseThrow();
        assertThat(candidateInDb.getName()).isEqualTo(newCandidate.getName());
    }

    @Test
    void whenFindAllCandidates() {
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate1 = new Candidate(0, "Anna", new City());
        store.add(candidate1);
        Candidate candidate2 = new Candidate(0, "Sveta", new City());
        store.add(candidate2);
        List<Candidate> rsl = store.findAll();
        assertThat(List.of(rsl.get(0).getName(), rsl.get(1).getName()))
                .isEqualTo(List.of(candidate1.getName(), candidate2.getName()));
    }
}