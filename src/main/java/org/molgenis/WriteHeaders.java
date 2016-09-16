package org.molgenis;

import org.molgenis.messages.Attribute;
import org.molgenis.messages.QueryResponse;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class WriteHeaders {
    public List<Map<String, String>> writeHeaders(QueryResponse response) {
        Map<String, String> headers = response
                .getMeta()
                .getAttributes()
                .stream()
                .map(Attribute::getName)
                .collect(toMap(identity(), identity(),
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        }, LinkedHashMap::new));
        return Collections.singletonList(headers);
    }
}
