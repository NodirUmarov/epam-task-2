package com.epam.repository.dao.provider;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/28/2022
 */
public class CollectorUtils {
    public static <T> Collector<T, ?, Stream<T>> toShuffledStream() {
        return Collectors.collectingAndThen(Collectors.toList(), collected -> {
            Collections.shuffle(collected);
            return collected.stream();
        });
    }
}
