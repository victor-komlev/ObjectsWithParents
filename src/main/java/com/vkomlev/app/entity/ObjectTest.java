package com.vkomlev.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "objects_test")
public class ObjectTest {
    @Id
    private String id;

    @Column(name = "object_value")
    private  String objectValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ObjectTest parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<ObjectTest> children;

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

    public ObjectTest getParent() {
        return parent;
    }

    public void setParent(ObjectTest parent) {
        this.parent = parent;
    }

    public Set<ObjectTest> getChildren() {
        return children;
    }

    public void setChildren(Set<ObjectTest> children) {
        this.children = children;
    }
}
