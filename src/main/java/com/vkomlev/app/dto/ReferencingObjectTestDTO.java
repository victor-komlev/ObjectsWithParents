package com.vkomlev.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

public class ReferencingObjectTestDTO {

    private String id;
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ReferencingObjectTestDTO> children;

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
    public Set<ReferencingObjectTestDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<ReferencingObjectTestDTO> children) {
        this.children = children;
    }

}
