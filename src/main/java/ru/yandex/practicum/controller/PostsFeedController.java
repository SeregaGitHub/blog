package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String findAllPosts(Model model,
                               @RequestParam(value = "size", required = false) String size,
                               @RequestParam(value = "prev", required = false) String prev,
                               @RequestParam(value = "next", required = false) String next) {



        List<PostsFeed> postsFeedList = service.findPosts(size, prev, next);
        model.addAttribute("feed", postsFeedList);


        return "feed";
    }

    @PostMapping
    public String save(@ModelAttribute CreatePostDto createPostDto) {
        service.save(createPostDto);
        return "redirect:/feed";
    }
}
