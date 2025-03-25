package ru.yandex.practicum.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.CreatePostDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.dto.UpdatePostDto;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Utilities {

    @SneakyThrows
    public List<CommentDto> arrayToList(Array a) {

        List<String> comments = Arrays.stream((Object[]) a.getArray())
                .map(Object::toString)
                .collect(Collectors.toList());

        return toComments(comments);
    }

    private List<CommentDto> toComments(List<String> list) {
        // Не хотел получать комментарии отдельным запросом. Другого способа не придумал

        List<CommentDto> commentDtoList = new ArrayList<>();
        for (String s : list) {
            int[] commaIndexes = new int[2];
            Integer[] additionalIndexes = new Integer[2];

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ',') {
                    commaIndexes[0] = i;

                    if (s.charAt(i + 1) == '"') {
                        additionalIndexes[0] = i + 1;
                    } else {
                        additionalIndexes[0] = i;
                    }

                    break;
                }
            }

            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == ',') {
                    commaIndexes[1] = i;

                    if (s.charAt(i - 1) == '"') {
                        additionalIndexes[1] = i - 1;
                    } else {
                        additionalIndexes[1] = i;
                    }

                    break;
                }
            }

            commentDtoList.add(
                    CommentDto.builder()
                            .id(Integer.parseInt(s.substring(1, commaIndexes[0])))
                            .postComment(s.substring(additionalIndexes[0] + 1, additionalIndexes[1]))
                            .build());
        }
        return commentDtoList;
    }

    public PostDto toPostDto(CreatePostDto createPostDto) {
        return PostDto.builder()
                .name(createPostDto.getName())
                .imageUrl(createPostDto.getImageUrl())
                .description(Arrays.stream(createPostDto.getDescription().split("\n")).toList().toArray(new String[0]))
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

    public String toStringFromList(List<String> list, String separator) {
        StringBuilder builder = new StringBuilder();
        for (String s: list) {
            builder.append(s);
            builder.append(separator);
        }
        return builder.toString();
    }
}
