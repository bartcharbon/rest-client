package org.molgenis;

import org.apache.camel.Message;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class EntityMapToCsvRow {
    public List<Map<String, Object>> convert(Message in) {
        List<Map<String, Object>> attributes = (List<Map<String, Object>>) in.getHeader("attributes");
        List<Map<String, Object>> entities = (List<Map<String, Object>>) in.getBody();
        return entities
                .stream()
                .map(entity -> apply(attributes, entity))
                .collect(toList());
    }

    private Map<String, Object> apply(List<Map<String, Object>> attributes, Map<String, Object> entity) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map<String, Object> attr : attributes) {
            String name = (String) attr.get("name");
            Object value = entity.get(name);
            if (value != null) {
                switch ((String) attr.get("fieldType")) {
                    case "XREF":
                    case "CATEGORICAL":
                        result.put(name, getAttributeValue(attr, (Map<String, Object>) value));
                        break;
                    case "INT":
                        result.put(name, (int) (double) value);
                        break;
                    case "LONG":
                        result.put(name, (long) (double) value);
                        break;
                    case "MREF":
                    case "CATEGORICAL_MREF":
                        List<Map<String, Object>> values = (List<Map<String, Object>>) value;
                        result
                                .put(name, values
                                        .stream()
                                        .map(v -> getAttributeValue(attr, v).toString()) // TODO: don't tostring an int id in an mref
                                        .collect(Collectors.joining(",")));
                        break;
                    default:
                        result.put(name, value);
                }
            }

        }
        return result;
    }

    private Object getAttributeValue(Map<String, Object> attr, Map<String, Object> value) {
        Map<String, Object> valueMap = value;
        Map<String, Object> refEntity = (Map<String, Object>) (attr.get("refEntity"));
        Object idAttributeName = refEntity.get("idAttribute");
        return valueMap.get(idAttributeName);
    }

}
