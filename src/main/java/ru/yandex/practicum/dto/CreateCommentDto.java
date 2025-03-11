package ru.yandex.practicum.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDto {
    String commentText;
    Integer postId;
}
