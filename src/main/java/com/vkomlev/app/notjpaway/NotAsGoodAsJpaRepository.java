package com.vkomlev.app.notjpaway;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository public class NotAsGoodAsJpaRepository {

    private Map<String, NotJpaObject> objectsById = new ConcurrentHashMap<>();
    private Map<String, Set<String>> childrenByParentId = new ConcurrentHashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void save(NotJpaObject objectToSave) {
        try {
            readWriteLock.writeLock().lock();
            objectsById.put(objectToSave.getId(), objectToSave);
            Set<String> children = childrenByParentId.get(objectToSave.getParentId());
            if (children == null) {
                children = new HashSet<>();
            }
            children.add(objectToSave.getId());
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public List<NotJpaObject> getObjectChildren(NotJpaObject parent) {
        try {
            readWriteLock.readLock().lock();
            List<NotJpaObject> children = new ArrayList<>();
            childrenByParentId.get(parent.getId());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
