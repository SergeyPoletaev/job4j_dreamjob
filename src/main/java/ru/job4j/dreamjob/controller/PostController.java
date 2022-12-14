package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.job4j.dreamjob.utils.HttpHelper.setSessionToModel;

@ThreadSafe
@Controller
public class PostController {
    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        setSessionToModel(session, model);
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model, HttpSession session) {
        setSessionToModel(session, model);
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        int cityId = post.getCity().getId();
        City city = cityService.findById(cityId)
                .orElseThrow(() -> new NoSuchElementException("Выбранный город не найден списке доступных городов"));
        post.setCity(city);
        postService.add(post);
        return "redirect:/posts";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        int cityId = post.getCity().getId();
        City city = cityService.findById(cityId)
                .orElseThrow(() -> new NoSuchElementException("Выбранный город не найден списке доступных городов"));
        post.setCity(city);
        postService.update(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        Optional<Post> post = postService.findById(id);
        model.addAttribute("post",
                post.orElseThrow(() -> new NoSuchElementException("Не найден объект для редактирования")));
        model.addAttribute("cities", cityService.getAllCities());
        setSessionToModel(session, model);
        return "updatePost";
    }
}
