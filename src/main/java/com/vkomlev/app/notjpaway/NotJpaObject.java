package com.vkomlev.app.notjpaway;

public class NotJpaObject {
    private String id;

    private  String objectValue;

    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String objectValue) {
        this.objectValue = objectValue;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        NotJpaObject that = (NotJpaObject) o;

        return id.equals(that.id);
    }

    @Override public int hashCode() {
        return id.hashCode();
    }
}
