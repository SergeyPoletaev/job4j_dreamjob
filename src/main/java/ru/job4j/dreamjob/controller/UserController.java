package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

@ThreadSafe
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formAddUser")
    public String formAddPost() {
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (!userService.add(user)) {
            redirectAttributes.addFlashAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/formAddUser";
        }
        redirectAttributes.addFlashAttribute("message", "Пользователь успешно зарегистрирован");
        return "redirect:/formAddUser";
    }
}
