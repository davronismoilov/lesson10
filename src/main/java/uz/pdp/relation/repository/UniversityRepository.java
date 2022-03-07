package uz.pdp.relation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.relation.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
}
