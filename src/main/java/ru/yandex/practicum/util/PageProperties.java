package ru.yandex.practicum.util;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import ru.yandex.practicum.model.PostsFeed;

import java.util.List;

@Value
@Builder
public class PageProperties {
    @NonNull
    Integer page;
    @NonNull
    Integer size;
    @NonNull
    List<PostsFeed> postsFeedList;
    //@NonNull
    int postsCount;
//    @NonNull
//    boolean hasNext;
}

