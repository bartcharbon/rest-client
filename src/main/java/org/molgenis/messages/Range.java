package org.molgenis.messages;

/**
 * Created by fkelpin on 09/09/16.
 */
public class Range {
    private Long min;
    private Long max;

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (getMin() != null ? !getMin().equals(range.getMin()) : range.getMin() != null) return false;
        return getMax() != null ? getMax().equals(range.getMax()) : range.getMax() == null;

    }

    @Override
    public int hashCode() {
        int result = getMin() != null ? getMin().hashCode() : 0;
        result = 31 * result + (getMax() != null ? getMax().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Range{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
