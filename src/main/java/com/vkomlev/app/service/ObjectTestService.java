package com.vkomlev.app.service;

import com.vkomlev.app.dto.IncomingObjectTestDTO;
import com.vkomlev.app.dto.ReferencedObjectTestDTO;
import com.vkomlev.app.dto.ReferencingObjectTestDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface ObjectTestService {

    @Transactional ReferencedObjectTestDTO findObjectWithParents(String id, int depth);

    @Transactional ReferencingObjectTestDTO findObjectWithChildren(String id, int depth);

    @Transactional void saveTestObjects(List<IncomingObjectTestDTO> objects);
}
