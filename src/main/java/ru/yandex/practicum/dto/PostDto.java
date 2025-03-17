package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PostDto {
    Integer id;
    @NonNull
    String name;
    @NonNull
    String imageUrl;
    @NonNull
    //private List<String> description;
    String[] description;
    @NonNull
    //private List<String> tags;
    String[] tags;
}
