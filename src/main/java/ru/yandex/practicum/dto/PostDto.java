package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PostDto {
    Integer id;
    @NonNull
    String name;
    @NonNull
    String imageUrl;
    @NonNull
    String[] description;
    @NonNull
    String[] tags;
}
