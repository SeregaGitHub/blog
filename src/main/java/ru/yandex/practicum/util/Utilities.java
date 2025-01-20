package ru.yandex.practicum.util;

import lombok.experimental.UtilityClass;

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
}
