package uz.pdp.relation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.relation.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
