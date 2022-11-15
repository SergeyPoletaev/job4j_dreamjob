package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.persistence.UserDbStore;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public boolean add(User user) {
        return store.add(user);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public List<User> findAll() {
        return store.findAll();
    }
}