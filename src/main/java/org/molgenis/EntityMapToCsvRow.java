package org.molgenis;

import org.apache.camel.Message;
import org.molgenis.messages.Attribute;
import org.molgenis.messages.Meta;
import org.molgenis.messages.QueryResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class EntityMapToCsvRow {
    public List<Map<String, Object>> convert(Message in) {
        QueryResponse queryResponse = (QueryResponse) in.getBody();
        return queryResponse
                .getItems()
                .stream()
                .map(entity -> toCsvRow(queryResponse
                        .getMeta()
                        .getAttributes(), entity))
                .collect(toList());
    }

    private Map<String, Object> toCsvRow(List<Attribute> attributes, Map<String, Object> entity) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Attribute attr : attributes) {
            String name = attr.getName();
            Object value = entity.get(name);
            if (value != null) {
                switch (attr.getFieldType()) {
                    case "XREF":
                    case "CATEGORICAL":
                        result.put(name, getAttributeValue(attr, (Map<String, Object>) value));
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

    private Object getAttributeValue(Attribute attr, Map<String, Object> value) {
        Map<String, Object> valueMap = value;
        Meta refEntity = attr.getRefEntity();
        Object idAttributeName = refEntity.getIdAttribute();
        return valueMap.get(idAttributeName);
    }

}
