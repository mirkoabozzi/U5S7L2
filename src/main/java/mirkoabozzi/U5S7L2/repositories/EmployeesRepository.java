package mirkoabozzi.U5S7L2.repositories;

import mirkoabozzi.U5S7L2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);

    //Utilizzerò questa query per la ricerca del dipendente tramite email
    Optional<Employee> findByEmail(String email);
}
