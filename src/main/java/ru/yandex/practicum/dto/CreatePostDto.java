package ru.yandex.practicum.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostDto {
    @NonNull
    private String name;
    @NonNull
    private String imageUrl;
    @NonNull
    private String description;
    //@NonNull
    private String tags;
}
