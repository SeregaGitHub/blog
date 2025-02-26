package ru.yandex.practicum.util;

import lombok.experimental.UtilityClass;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.PostDto;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Utilities {
    /*public String arrayToString(List<String> list) {
        return String.join(" ", list);
    }*/

    public List<String> arrayToList(Array a) {
        List<String> tags = new ArrayList<>();

        Object[] tagsArray = null;
        try {
            tagsArray = (Object[]) a.getArray();
        } catch (SQLException e) {
            return tags;
        }

        return Arrays.stream(tagsArray)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public PostDto toPostDto(CreatePostDto createPostDto) {
        return PostDto.builder()
                .name(createPostDto.getName())
                .imageUrl(createPostDto.getImageUrl())
//                .description(List.of(createPostDto.getDescription().split("\n")))
//                .tags(List.of(createPostDto.getTags().split(" ")))
                //.description(new ArrayList<>(List.of(createPostDto.getDescription().split("\n"))))
                .description(Arrays.stream(createPostDto.getDescription().split("\n")).toList().toArray(new String[0]))
                //.tags(new ArrayList<>(List.of(createPostDto.getTags().split(" "))))
                .tags(Arrays.stream(createPostDto.getTags().split(" ")).toList().toArray(new String[0]))
                .build();
    }

    public Integer setPageCount(String str) {
        return switch (str) {
            case "twenty" -> 20;
            case "fifty" -> 50;
            default -> 10;
        };
    }
}
