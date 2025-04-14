package ru.yandex.practicum.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.dto.CreateCommentDto;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.model.Post;
import ru.yandex.practicum.model.PostsFeed;
import ru.yandex.practicum.util.Utilities;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void savePost(PostDto postDto) {
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
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .commentsCount(rs.getInt("commentsCount"))
                        .likesCount(rs.getInt("likesCount"))
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
                        .abbreviatedDescription(rs.getString("abbreviatedDescription"))
                        .commentsCount(rs.getInt("commentsCount"))
                        .likesCount(rs.getInt("likesCount"))
                        .tags(rs.getString("tags"))
                        .build(),
                keyword, offset, limit);
    }

    @Override
    public Optional<Post> findPost(Integer id) {
        List<Post> list = jdbcTemplate.query(
                """
                        SELECT p.id, p.name, p.image_url, p.description,
                        		COALESCE (STRING_AGG(DISTINCT t.name, ' '), '') AS tags,
                        		COALESCE (l.count, 0) AS likesCount,
                        		CASE
                              		WHEN (ARRAY_AGG(DISTINCT c.*)) <> '{NULL}' THEN (ARRAY_AGG(DISTINCT c.* ORDER BY c.* DESC))
                              		ELSE ARRAY[]::comment[]
                          		END AS comments
                        FROM post p
                        LEFT JOIN comment c ON c.post_id = p.id
                        LEFT JOIN likes l ON l.post_id = p.id
                        LEFT JOIN post_tag pt ON p.id = pt.post_id
                        LEFT JOIN tag t ON t.id = pt.tag_id
                        WHERE p.id = ?
                        GROUP BY p.id, l.count;
                        """,
                (rs, rowNum) -> Post.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .imageUrl(rs.getString("image_url"))
                        .description(Arrays.stream((String[]) rs.getArray("description").getArray()).toList())
                        .tags(List.of(rs.getString("tags").split(" ")))
                        .likes(rs.getInt("likesCount"))
                        .comments(Utilities.arrayToList(rs.getArray("comments")))
                        .build(), id);

        //return list.size() == 1 ? Optional.of(list.getFirst()) : Optional.empty();
        return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();
    }

    @Override
    public void saveComment(CreateCommentDto createCommentDto) {
        jdbcTemplate.update(
                """
                        BEGIN;
                            INSERT INTO comment(post_comment, post_id)
                            VALUES
                            (?, ?);
                        
                            UPDATE post
                        	SET commentsCount = commentsCount + 1
                        	WHERE id = ?;
                        COMMIT;
                        """,
                createCommentDto.getCommentText(), createCommentDto.getPostId(), createCommentDto.getPostId()
        );
    }

    @Override
    public void addLike(Integer postId) {
        jdbcTemplate.update(
                """
                        INSERT INTO likes (count, post_id)
                        VALUES
                        (1, ?)
                        ON CONFLICT (post_id) DO UPDATE
                        SET count = likes.count + 1
                        WHERE likes.post_id = ?;
                        """,
                postId, postId
        );
    }

    @Override
    public void deleteComment(Integer commentId, Integer postId) {
        jdbcTemplate.update(
                """
                        BEGIN;
                        	DELETE FROM comment
                        	WHERE id = ?;
                        
                        	UPDATE post
                        	SET commentsCount = commentsCount - 1
                        	WHERE id = ?;
                        COMMIT;
                        """,
                commentId, postId
        );
    }

    @Override
    public void deletePost(Integer postId) {
        jdbcTemplate.update(
                """
                        DELETE FROM post
                        WHERE id = ?;
                        """,
                postId
        );
    }

    @Override
    public void updatePost(PostDto postDto) {
        List<SqlParameter> procedureParams = List.of(
                new SqlParameter("id", Types.INTEGER),
                new SqlParameter("post_name", Types.VARCHAR),
                new SqlParameter("image_url", Types.VARCHAR),
                new SqlParameter("description", Types.ARRAY),
                new SqlParameter("tags_array", Types.ARRAY)
        );

        jdbcTemplate.call(connection -> {
            Array sqlDescriptionArray = connection.createArrayOf("text", postDto.getDescription());
            Array sqlTagsArray = connection.createArrayOf("varchar", postDto.getTags());

            CallableStatement callableStatement = connection.prepareCall("call update_post(?, ?, ?, ?, ?)");
            callableStatement.setInt(1, postDto.getId());
            callableStatement.setString(2, postDto.getName());
            callableStatement.setString(3, postDto.getImageUrl());
            callableStatement.setArray(4, sqlDescriptionArray);
            callableStatement.setArray(5, sqlTagsArray);

            return callableStatement;
        }, procedureParams);
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        jdbcTemplate.update(
                """
                        UPDATE comment
                        SET post_comment = ?
                        WHERE id = ?;
                        """,
                commentDto.getPostComment(), commentDto.getId()
        );
    }
}
