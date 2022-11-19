package ru.job4j.dreamjob.utils;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public class HttpHelper {

    private HttpHelper() {

    }

    public static void setSessionToModel(HttpSession session, Model model) {
        User user = getUser(session);
        model.addAttribute("user", user);
    }

    private static User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }


}
