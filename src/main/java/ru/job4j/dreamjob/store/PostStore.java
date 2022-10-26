package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private final static PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post("Junior Java Job", "msg1"));
        posts.put(2, new Post("Middle Java Job", "msg2"));
        posts.put(3, new Post("Senior Java Job", "msg3"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public boolean add(Post post) {
        post.setId();
        return posts.putIfAbsent(post.getId(), post) == null;
    }
}
