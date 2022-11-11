package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateDbStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDbStore store;
    private final CityService cityService;

    public CandidateService(CandidateDbStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        List<Candidate> candidates = store.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId()).orElseThrow()
                )
        );
        return candidates;
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
