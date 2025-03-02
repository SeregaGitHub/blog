package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.util.PageProperties;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class PostsFeedController {

    private final PostService service;

    @GetMapping
    public String findAllPosts(Model model,
                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                               @RequestParam(value = "prev", required = false) String prev,
                               @RequestParam(value = "next", required = false) String next,
                               @RequestParam(value = "posts", required = false) Integer postsCount) {



        //List<PostsFeed> postsFeedList = service.findPosts(page, size);
        PageProperties pageProperties = service.findPosts(page, size, prev, next, postsCount);

        model.addAttribute("feed", pageProperties);


        return "feed";
    }

    @PostMapping
    public String save(@ModelAttribute CreatePostDto createPostDto) {
        service.save(createPostDto);
        return "redirect:/feed";
    }
}
