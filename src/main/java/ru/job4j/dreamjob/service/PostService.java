package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;

import java.util.Collection;
import java.util.Optional;

public class PostService {
    private final static PostService INST = new PostService();
    private final PostStore store = PostStore.instOf();

    private PostService() {
    }

    public static PostService instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public boolean add(Post post) {
        return store.add(post);
    }

    public Optional<Post> findById(int id) {
        return store.findById(id);
    }

    public boolean update(Post post) {
        return store.update(post);
    }
}
