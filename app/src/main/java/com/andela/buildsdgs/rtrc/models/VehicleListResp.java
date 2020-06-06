package com.andela.buildsdgs.rtrc.models;

import java.util.List;

public class VehicleListResp {
    private String next;
    private String previous;
    private int count;
    private List<Vehicle> results;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Vehicle> getResults() {
        return results;
    }

    public void setResults(List<Vehicle> results) {
        this.results = results;
    }
}
