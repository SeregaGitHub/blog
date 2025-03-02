package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.PostsFeed;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer findPostsCount() {
        return jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*) AS posts_count FROM post;
                        """,
                            Integer.class
        );
    }

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
                        """,
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

    @Override
    public void save(PostDto postDto) {
        List<SqlParameter> procedureParams = List.of(
                new SqlParameter("post_name", Types.VARCHAR),
                new SqlParameter("image_url", Types.VARCHAR),
                new SqlParameter("description", Types.ARRAY),
                new SqlParameter("tags_array", Types.ARRAY)
        );

        jdbcTemplate.call(connection -> {
            Array sqlDescriptionArray = connection.createArrayOf("text", postDto.getDescription());
            Array sqlTagsArray = connection.createArrayOf("varchar", postDto.getTags());

            CallableStatement callableStatement = connection.prepareCall("call create_new_post(?, ?, ?, ?)");
            callableStatement.setString(1, postDto.getName());
            callableStatement.setString(2, postDto.getImageUrl());
            callableStatement.setArray(3, sqlDescriptionArray);
            callableStatement.setArray(4, sqlTagsArray);

            return callableStatement;
        }, procedureParams);
    }

    @Override
    public List<PostsFeed> findAllPosts(Integer offset, Integer limit) {
        return jdbcTemplate.query(
                """
                        SELECT p.id, p.name, p.image_url, SUBSTRING(p.description[1], 1, 70) AS abbreviatedDescription,
                        p.commentsCount, COALESCE (l.count, 0) AS likesCount,
                        COALESCE (STRING_AGG(t.name, ' '), '') AS tags
                        FROM post p
                        LEFT JOIN likes l ON l.post_id = p.id
                        LEFT JOIN post_tag pt ON p.id = pt.post_id
                        LEFT JOIN tag t ON t.id = pt.tag_id
                        GROUP BY p.id, l.count
                        ORDER BY p.id DESC
                        OFFSET ?
                        LIMIT ?;
                        """,
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
                        .build(),
                offset, limit);
    }

    @Override
    public List<PostsFeed> filteringByTag(String keyword, Integer offset, Integer limit) {
        return jdbcTemplate.query(
                """
                        SELECT p.id, p.name, p.image_url, SUBSTRING(p.description[1], 1, 70) AS abbreviatedDescription,
                        p.commentsCount, COALESCE (l.count, 0) AS likesCount,
                        COALESCE (STRING_AGG(t.name, ' '), '') AS tags
                        FROM post p
                        LEFT JOIN likes l ON l.post_id = p.id
                        LEFT JOIN post_tag pt ON p.id = pt.post_id
                        LEFT JOIN tag t ON t.id = pt.tag_id
                        WHERE p.id IN (SELECT pt.post_id
                        			   FROM post_tag pt
                        			   JOIN tag t ON pt.tag_id = t.id
                        			   WHERE t.name = ?)
                        GROUP BY p.id, l.count
                        ORDER BY p.id DESC
                        OFFSET ?
                        LIMIT ?;
                        """,
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
                        .build(),
                keyword, offset, limit);
    }
}
