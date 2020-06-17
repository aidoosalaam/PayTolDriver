package com.andela.buildsdgs.rtrc.models;

public class WalletResponse {
    private String next;
    private String previous;
    private String count;
    private WalletResult[] results;

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

    public WalletResult[] getResults() {
        return results;
    }

    public void setResults(WalletResult[] results) {
        this.results = results;
    }
}
