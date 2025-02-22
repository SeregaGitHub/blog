package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PostDto {
    @NonNull
    private String name;
    @NonNull
    private String imageUrl;
    @NonNull
    //private List<String> description;
    private String[] description;
    @NonNull
    //private List<String> tags;
    private String[] tags;
}
