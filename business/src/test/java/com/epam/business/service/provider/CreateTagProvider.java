package com.epam.business.service.provider;

import com.epam.business.model.request.CreateTagRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class CreateTagProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return entities().stream()
                .map(Arguments::of);
    }

    public static List<Set<CreateTagRequest>> entities() {
        List<Set<CreateTagRequest>> listOfRequest = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Set<CreateTagRequest> requests = new LinkedHashSet<>();
            for (int j = 1; j <= 50; j++) {
                CreateTagRequest request = new CreateTagRequest();
                request.setName("Tag #" + i + "." + j);
                requests.add(request);
            }
            listOfRequest.add(requests);
        }
        return listOfRequest;
    }
}


