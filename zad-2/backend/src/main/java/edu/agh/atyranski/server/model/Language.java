package edu.agh.atyranski.server.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Language {

    private String language;
    private Integer amount;

    public String getLanguage() {
        return language;
    }

    public Integer getAmount() {
        return amount;
    }

    public Language setLanguage(String language) {
        this.language = language;
        return this;
    }

    public Language setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
