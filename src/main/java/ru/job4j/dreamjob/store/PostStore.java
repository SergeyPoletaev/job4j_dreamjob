package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private final static PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "msg1"));
        posts.put(2, new Post(2, "Middle Java Job", "msg2"));
        posts.put(3, new Post(3, "Senior Java Job", "msg3"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public boolean add(Post post) {
        return posts.putIfAbsent(post.getId(), post) == null;
    }

    public Post findById(int id) {
        return posts.getOrDefault(id, new Post());
    }

    public boolean update(Post post) {
        return posts.computeIfPresent(post.getId(), (oldVal, newVal) -> post) != null;
    }
}
