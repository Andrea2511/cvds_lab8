package co.edu.escuelaing.cvds.lab7;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.repository.EmployeeRepository;
import co.edu.escuelaing.cvds.lab7.service.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class Lab7ApplicationTest {

    @Mock
    private EmployeeRepository mockedEmployeeRepository;

    @InjectMocks
    private EmployeeService employeeService;
    private Employee employee;

    /* 
	 * Dado que tengo 1 empleado registrado
	 * Cuando lo consulto a nivel de servicio
	 * Entonces la consulta será existosa validando el campo id
	 */
    @Test

    void deberiaEncontrarEmpleadoPorSuId(){
        //dado
        Employee mockedEmployee = new Employee(1000858016L, "Carolina", "Medina", "Vendedor", 10.000);
        List <Employee> mockedEmployees =new ArrayList<>();
        mockedEmployees.add(0, mockedEmployee);

        //cuando
        given(mockedEmployeeRepository.findAll())
                .willReturn(mockedEmployees);
        List <Employee> employees = employeeService.getAllEmployees();

        //entonces
        assertThat(employees.get(0).getEmployeeId()).isEqualTo(1000858016L);
    }

    /*
     * Dado que no hay ningún empleado registrado
     * Cuándo lo consulto a nivel de servicio
     * Entonces la consulta no retornará ningún resultado.
     */
    @Test
    void deberiaNoRetornarAlgunResultadoSiNohayUnEmpleadoRegistrado(){

        //dado
        List <Employee> mockedEmployees =new ArrayList<>();

        given(mockedEmployeeRepository.findAll()).willReturn(mockedEmployees);
        //cuando
        mockedEmployees = employeeService.getAllEmployees();

        //entonces
        assertTrue(mockedEmployees.isEmpty());
    }

    /*
     * Dado que no hay ningún empleado registrado,
     * Cuándo lo creo a nivel de servicio,
     * Entonces la creación será exitosa.
     */
    @Test
    void deberiaCrearseElEmpleadoaNivelDeServicio(){

        //dado
        Employee mockedEmployee = new Employee(1000858016L, "Carolina", "Medina", "Vendedor", 10.000);
        List <Employee> mockedEmployees =new ArrayList<>();
        mockedEmployees.add(0, mockedEmployee);

        // Configura el comportamiento del repositorio Mock para la creación y eliminación de empleados
        given(mockedEmployeeRepository.findAll()).willReturn(mockedEmployees);
        
        mockedEmployees = employeeService.getAllEmployees();

        assertTrue(mockedEmployees.get(0).getEmployeeId().equals(mockedEmployee.getEmployeeId()));

    }

    /*
     * Dado que tengo 1 empleado registrado,
     * Cuándo lo elimino a nivel de servicio,
     * Entonces la eliminación será exitosa.
     */
    @Test
    void deberiaEliminarseCorrectamente() {

        // Dado que tengo 1 empleado registrado
        // Mockear la base de datos para que tenga un solo empleado
        Employee mockedEmployee = new Employee(1000858016L, "Carolina", "Medina", "Vendedor", 10.000);
        List <Employee> mockedEmployees = new ArrayList<>();
        mockedEmployees.add(0, mockedEmployee);

        //willDoNothing().given(mockedEmployeeRepository).deleteById(mockedEmployee.getEmployeeId());
        doNothing().when(mockedEmployeeRepository).deleteById(mockedEmployee.getEmployeeId());

        // Cuando lo elimino a nivel de servicio
        // Llamar la función eliminar empleado que está a nivel de servicio
        employeeService.deleteUser(mockedEmployee.getEmployeeId());

        // Entonces la eliminación será exitosa
        // Verificar que la función sea llamada una vez
        verify(mockedEmployeeRepository, times(1)).deleteById(mockedEmployee.getEmployeeId());

    }

    /*
     * Dado que tengo 1 empleado registrado,
     *  Cuándo lo elimino y consulto a nivel de servicio,
     * Entonces el resultado de la consulta no retornará ningún resultado.
     *
     */
    @Test
    void deberiaEliminarYNoRetornarResultadosDespues() {

        // Dado que tengo 1 empleado registrado
        Employee mockedEmployee = new Employee(1000858016L, "Carolina", "Medina", "Vendedor", 10.000);

        // Configura el comportamiento del repositorio Mock para la creación y eliminación de empleados
        given(mockedEmployeeRepository.save(mockedEmployee)).willReturn(mockedEmployee);
        willDoNothing().given(mockedEmployeeRepository).deleteById(mockedEmployee.getEmployeeId());

        // Llama al método de servicio para crear un empleado y eliminarlo
        Employee savedEmployee = employeeService.createEmployee(mockedEmployee);


        employeeService.deleteUser(savedEmployee.getEmployeeId());

        // Verifica que el empleado se haya eliminado correctamente
        verify(mockedEmployeeRepository, times(1)).deleteById(savedEmployee.getEmployeeId());

        // Llama al método de servicio para obtener la lista de empleados después de la eliminación
        List<Employee> employeesAfterDeletion = employeeService.getAllEmployees();

        // Verifica que la lista esté vacía
        assertTrue(employeesAfterDeletion.isEmpty());
    }

}

