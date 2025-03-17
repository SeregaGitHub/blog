package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CreateCommentDto;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.UpdatePostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.util.PageProperties;

import java.util.List;

public interface PostService {
    List<PostsFeed> findAll();

    void save(CreatePostDto createPostDto);

    //List<PostsFeed> findPosts(Integer from, Integer pageSize);
    List<PostsFeed> findPosts(String size, String prev, String next);

    PageProperties findPosts(Integer page, Integer size, String prev, String next, Integer postsCount, String keyword);


    Post findPost(Integer id);

    void saveComment(CreateCommentDto createCommentDto, Integer post_id);

    void addLike(Integer postId);

    void deleteComment(Integer commentId, Integer postId);

    void deletePost(Integer postId);

    void updatePost(UpdatePostDto updatePostDto, Integer postId);
}
