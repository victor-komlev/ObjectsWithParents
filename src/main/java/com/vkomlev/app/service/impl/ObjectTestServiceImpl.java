package com.vkomlev.app.service.impl;

import com.vkomlev.app.dto.IncomingObjectTestDTO;
import com.vkomlev.app.dto.ReferencedObjectTestDTO;
import com.vkomlev.app.dto.ReferencingObjectTestDTO;
import com.vkomlev.app.entity.ObjectTest;
import com.vkomlev.app.repository.ObjectsTestRepository;
import com.vkomlev.app.service.ObjectTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service public class ObjectTestServiceImpl implements ObjectTestService {
    @Autowired private ObjectsTestRepository objectsTestRepository;

    @Transactional @Override public ReferencedObjectTestDTO findObjectWithParents(String id, int depth) {
        ObjectTest objectTest = objectsTestRepository.findOne(id);
        return fromObjectTestToReferencedObjectDTO(objectTest, depth);
    }

    @Transactional @Override public ReferencingObjectTestDTO findObjectWithChildren(String id, int depth) {
        ObjectTest objectTest = objectsTestRepository.findOne(id);
        return fromObjectTestToReferencingObjectDTO(objectTest, depth);
    }

    @Transactional @Override public void saveTestObjects(List<IncomingObjectTestDTO> objects) {
        objects.stream().forEach(object -> {
            ObjectTest objectTest = new ObjectTest();
            objectTest.setId(object.getId());
            objectTest.setObjectValue(object.getValue());
            if (object.getDependedId() != null) {
                ObjectTest parent = objectsTestRepository.findOne(object.getDependedId());
                objectTest.setParent(parent);
            }
            objectsTestRepository.save(objectTest);
        });
    }

    private ReferencingObjectTestDTO fromObjectTestToReferencingObjectDTO(ObjectTest objectTest, int depth) {
        if (objectTest == null) {
            return null;
        }
        ReferencingObjectTestDTO referencingObjectTestDTO = new ReferencingObjectTestDTO();
        referencingObjectTestDTO.setId(objectTest.getId());
        referencingObjectTestDTO.setValue(objectTest.getObjectValue());
        if (depth > 0) {
            Set<ObjectTest> childs = objectTest.getChildren();
            if (childs.size() > 0) {
                referencingObjectTestDTO.setChildren(new HashSet<>());
                int newDepth = --depth;
                childs.stream().forEach(child -> referencingObjectTestDTO.getChildren()
                        .add(fromObjectTestToReferencingObjectDTO(child, newDepth)));
            }
        }
        return referencingObjectTestDTO;
    }

    private ReferencedObjectTestDTO fromObjectTestToReferencedObjectDTO(ObjectTest objectTest, int depth) {
        if (objectTest == null) {
            return null;
        }
        ReferencedObjectTestDTO referencedObjectTestDTO = new ReferencedObjectTestDTO();
        referencedObjectTestDTO.setId(objectTest.getId());
        referencedObjectTestDTO.setValue(objectTest.getObjectValue());
        if (depth > 0) {
            referencedObjectTestDTO.setParent(fromObjectTestToReferencedObjectDTO(objectTest.getParent(), --depth));
        }
        return referencedObjectTestDTO;
    }
}
