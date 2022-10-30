package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;
import java.util.Optional;

public class CandidateService {
    private final static CandidateService INST = new CandidateService();
    private final CandidateStore store = CandidateStore.instOf();

    private CandidateService() {
    }

    public static CandidateService instOf() {
        return INST;
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
