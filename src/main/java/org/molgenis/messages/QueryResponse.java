package org.molgenis.messages;

import java.util.List;
import java.util.Map;

public class QueryResponse {

    private String href;

    private int start;

    private int num;

    private int total;

    private String prevHref;

    private String nextHref;

    private List<Map<String, Object>> items;

    private Meta meta;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPrevHref() {
        return prevHref;
    }

    public void setPrevHref(String prevHref) {
        this.prevHref = prevHref;
    }

    public String getNextHref() {
        return nextHref;
    }

    public void setNextHref(String nextHref) {
        this.nextHref = nextHref;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryResponse that = (QueryResponse) o;

        if (getStart() != that.getStart()) return false;
        if (getNum() != that.getNum()) return false;
        if (getTotal() != that.getTotal()) return false;
        if (getHref() != null ? !getHref().equals(that.getHref()) : that.getHref() != null) return false;
        if (getPrevHref() != null ? !getPrevHref().equals(that.getPrevHref()) : that.getPrevHref() != null)
            return false;
        if (getNextHref() != null ? !getNextHref().equals(that.getNextHref()) : that.getNextHref() != null)
            return false;
        if (getItems() != null ? !getItems().equals(that.getItems()) : that.getItems() != null) return false;
        return getMeta() != null ? getMeta().equals(that.getMeta()) : that.getMeta() == null;

    }

    @Override
    public int hashCode() {
        int result = getHref() != null ? getHref().hashCode() : 0;
        result = 31 * result + getStart();
        result = 31 * result + getNum();
        result = 31 * result + getTotal();
        result = 31 * result + (getPrevHref() != null ? getPrevHref().hashCode() : 0);
        result = 31 * result + (getNextHref() != null ? getNextHref().hashCode() : 0);
        result = 31 * result + (getItems() != null ? getItems().hashCode() : 0);
        result = 31 * result + (getMeta() != null ? getMeta().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueryResponse{" +
                "href='" + href + '\'' +
                ", start=" + start +
                ", num=" + num +
                ", total=" + total +
                ", prevHref='" + prevHref + '\'' +
                ", nextHref='" + nextHref + '\'' +
                ", items=" + items +
                ", meta=" + meta +
                '}';
    }
}