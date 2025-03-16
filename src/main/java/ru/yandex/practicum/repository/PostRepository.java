package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.CreateCommentDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Integer findPostsCount();

    List<PostsFeed> findAll();

    void save(PostDto postDto);

    List<PostsFeed> findAllPosts(Integer offset, Integer limit);

    List<PostsFeed> filteringByTag(String keyword, Integer offset, Integer limit);

    Optional<Post> findPost(Integer id);

    void saveComment(CreateCommentDto createCommentDto);

    void addLike(Integer postId);

    void deleteComment(Integer commentId, Integer postId);

    void deletePost(Integer postId);

    //List<PostFeedDto> findAllPosts();
}
