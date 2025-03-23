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
import ru.yandex.practicum.util.PostProperties;
import ru.yandex.practicum.util.Utilities;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private static int from = 0;
    private static int pageSize = 10;
    private static boolean hasNext = true;

    private final PostRepositoryImpl repository;

    @Override
    public List<PostsFeed> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(CreatePostDto createPostDto) {
        repository.save(Utilities.toPostDto(createPostDto));
    }

    @Override
    public List<PostsFeed> findPosts(String size, String prev, String next) {

        if (size != null) {
            pageSize = Utilities.setPageCount(size);
            from = 0;
            hasNext = true;
        } else if (prev != null && from > 0) {
            from = from - pageSize;
            hasNext = true;
        } else if (next != null && hasNext) {
            from = from + pageSize;
        }

        List<PostsFeed> postsFeedList = repository.findAllPosts(from, pageSize);

        if (postsFeedList.size() < pageSize) {
            hasNext = false;
        }

        return repository.findAllPosts(from, pageSize);
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
    public PostProperties findPost(Integer id) {
        Post post = repository.findPost(id).orElseThrow(
                () -> new RuntimeException("This post is not exist"));

        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .id(post.getId())
                .name(post.getName())
                .imageUrl(post.getImageUrl())
                .description(Utilities.toStringFromList(post.getDescription(), "\n"))
                .tags(Utilities.toStringFromList(post.getTags(), " "))
                .build();

        return PostProperties.builder()
                .post(post)
                .updatePostDto(updatePostDto)
                .build();
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
