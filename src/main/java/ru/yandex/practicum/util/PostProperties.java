package ru.yandex.practicum.util;

import lombok.Builder;
import lombok.Value;
import ru.yandex.practicum.dto.UpdatePostDto;
import ru.yandex.practicum.model.Post;

@Value
@Builder
public class PostProperties {
    Post post;
    UpdatePostDto updatePostDto;
}
