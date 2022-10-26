package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {
    private final AtomicInteger count = new AtomicInteger();
    private final static PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(count.incrementAndGet(), "Junior Java Job", "msg1"));
        posts.put(2, new Post(count.incrementAndGet(), "Middle Java Job", "msg2"));
        posts.put(3, new Post(count.incrementAndGet(), "Senior Java Job", "msg3"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public boolean add(Post post) {
        post.setId(count.incrementAndGet());
        return posts.putIfAbsent(post.getId(), post) == null;
    }

    public Optional<Post> findById(int id) {
        return Optional.of(posts.get(id));
    }

    public boolean update(Post post) {
        return posts.replace(post.getId(), post) != null;
    }
}
