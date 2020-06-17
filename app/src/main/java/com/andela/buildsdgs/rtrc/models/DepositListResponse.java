package com.andela.buildsdgs.rtrc.models;

import java.util.List;

public class DepositListResponse {
    private String next;
    private String previous;
    private String count;
    private List<DepositResult> results;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DepositResult> getResults() {
        return results;
    }

    public void setResults(List<DepositResult> results) {
        this.results = results;
    }
}
