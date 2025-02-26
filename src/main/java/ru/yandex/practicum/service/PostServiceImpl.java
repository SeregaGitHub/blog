package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.repository.PostRepositoryImpl;
import ru.yandex.practicum.util.Utilities;

import java.util.List;

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
    //public List<PostsFeed> findPosts(Integer from, Integer pageSize) {
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
}
