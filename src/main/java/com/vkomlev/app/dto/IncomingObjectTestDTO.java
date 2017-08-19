package com.vkomlev.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IncomingObjectTestDTO {
    private String id;
    private String value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dependent_id")
    private String dependedId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDependedId() {
        return dependedId;
    }

    public void setDependedId(String dependedId) {
        this.dependedId = dependedId;
    }
}

