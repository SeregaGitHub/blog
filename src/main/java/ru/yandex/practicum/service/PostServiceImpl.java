package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.CreateCommentDto;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.UpdatePostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.repository.PostRepositoryImpl;
import ru.yandex.practicum.util.PageProperties;
import ru.yandex.practicum.util.Utilities;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepositoryImpl repository;

    @Override
    public void savePost(CreatePostDto createPostDto) {
        repository.savePost(Utilities.toPostDto(createPostDto));
    }

    @Override
    public PageProperties findPosts(Integer page, Integer size, String prev, String next, Integer postsCount, String keyword) {
        int offset;

        if (prev != null && page > 0) {
            offset = page - size;
        } else if (next != null && Objects.equals(postsCount, size)) {
            offset = page + size;
        } else {
            offset = page;
        }

        List<PostsFeed> postsFeedList;
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
            postsFeedList = repository.findAllPosts(offset, size);
        } else {
            postsFeedList = repository.filteringByTag("#".concat(keyword.toLowerCase()), offset, size);
        }

        return PageProperties.builder()
                .page(offset)
                .size(size)
                .postsFeedList(postsFeedList)
                .postsCount(postsFeedList.size())
                .keyword(keyword)
                .build();
    }

    @Override
    public Post findPost(Integer id) {
        Post post = repository.findPost(id).orElseThrow(
                () -> new RuntimeException("This post is not exist"));
        return post;
    }

    @Override
    public void saveComment(CreateCommentDto createCommentDto, Integer post_id) {
        createCommentDto.setPostId(post_id);
        repository.saveComment(createCommentDto);
    }

    @Override
    public void addLike(Integer postId) {
        repository.addLike(postId);
    }

    @Override
    public void deleteComment(Integer commentId, Integer postId) {
        repository.deleteComment(commentId, postId);
    }

    @Override
    public void deletePost(Integer postId) {
        repository.deletePost(postId);
    }

    @Override
    public void updatePost(UpdatePostDto updatePostDto, Integer postId) {
        updatePostDto.setId(postId);
        repository.updatePost(Utilities.toPostDto(updatePostDto));
    }

    @Override
    public void updateComment(CommentDto commentDto, Integer commentId) {
        commentDto.setId(commentId);
        repository.updateComment(commentDto);
    }
}
