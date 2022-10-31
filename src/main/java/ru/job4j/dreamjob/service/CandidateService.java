package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class CandidateService {
    private final CandidateStore store;

    public CandidateService(CandidateStore store) {
        this.store = store;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public boolean add(Candidate candidate) {
        return store.add(candidate);
    }

    public Optional<Candidate> findById(int id) {
        return store.findById(id);
    }

    public boolean update(Candidate candidate) {
        return store.update(candidate);
    }
}
