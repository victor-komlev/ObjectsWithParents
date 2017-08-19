package com.vkomlev.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ReferencedObjectTestDTO {
    private String id;
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ReferencedObjectTestDTO parent;

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

    public ReferencedObjectTestDTO getParent() {
        return parent;
    }

    public void setParent(ReferencedObjectTestDTO parent) {
        this.parent = parent;
    }

}
