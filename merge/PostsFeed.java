package ru.yandex.practicum.model;

import lombok.*;

import java.util.List;

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
    @NonNull
    private String abbreviatedDescription;
    private int commentsCount;
    private int likesCount;
    @NonNull
    private String tags;
    //private List<String> tags;

}
