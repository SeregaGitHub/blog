package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    /*@Override
    public List<PostsFeed> findAll() {
        return jdbcTemplate.query(
                "select id, name, imageUrl, age, active from users",
                (rs, rowNum) -> new PostsFeed(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getBoolean("active")
                ));
    }*/

    @Override
    public List<PostsFeed> findAll() {
        return jdbcTemplate.query(
                "SELECT post.id, post.name, image_url, " +
                        "SUBSTRING(description[1], 1, 7) AS abbreviatedDescription, " +
                        "ARRAY(SELECT name FROM tag " +
                        "WHERE post.id = tag.post_id" +
                        ") AS tags" +
                        ", likes.count AS likesCount, " +
                        "COUNT(comment.post_id) AS commentCount " +
                        "FROM post " +
                        "LEFT JOIN likes ON post.id = likes.post_id " +
                        "LEFT JOIN comment ON post.id = comment.post_id " +
                        "GROUP BY comment.post_id, post.name, post.id, likes.count",
                (rs, rowNum) -> PostsFeed.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .imageUrl(rs.getString("image_url"))
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .commentsCount(rs.getInt("commentCount"))
                        .likesCount(rs.getInt("likesCount"))
                        .tags((List<String>) rs.getArray("tags"))
                        .build());
    }

    @Override
    public List<PostFeedDto> findAllPosts() {
        return jdbcTemplate.query(
                "SELECT post.name, image_url, " +
                        "SUBSTRING(description[1], 2, 7) AS abbreviatedDescription, " +
                        "likes.count AS likesCount, " +
                        "COUNT(comment.post_id) AS commentCount " +
                        "FROM post " +
                        "LEFT JOIN likes ON post.id = likes.post_id " +
                        "LEFT JOIN comment ON post.id = comment.post_id " +
                        "GROUP BY comment.post_id, post.name, likes.count",
                /*(rs, rowNum) -> new PostFeedDto(
                        rs.getString("name"),
                        rs.getString("image_url"),
                        rs.getString("abbreviatedDescription"),
                        rs.getInt("commentCount"),
                        rs.getInt("likesCount")
                ));*/
                (rs, rowNum) -> PostFeedDto.builder()
                        .name(rs.getString("name"))
                        .imageUrl(rs.getString("image_url"))
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .likesCount(rs.getInt("likesCount"))
                        .commentsCount(rs.getInt("commentCount"))
                        .build());
    }
}
