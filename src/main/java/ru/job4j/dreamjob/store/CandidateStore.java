package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private final AtomicInteger count = new AtomicInteger();
    private final static CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(count.incrementAndGet(), "Anna", "level-1"));
        candidates.put(2, new Candidate(count.incrementAndGet(), "Sveta", "level-2"));
        candidates.put(3, new Candidate(count.incrementAndGet(), "Tanya", "level-3"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public boolean add(Candidate candidate) {
        candidate.setId(count.incrementAndGet());
        return candidates.putIfAbsent(candidate.getId(), candidate) == null;
    }

    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    public boolean update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate) != null;
    }
}
