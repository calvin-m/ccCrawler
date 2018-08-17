package com.calvinmai.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseData {
    @JsonProperty(value = "links")
    private List<String> links;

    public List<String> getLinks()
    {
        return this.links;
    }
    public void setLinks(List<String> links)
    {
        this.links = links;
    }

}
