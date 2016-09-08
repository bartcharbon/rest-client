package org.molgenis.messages;

import java.util.List;

public class Attribute {
    private boolean aggregateable;
    private List<Attribute> attributes;
    private boolean auto;
    private String fieldType;
    private String href;
    private String label;
    private String description;
    private boolean labelAttribute;
    private boolean lookupAttribute;
    private int maxLength;
    private String name;
    private boolean nillable;
    private boolean readOnly;
    private boolean unique;
    private boolean visible;
    private Meta refEntity;
    private String defaultValue;
    private List<String> enumOptions;
    private String validationExpression;

    public boolean isAggregateable() {
        return aggregateable;
    }

    public void setAggregateable(boolean aggregateable) {
        this.aggregateable = aggregateable;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLabelAttribute() {
        return labelAttribute;
    }

    public void setLabelAttribute(boolean labelAttribute) {
        this.labelAttribute = labelAttribute;
    }

    public boolean isLookupAttribute() {
        return lookupAttribute;
    }

    public void setLookupAttribute(boolean lookupAttribute) {
        this.lookupAttribute = lookupAttribute;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNillable() {
        return nillable;
    }

    public void setNillable(boolean nillable) {
        this.nillable = nillable;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Meta getRefEntity() {
        return refEntity;
    }

    public void setRefEntity(Meta refEntity) {
        this.refEntity = refEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<String> getEnumOptions() {
        return enumOptions;
    }

    public void setEnumOptions(List<String> enumOptions) {
        this.enumOptions = enumOptions;
    }

    public String getValidationExpression() {
        return validationExpression;
    }

    public void setValidationExpression(String validationExpression) {
        this.validationExpression = validationExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (isAggregateable() != attribute.isAggregateable()) return false;
        if (isAuto() != attribute.isAuto()) return false;
        if (isLabelAttribute() != attribute.isLabelAttribute()) return false;
        if (isLookupAttribute() != attribute.isLookupAttribute()) return false;
        if (getMaxLength() != attribute.getMaxLength()) return false;
        if (isNillable() != attribute.isNillable()) return false;
        if (isReadOnly() != attribute.isReadOnly()) return false;
        if (isUnique() != attribute.isUnique()) return false;
        if (isVisible() != attribute.isVisible()) return false;
        if (getAttributes() != null ? !getAttributes().equals(attribute.getAttributes()) : attribute.getAttributes() != null)
            return false;
        if (getFieldType() != null ? !getFieldType().equals(attribute.getFieldType()) : attribute.getFieldType() != null)
            return false;
        if (getHref() != null ? !getHref().equals(attribute.getHref()) : attribute.getHref() != null) return false;
        if (getLabel() != null ? !getLabel().equals(attribute.getLabel()) : attribute.getLabel() != null) return false;
        if (getDescription() != null ? !getDescription().equals(attribute.getDescription()) : attribute.getDescription() != null)
            return false;
        if (getName() != null ? !getName().equals(attribute.getName()) : attribute.getName() != null) return false;
        if (getRefEntity() != null ? !getRefEntity().equals(attribute.getRefEntity()) : attribute.getRefEntity() != null)
            return false;
        if (getDefaultValue() != null ? !getDefaultValue().equals(attribute.getDefaultValue()) : attribute.getDefaultValue() != null)
            return false;
        if (getEnumOptions() != null ? !getEnumOptions().equals(attribute.getEnumOptions()) : attribute.getEnumOptions() != null)
            return false;
        return getValidationExpression() != null ? getValidationExpression().equals(attribute.getValidationExpression()) : attribute.getValidationExpression() == null;

    }

    @Override
    public int hashCode() {
        int result = (isAggregateable() ? 1 : 0);
        result = 31 * result + (getAttributes() != null ? getAttributes().hashCode() : 0);
        result = 31 * result + (isAuto() ? 1 : 0);
        result = 31 * result + (getFieldType() != null ? getFieldType().hashCode() : 0);
        result = 31 * result + (getHref() != null ? getHref().hashCode() : 0);
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (isLabelAttribute() ? 1 : 0);
        result = 31 * result + (isLookupAttribute() ? 1 : 0);
        result = 31 * result + getMaxLength();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (isNillable() ? 1 : 0);
        result = 31 * result + (isReadOnly() ? 1 : 0);
        result = 31 * result + (isUnique() ? 1 : 0);
        result = 31 * result + (isVisible() ? 1 : 0);
        result = 31 * result + (getRefEntity() != null ? getRefEntity().hashCode() : 0);
        result = 31 * result + (getDefaultValue() != null ? getDefaultValue().hashCode() : 0);
        result = 31 * result + (getEnumOptions() != null ? getEnumOptions().hashCode() : 0);
        result = 31 * result + (getValidationExpression() != null ? getValidationExpression().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "aggregateable=" + aggregateable +
                ", attributes=" + attributes +
                ", auto=" + auto +
                ", fieldType='" + fieldType + '\'' +
                ", href='" + href + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", labelAttribute=" + labelAttribute +
                ", lookupAttribute=" + lookupAttribute +
                ", maxLength=" + maxLength +
                ", name='" + name + '\'' +
                ", nillable=" + nillable +
                ", readOnly=" + readOnly +
                ", unique=" + unique +
                ", visible=" + visible +
                ", refEntity=" + refEntity +
                ", defaultValue='" + defaultValue + '\'' +
                ", enumOptions=" + enumOptions +
                ", validationExpression='" + validationExpression + '\'' +
                '}';
    }
}
