package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;

public interface PostRepository {
    List<PostsFeed> findAll();

    List<PostFeedDto> findAllPosts();
}
