package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;

public interface PostRepository {
    List<PostsFeed> findAll();

    void save(PostDto postDto);

    //List<PostFeedDto> findAllPosts();
}
