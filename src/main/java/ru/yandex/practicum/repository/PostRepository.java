package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;

public interface PostRepository {
    Integer findPostsCount();

    List<PostsFeed> findAll();

    void save(PostDto postDto);

    List<PostsFeed> findAllPosts(Integer offset, Integer limit);

    List<PostsFeed> filteringByTag(String keyword, Integer offset, Integer limit);

    //List<PostFeedDto> findAllPosts();
}
