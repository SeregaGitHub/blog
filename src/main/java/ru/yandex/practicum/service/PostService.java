package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;

public interface PostService {
    List<PostsFeed> findAll();

    void save(CreatePostDto createPostDto);
    //List<PostFeedDto> findAllPosts();
}
