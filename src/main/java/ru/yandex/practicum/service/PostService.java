package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.CreateCommentDto;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.UpdatePostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.util.PageProperties;

public interface PostService {

    void savePost(CreatePostDto createPostDto);

    PageProperties findPosts(Integer page, Integer size, String prev, String next, Integer postsCount, String keyword);

    Post findPost(Integer id);

    void saveComment(CreateCommentDto createCommentDto, Integer post_id);

    void addLike(Integer postId);

    void deleteComment(Integer commentId, Integer postId);

    void deletePost(Integer postId);

    void updatePost(UpdatePostDto updatePostDto, Integer postId);

    void updateComment(CommentDto commentDto, Integer commentId);
}
