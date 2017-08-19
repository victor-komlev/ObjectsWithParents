package com.vkomlev.app.repository;

import com.vkomlev.app.entity.ObjectTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectsTestRepository extends JpaRepository<ObjectTest, String> {
}
