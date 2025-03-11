package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDto {
    Integer id;
    String postComment;
}
