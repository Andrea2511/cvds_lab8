package co.edu.escuelaing.cvds.lab7.repository;

import co.edu.escuelaing.cvds.lab7.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
