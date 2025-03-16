package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreateCommentDto;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.util.PageProperties;

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
                               @RequestParam(value = "posts", required = false) Integer postsCount,
                               @RequestParam(value = "keyword", required = false) String keyword) {



        //List<PostsFeed> postsFeedList = service.findPosts(page, size);
        PageProperties pageProperties = service.findPosts(page, size, prev, next, postsCount, keyword);

        model.addAttribute("feed", pageProperties);


        return "feed";
    }

    @GetMapping(value = "/{id}")
    public String findPost(Model model, @PathVariable(name = "id") Integer id) {
        Post post = service.findPost(id);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping
    public String save(@ModelAttribute CreatePostDto createPostDto) {
        service.save(createPostDto);
        return "redirect:/feed";
    }

    @PostMapping(value = "/comment/post/{post_id}")
    public String saveComment(@ModelAttribute CreateCommentDto createCommentDto, @PathVariable(name = "post_id") Integer post_id) {
        service.saveComment(createCommentDto, post_id);
        return "redirect:/feed/" + post_id;
    }

    @PostMapping(value = "/likes/post/{post_id}")
    public String addLike(@PathVariable(name = "post_id") Integer post_id) {
        service.addLike(post_id);
        return "redirect:/feed/" + post_id;
    }

    @PostMapping(value = "/comment/delete/{comment_id}/post/{post_id}", params = "_method=delete")
    public String delete(@PathVariable(name = "comment_id") Integer commentId,
                         @PathVariable(name = "post_id") Integer postId) {
        service.deleteComment(commentId, postId);
        return "redirect:/feed/" + postId;
    }
    // http://localhost:8080/blog/feed/comment/delete/2/post/19
}
