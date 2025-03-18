package ru.yandex.practicum.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    Integer id;
    String postComment;
}
