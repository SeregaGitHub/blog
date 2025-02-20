package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.PostFeedDto;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.util.Utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PostsFeed> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT p.id, p.name, p.image_url, SUBSTRING(p.description[1], 1, 7) AS abbreviatedDescription,
                        p.commentsCount, COALESCE (l.count, 0) AS likesCount,
                        COALESCE (STRING_AGG(t.name, ' '), '') AS tags
                        FROM post p
                        LEFT JOIN likes l ON l.post_id = p.id
                        LEFT JOIN post_tag pt ON p.id = pt.post_id
                        LEFT JOIN tag t ON t.id = pt.tag_id
                        GROUP BY p.id, l.count
                        ORDER BY p.id DESC
                        OFFSET 0
                        LIMIT 20;
                        """
                /*"SELECT post.id, post.name, image_url, " +
                        "SUBSTRING(description[1], 2, 70) AS abbreviatedDescription, " +
                        "ARRAY(SELECT name FROM tag " +
                        "WHERE post.id = tag.post_id" +
                        ") AS tags" +
                        ", likes.count AS likesCount, " +
                        "COUNT(comment.post_id) AS commentCount " +
                        "FROM post " +
                        "LEFT JOIN likes ON post.id = likes.post_id " +
                        "LEFT JOIN comment ON post.id = comment.post_id " +
                        "GROUP BY comment.post_id, post.name, post.id, likes.count"*/,
                (rs, rowNum) -> PostsFeed.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .imageUrl(rs.getString("image_url"))
                        //.abbreviatedDescription(rs.getArray())
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .commentsCount(rs.getInt("commentsCount"))
                        .likesCount(rs.getInt("likesCount"))
                        //.tags(Utilities.arrayToList(rs.getArray("tags")))
                        //.tags(List.of(rs.getString("tags").split(" ")))
                        .tags(rs.getString("tags"))
                        .build());
    }

    /*@Override
    public List<PostsFeed> findAll() {
        return jdbcTemplate.query(
                "SELECT post.id, post.name, image_url, " +
                        "SUBSTRING(description[1], 2, 70) AS abbreviatedDescription, " +
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
                        //.abbreviatedDescription(rs.getArray())
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .commentsCount(rs.getInt("commentCount"))
                        .likesCount(rs.getInt("likesCount"))
                        .tags(Utilities.arrayToList(rs.getArray("tags")))
                        .build());
    }*/

    /*@Override
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
                (rs, rowNum) -> PostFeedDto.builder()
                        .name(rs.getString("name"))
                        .imageUrl(rs.getString("image_url"))
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .likesCount(rs.getInt("likesCount"))
                        .commentsCount(rs.getInt("commentCount"))
                        .build());
    }*/
}
