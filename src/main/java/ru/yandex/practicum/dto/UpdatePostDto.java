package ru.yandex.practicum.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostDto {
    @NonNull
    Integer id;
    @NonNull
    String name;
    @NonNull
    String imageUrl;
    @NonNull
    String description;
    @NonNull
    String tags;
}
