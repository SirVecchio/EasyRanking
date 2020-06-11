package me.kaotich00.easyranking.utils;

import java.util.*;
import java.util.stream.Collectors;

public class SortUtil {

    public static boolean ASC = true;
    public static boolean DESC = false;

    public static Map<UUID, Float> sortByValue(Map<UUID, Float> unsortMap, final boolean order)
    {
        List<Map.Entry<UUID, Float>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

}
