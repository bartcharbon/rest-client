package org.molgenis.messages;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class Meta {

    private String href;
    private String hrefCollection;
    private String name;
    private String label;
    private String description;
    private List<Attribute> attributes;
    private String labelAttribute;
    private String idAttribute;
    private List<String> lookupAttributes;
    private Boolean isAbstract;
    private Boolean writable;
    private String languageCode;

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getHrefCollection() {
        return hrefCollection;
    }

    public void setHrefCollection(String hrefCollection) {
        this.hrefCollection = hrefCollection;
    }

    public String getIdAttribute() {
        return idAttribute;
    }

    public void setIdAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelAttribute() {
        return labelAttribute;
    }

    public void setLabelAttribute(String labelAttribute) {
        this.labelAttribute = labelAttribute;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public List<String> getLookupAttributes() {
        return lookupAttributes;
    }

    public void setLookupAttributes(List<String> lookupAttributes) {
        this.lookupAttributes = lookupAttributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public Boolean getIsAbstract() {
        return isAbstract;
    }

    public void setIsAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meta meta = (Meta) o;

        if (attributes != null ? !attributes.equals(meta.attributes) : meta.attributes != null) return false;
        if (href != null ? !href.equals(meta.href) : meta.href != null) return false;
        if (hrefCollection != null ? !hrefCollection.equals(meta.hrefCollection) : meta.hrefCollection != null)
            return false;
        if (idAttribute != null ? !idAttribute.equals(meta.idAttribute) : meta.idAttribute != null) return false;
        if (label != null ? !label.equals(meta.label) : meta.label != null) return false;
        if (labelAttribute != null ? !labelAttribute.equals(meta.labelAttribute) : meta.labelAttribute != null)
            return false;
        if (languageCode != null ? !languageCode.equals(meta.languageCode) : meta.languageCode != null) return false;
        if (lookupAttributes != null ? !lookupAttributes.equals(meta.lookupAttributes) : meta.lookupAttributes != null)
            return false;
        if (name != null ? !name.equals(meta.name) : meta.name != null) return false;
        if (description != null ? !description.equals(meta.description) : meta.description != null) return false;
        if (writable != null ? !writable.equals(meta.writable) : meta.writable != null) return false;
        return isAbstract != null ? isAbstract.equals(meta.isAbstract) : meta.isAbstract == null;

    }

    @Override
    public int hashCode() {
        int result = attributes != null ? attributes.hashCode() : 0;
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (hrefCollection != null ? hrefCollection.hashCode() : 0);
        result = 31 * result + (idAttribute != null ? idAttribute.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (labelAttribute != null ? labelAttribute.hashCode() : 0);
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        result = 31 * result + (lookupAttributes != null ? lookupAttributes.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (writable != null ? writable.hashCode() : 0);
        result = 31 * result + (isAbstract != null ? isAbstract.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "attributes=" + attributes +
                ", href='" + href + '\'' +
                ", hrefCollection='" + hrefCollection + '\'' +
                ", idAttribute='" + idAttribute + '\'' +
                ", label='" + label + '\'' +
                ", labelAttribute='" + labelAttribute + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", lookupAttributes=" + lookupAttributes +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", writable=" + writable +
                ", isAbstract=" + isAbstract +
                '}';
    }

    public Map<String, String> toCsv() {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        List<String> nameParts = newArrayList(getName().split("_"));

        builder.put("name", nameParts.get(nameParts.size() - 1));
        builder.put("package", nameParts
                .subList(0, nameParts.size() - 1)
                .stream()
                .collect(Collectors.joining("_")));
        builder.put("label", getLabel());
        if (getDescription() != null) {
            builder.put("description", getDescription());
        }
        if (getIsAbstract() != null) {
            builder.put("abstract", Boolean.toString(getIsAbstract()));
        }
        //TODO: tags!
        return builder.build();
    }

    public static List<List<String>> createHeader() {
        return Collections.singletonList(newArrayList("name", "package", "label", "description", "abstract"));
    }
}
