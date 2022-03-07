package uz.pdp.relation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.relation.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

}
