package com.vkomlev.app.service.impl;

import com.vkomlev.app.dto.IncomingObjectTestDTO;
import com.vkomlev.app.dto.ReferencedObjectTestDTO;
import com.vkomlev.app.dto.ReferencingObjectTestDTO;
import com.vkomlev.app.entity.ObjectTest;
import com.vkomlev.app.repository.ObjectsTestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class) public class TestObjectTestServiceImpl {

    @InjectMocks private ObjectTestServiceImpl objectTestService;

    @Mock private ObjectsTestRepository mockObjectsTestRepository;

    @Test public void findObjectWithParents() throws Exception {
        ObjectTest objectTest = createTestObject(10);
        expectRepositoryFindById(objectTest.getId(), objectTest);
        ReferencedObjectTestDTO objectWithParents = objectTestService.findObjectWithParents(objectTest.getId(), 5);
        assertNotNull(objectWithParents);
        AtomicInteger depth = new AtomicInteger(0);
        measureDepthOfParents(depth, objectWithParents);
        assertEquals(5, depth.get());
    }

    private void measureDepthOfParents(AtomicInteger depth, ReferencedObjectTestDTO objectWithParents) {
        if (objectWithParents.getParent() != null) {
            depth.incrementAndGet();
            measureDepthOfParents(depth, objectWithParents.getParent());
        }
    }

    private void measureDepthOfChildern(AtomicInteger depth, ReferencingObjectTestDTO objectWithChildren) {
        if (objectWithChildren.getChildren() != null && !objectWithChildren.getChildren().isEmpty()) {
            depth.incrementAndGet();
            // TODO: This works because every object has only one child.
            objectWithChildren.getChildren().forEach(child -> measureDepthOfChildern(depth, child));
        }
    }

    @Test public void findObjectWithChildren() throws Exception {
        ObjectTest objectTest = createTestObject(10);
        expectRepositoryFindById(objectTest.getId(), objectTest);
        ReferencingObjectTestDTO objectWithChildren = objectTestService.findObjectWithChildren(objectTest.getId(), 5);
        assertNotNull(objectWithChildren);
        AtomicInteger depth = new AtomicInteger(0);
        measureDepthOfChildern(depth, objectWithChildren);
        assertEquals(5, depth.get());
    }

    @Test public void saveTestObjects() throws Exception {
        List<IncomingObjectTestDTO> incomingObjectTestDTO = createIncomingObjects(10);
        objectTestService.saveTestObjects(incomingObjectTestDTO);
        expectRepositorySave(10);
    }

    private void expectRepositorySave(int i) {
        Mockito.verify(mockObjectsTestRepository, Mockito.times(10)).save(Mockito.any(ObjectTest.class));
    }

    private List<IncomingObjectTestDTO> createIncomingObjects(int count) {
        return new ArrayList<IncomingObjectTestDTO>() {{
            for (int i = 0; i < count; i++) {
                IncomingObjectTestDTO incomingObjectTestDTO = new IncomingObjectTestDTO();
                incomingObjectTestDTO.setId(String.valueOf(new Random().nextInt()));
                incomingObjectTestDTO.setValue("DATA " + String.valueOf(new Random().nextInt()));
                incomingObjectTestDTO.setDependedId(String.valueOf(new Random().nextInt()));
                add(incomingObjectTestDTO);
            }
        }};
    }

    private ObjectTest createTestObject(int depth) {
        ObjectTest objectTest = new ObjectTest();
        objectTest.setId(String.valueOf(new Random().nextInt()));
        objectTest.setObjectValue("DATA " + String.valueOf(new Random().nextInt()));
        if (depth > 0) {
            objectTest.setParent(createTestObject(depth - 1));
            objectTest.setChildren(new HashSet<ObjectTest>() {{
                add(createTestObject(depth - 1));
            }});
        }
        return objectTest;
    }

    private void expectRepositoryFindById(String id, ObjectTest objectTest) {
        Mockito.when(mockObjectsTestRepository.findOne(id)).thenReturn(objectTest);
    }

}