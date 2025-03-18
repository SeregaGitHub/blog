package ru.yandex.practicum.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.dto.UpdatePostDto;

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

    @SneakyThrows
    public List<CommentDto> arrayToList(Array a) {
        //List<CommentDto> commentDtoList = new ArrayList<>();

        List<String> comments = Arrays.stream((Object[]) a.getArray())
                .map(Object::toString)
                .collect(Collectors.toList());

        /*, commentDtoList*/
        return toComments(comments/*, commentDtoList*/);
    }

    private List<CommentDto> toComments(List<String> list/*, List<CommentDto> commentDtoList*/) {
        // Не хотел получать комментарии отдельным запросом. Другого способа не придумал

        List<CommentDto> commentDtoList = new ArrayList<>();
        for (String s : list) {
            int[] indexes = new int[2];

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ',') {
                    indexes[0] = i;
                    break;
                }
            }

            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == ',') {
                    indexes[1] = i;
                    break;
                }
            }

            commentDtoList.add(
                    CommentDto.builder()
                            .id(Integer.parseInt(s.substring(1, indexes[0])))
                            .postComment(s.substring(indexes[0] + 1, indexes[1]))
                            .build());
        }
        return commentDtoList;
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

    public PostDto toPostDto(UpdatePostDto updatePostDto) {
        return PostDto.builder()
                .id(updatePostDto.getId())
                .name(updatePostDto.getName())
                .imageUrl(updatePostDto.getImageUrl())
                .description(Arrays.stream(updatePostDto.getDescription().split("\n")).toList().toArray(new String[0]))
                .tags(Arrays.stream(updatePostDto.getTags().split(" ")).toList().toArray(new String[0]))
                .build();
    }

    public Integer setPageCount(String str) {
        return switch (str) {
            case "twenty" -> 20;
            case "fifty" -> 50;
            default -> 10;
        };
    }

    public String toStringFromList(List<String> list, String separator) {
        StringBuilder builder = new StringBuilder();
        for (String s: list) {
            builder.append(s);
            builder.append(separator);
        }
        return builder.toString();
    }
}
