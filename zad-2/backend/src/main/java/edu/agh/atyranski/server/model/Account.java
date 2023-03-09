package edu.agh.atyranski.server.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String account;

    private String interval;

    public String getAccount() {
        return account;
    }

    public String getInterval() {
        return interval;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!other.getClass().equals(this.getClass())) return false;

        Account otherAccount = (Account) other;
        return  Objects.equals(account, otherAccount.getAccount()) &&
                Objects.equals(interval, otherAccount.getInterval());
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, interval);
    }

    @Override
    public String toString() {
        return String.format("[Account] login: %s | interval: %s",
                account, interval);
    }
}
