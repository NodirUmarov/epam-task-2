package com.epam.repository.dao;

import com.epam.repository.model.entity.TagEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class TagEntityProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return entities().stream()
                .map(Arguments::of);
    }


    static List<TagEntity> entities() {
        String[] tagNames = {"cruel", "model", "gaffe", "faith", "party", "error", "irony",
                "stamp", "horse", "aware", "waist", "route", "dough", "strap",
                "arrow", "table", "proud", "price", "seize", "rider", "build",
                "loose", "serve", "haunt", "trail", "offer", "Venus", "grave",
                "ditch", "clean", "wheel", "metal", "brave", "suite", "burst",
                "track", "creed", "field", "shift", "humor", "joint", "admit",
                "abbey", "chart", "throw", "lease", "tiger", "angle", "spill", "child" };
        List<TagEntity> entities = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            entities.add(TagEntity.builder()
                    .id((long) i)
                    .name(tagNames[i - 1])
                    .build());
        }
        return entities;
    }
}


