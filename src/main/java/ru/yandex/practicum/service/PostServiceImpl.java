package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.repository.PostRepositoryImpl;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepositoryImpl repository;

    @Override
    public List<PostsFeed> findAll() {
        return repository.findAll();
    }

    /*@Override
    public List<PostFeedDto> findAllPosts() {
        return repository.findAllPosts();
    }*/
}
