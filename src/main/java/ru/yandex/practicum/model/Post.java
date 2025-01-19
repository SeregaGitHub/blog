package ru.yandex.practicum.model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String imageUrl;
    @NonNull
    private List<String> description;
    @NonNull
    private List<String> tags;
    @NonNull
    private List<Comment> comments;
    private int likes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(name, post.name) &&
                Objects.equals(imageUrl, post.imageUrl) &&
                Objects.equals(description, post.description) &&
                Objects.equals(tags, post.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, description, tags);
    }
}
