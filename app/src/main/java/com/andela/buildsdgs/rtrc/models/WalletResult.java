package com.andela.buildsdgs.rtrc.models;

import com.google.gson.annotations.SerializedName;

public class WalletResult {
    private String balance;
    @SerializedName("total_deposits")
    private String totalDeposits;
    @SerializedName("total_transactions")
    private String totalTransactions;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;
    private String id;
    private User user;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTotalDeposits() {
        return totalDeposits;
    }

    public void setTotalDeposits(String totalDeposits) {
        this.totalDeposits = totalDeposits;
    }

    public String getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(String totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
