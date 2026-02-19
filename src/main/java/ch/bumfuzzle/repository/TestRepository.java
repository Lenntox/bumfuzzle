package ch.bumfuzzle.repository;

import ch.bumfuzzle.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
