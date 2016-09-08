package org.molgenis.messages;

import java.util.List;

public class Meta {

    private List<Attribute> attributes;

    private String href;

    private String hrefCollection;

    private String idAttribute;

    private String label;

    private String labelAttribute;

    private String languageCode;

    private List<String> lookupAttributes;

    private String name;

    private String description;

    private boolean writable;

    private boolean isAbstract;

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

    public boolean getIsAbstract() {
        return isAbstract;
    }

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
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

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meta meta = (Meta) o;

        if (getIsAbstract() != meta.getIsAbstract()) return false;
        if (isWritable() != meta.isWritable()) return false;
        if (getAttributes() != null ? !getAttributes().equals(meta.getAttributes()) : meta.getAttributes() != null)
            return false;
        if (getHref() != null ? !getHref().equals(meta.getHref()) : meta.getHref() != null) return false;
        if (getHrefCollection() != null ? !getHrefCollection().equals(meta.getHrefCollection()) : meta.getHrefCollection() != null)
            return false;
        if (getIdAttribute() != null ? !getIdAttribute().equals(meta.getIdAttribute()) : meta.getIdAttribute() != null)
            return false;
        if (getLabel() != null ? !getLabel().equals(meta.getLabel()) : meta.getLabel() != null) return false;
        if (getLabelAttribute() != null ? !getLabelAttribute().equals(meta.getLabelAttribute()) : meta.getLabelAttribute() != null)
            return false;
        if (getLanguageCode() != null ? !getLanguageCode().equals(meta.getLanguageCode()) : meta.getLanguageCode() != null)
            return false;
        if (getLookupAttributes() != null ? !getLookupAttributes().equals(meta.getLookupAttributes()) : meta.getLookupAttributes() != null)
            return false;
        if (getName() != null ? !getName().equals(meta.getName()) : meta.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(meta.getDescription()) : meta.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result = getAttributes() != null ? getAttributes().hashCode() : 0;
        result = 31 * result + (getHref() != null ? getHref().hashCode() : 0);
        result = 31 * result + (getHrefCollection() != null ? getHrefCollection().hashCode() : 0);
        result = 31 * result + (getIdAttribute() != null ? getIdAttribute().hashCode() : 0);
        result = 31 * result + (getIsAbstract() ? 1 : 0);
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        result = 31 * result + (getLabelAttribute() != null ? getLabelAttribute().hashCode() : 0);
        result = 31 * result + (getLanguageCode() != null ? getLanguageCode().hashCode() : 0);
        result = 31 * result + (getLookupAttributes() != null ? getLookupAttributes().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (isWritable() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "attributes=" + attributes +
                ", href='" + href + '\'' +
                ", hrefCollection='" + hrefCollection + '\'' +
                ", idAttribute='" + idAttribute + '\'' +
                ", isAbstract=" + isAbstract +
                ", label='" + label + '\'' +
                ", labelAttribute='" + labelAttribute + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", lookupAttributes=" + lookupAttributes +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", writable=" + writable +
                '}';
    }
}
