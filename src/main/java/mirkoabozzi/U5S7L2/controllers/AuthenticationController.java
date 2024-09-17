package mirkoabozzi.U5S7L2.controllers;

import mirkoabozzi.U5S7L2.dto.EmployeeLoginDTO;
import mirkoabozzi.U5S7L2.dto.EmployeeLoginRespDTO;
import mirkoabozzi.U5S7L2.dto.EmployeesDTO;
import mirkoabozzi.U5S7L2.entities.Employee;
import mirkoabozzi.U5S7L2.exceptions.BadRequestException;
import mirkoabozzi.U5S7L2.services.AuthenticationService;
import mirkoabozzi.U5S7L2.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authentication") //endpoint predefinito per le autenticazioni
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private EmployeesService employeesService;

    //POST LOGIN
    @PostMapping("/login") //endpoint dedicato al login
    public EmployeeLoginRespDTO login(@RequestBody @Validated EmployeeLoginDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return new EmployeeLoginRespDTO(this.authenticationService.checkCredentialAndCreateToken(payload));
        }
    }

    //POST REGISTRAZIONE
    @PostMapping("/register")  //endpoint dedicato alla registrazione nuovo dipendente
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated EmployeesDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return this.employeesService.save(payload);
        }
    }
}
