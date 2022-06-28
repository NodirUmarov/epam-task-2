package com.epam.repository.dao.provider;

import com.epam.repository.model.entity.TagEntity;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class TagProvider implements ArgumentsProvider {

    private static final String[] tagNames = {"cruel", "model", "gaffe", "faith", "party", "error", "irony", "stamp", "horse", "aware",
            "waist", "route", "dough", "strap", "arrow", "table", "proud", "price", "seize", "rider", "build", "loose",
            "serve", "haunt", "trail", "offer", "Venus", "grave", "ditch", "clean", "wheel", "metal", "brave", "suite",
            "burst", "track", "creed", "field", "shift", "humor", "joint", "admit", "abbey", "chart", "throw", "lease",
            "tiger", "angle", "spill", "child", "hour", "herb", "left", "seat", "urge", "back", "wrap", "plan", "heal",
            "year", "swop", "feel", "mine", "fish", "coal", "sock", "neck", "calf", "horn", "crew", "hair", "desk", "hell",
            "rear", "book", "make", "text", "dine", "calm", "bulb", "path", "rest", "heat", "date", "jail", "rung", "room",
            "meet", "monk", "fire", "ring", "flow", "help", "duty", "soar", "mean", "hurt", "wolf", "know", "loud", "gem",
            "bet", "hut", "rib", "see", "sip", "pay", "bed", "tin", "job", "put", "top", "fee", "far", "cap", "add", "try",
            "way", "sin", "pop", "fan", "hay", "fox", "kit", "oak", "bus", "ego", "nun", "kid", "inn", "air", "fax", "jaw",
            "shy", "fog", "vat", "age", "win", "raw", "run", "egg", "nut", "pit", "cut", "era", "bat", "mud", "pot", "pan", "use"};

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return entities().stream()
                .map(Arguments::of);
    }

    public static Set<TagEntity> entities() {
        Random random = new Random();
        AtomicLong counter = new AtomicLong(1);
        return Stream.of(tagNames)
                .map(tagNames -> TagEntity.builder()
                        .id(counter.getAndIncrement())
                        .name(tagNames)
                        .build())
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }))
                .limit(2 + random.nextInt(tagNames.length - 2))
                .collect(Collectors.toSet());
    }
}


