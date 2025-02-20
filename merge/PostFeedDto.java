package ru.yandex.practicum.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostFeedDto {
    @NonNull
    private String name;
    @NonNull
    private String imageUrl;
    @NonNull
    private String abbreviatedDescription;
    private int likesCount;
    private int commentsCount;
}
