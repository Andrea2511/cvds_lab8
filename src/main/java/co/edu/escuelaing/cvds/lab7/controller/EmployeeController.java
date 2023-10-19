package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleados")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Mostrar el formulario para agregar un empleado
    @GetMapping("/agregar")
    public String mostrarFormularioAgregarEmpleado(Model model) {
        model.addAttribute("empleado", new Employee());
        return "CREATE";
    }
    // Procesar el formulario y agregar el empleado
    @PostMapping("/agregar")
    public String agregarEmpleado(Employee empleado) {
        employeeService.createEmployee(empleado);
        return "redirect:/empleados/lista"; // Redirigir a la lista de empleados
    }

    // Mostrar la lista de empleados
    @GetMapping("/lista")
    public String mostrarListaEmpleados(Model model) {
        model.addAttribute("empleados", employeeService.getAllEmployees());
        return "READ";
    }


    @GetMapping("/editar/{employeeId}")
    public String loadUpdateEmployeeForm(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "UPDATE";
    }

    @PostMapping("/editar/{employeeId}")
    public String updateEmployee(@PathVariable Long employeeId, @ModelAttribute Employee updatedEmployee) {
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);
        if (existingEmployee == null) {
            return "redirect:/empleados/lista"; 
        }

        employeeService.updateUser(updatedEmployee);
          return "redirect:/empleados/lista"; 
    }

    @PostMapping("/eliminar/{employeeId}")
    public String deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteUser(employeeId);
        return "redirect:/empleados/lista";
    }
}
