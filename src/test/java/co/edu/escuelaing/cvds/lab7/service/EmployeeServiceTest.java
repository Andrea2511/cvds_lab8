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
import static org.mockito.BDDMockito.given;
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
    void getPremio() {
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

}