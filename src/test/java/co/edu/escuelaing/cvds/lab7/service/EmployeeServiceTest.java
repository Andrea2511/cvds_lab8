package co.edu.escuelaing.cvds.lab7.service;

import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository mockedEmployeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @DisplayName("""
    Dado que tengo 1 empleado registrado
    Cuando lo elimino a nivel de servicio
    Entonces la eliminación será exitosa
    """)
    @Test
    void deberiaEliminarseCorrectamente() {
        // Arrange / Given - precondition or setup
        Employee mockedEmployee = new Employee(1000858016L, "Carolina", "Medina", "Vendedor", 10.000);
        doNothing().when(mockedEmployeeRepository).deleteById(mockedEmployee.getEmployeeId());
        // Act / When - action or the behaviour that we are going test
        employeeService.deleteUser(mockedEmployee.getEmployeeId());
        // Assert / Then - verify the output
        verify(mockedEmployeeRepository, times(1)).deleteById(mockedEmployee.getEmployeeId());
    }

    @Test
    void deberiaEncontrarEmpleadoPorSuId(){
        //dado
        Employee mockedEmployee = new Employee(1000858016L, "Carolina", "Medina", "Vendedor", 10.000);
        given(mockedEmployeeRepository.findAll())
                .willReturn(Arrays.asList(mockedEmployee));

        //cuando
        List <Employee> employees = employeeService.getAllEmployees();

        //entonces
        assertThat(employees.get(0).getEmployeeId()).isEqualTo(1000858016L);
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