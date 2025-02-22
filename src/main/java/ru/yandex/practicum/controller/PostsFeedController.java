package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class PostsFeedController {
    private final PostService service;

    @GetMapping
    public String users(Model model) {
        List<PostsFeed> feed = service.findAll();
        model.addAttribute("feed", feed);
        return "feed";
    }

    @PostMapping
    public String save(@ModelAttribute CreatePostDto createPostDto) {
        service.save(createPostDto);
        return "redirect:/feed";
    }
}
