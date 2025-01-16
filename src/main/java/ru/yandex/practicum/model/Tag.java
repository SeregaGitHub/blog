package ru.yandex.practicum.model;

import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Integer postId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(name, tag.name) &&
                Objects.equals(postId, tag.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, postId);
    }
}
