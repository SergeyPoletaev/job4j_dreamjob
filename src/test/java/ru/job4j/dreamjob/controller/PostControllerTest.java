package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "msg"),
                new Post(2, "New post", "msg")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    void whenFormAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "СПб")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formAddPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(1, "New post", new City(1));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(1)).thenReturn(Optional.of(new City(1, "Москва")));
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(1, "New post", new City(1));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(1)).thenReturn(Optional.of(new City(1, "Москва")));
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(post);
        verify(postService).update(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenFormUpdatePost() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        int id = 1;
        Post post = new Post(1, "New post", new City(1));
        when(postService.findById(id)).thenReturn(Optional.of(post));
        CityService cityService = mock(CityService.class);
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "СПб")
        );
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, id, session);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("updatePost");
    }
}