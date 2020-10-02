package com.chatbot.permit.municipal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class Results {
    private List<AddressResult> results;

    public List<AddressResult> getResults() {
        return results;
    }

    public void setResults(List<AddressResult> results) {
        this.results = results;
    }
}
