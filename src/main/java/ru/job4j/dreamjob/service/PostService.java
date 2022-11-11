package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostDbStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class PostService {
    private final PostDbStore store;
    private final CityService cityService;

    public PostService(PostDbStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Post> findAll() {
        List<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId()).orElseThrow()
                )
        );
        return posts;
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
