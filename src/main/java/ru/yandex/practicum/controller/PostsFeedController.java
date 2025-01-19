package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class PostsFeedController {
    private final PostService service;

    /*@GetMapping // GET запрос /users
    public String users(Model model) {
        // Данные теперь получаются программно

        List<PostsFeed> feed = service.findAll();
        // Передаем данные в виде атрибута users
        model.addAttribute("feed", feed);
        return "feed"; // Возвращаем название шаблона — users.html
    }*/

    @GetMapping // GET запрос /users
    public String users(Model model) {
        // Данные теперь получаются программно

        List<PostFeedDto> feed = service.findAllPosts();
        // Передаем данные в виде атрибута users
        model.addAttribute("feed", feed);
        return "feed"; // Возвращаем название шаблона — users.html
    }
}
