package ru.yandex.practicum.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsFeed {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String imageUrl;
    private String abbreviatedDescription;
    private int commentsCount;
    private int likesCount;
    private String tags;

}
