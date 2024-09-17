package mirkoabozzi.U5S7L2.controllers;

import mirkoabozzi.U5S7L2.dto.EmployeesDTO;
import mirkoabozzi.U5S7L2.entities.Employee;
import mirkoabozzi.U5S7L2.exceptions.BadRequestException;
import mirkoabozzi.U5S7L2.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private EmployeesService employeesService;


    //GET
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Employee> getEmployees(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "surname") String sortBy) {
        return this.employeesService.findAll(page, size, sortBy);
    }

    //GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findById(@PathVariable UUID id) {
        return employeesService.findById(id);
    }

    //PUT
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee update(@PathVariable UUID id, @RequestBody @Validated EmployeesDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return employeesService.update(id, payload);
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.employeesService.delete(id);
    }

    //POST IMG
    @PostMapping("/avatar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void imgUpload(@RequestParam("avatar") MultipartFile img, @PathVariable UUID id) throws IOException, MaxUploadSizeExceededException {
        this.employeesService.imgUpload(img, id);
    }

    //GET CON ENDPOINT DEDICATO ALL'UTENTE LOGGATO, TORNA IL SUO PROFILO
    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal Employee employeeAuthenticated) {
        return employeeAuthenticated;
    }

    // PUT AGGIORNA PROFILO
    @PutMapping("/me")
    public Employee updateProfile(@AuthenticationPrincipal Employee employeeAuthenticated, @RequestBody EmployeesDTO payload) {
        return this.employeesService.update(employeeAuthenticated.getId(), payload);
    }

    //POST ME IMG
    @PostMapping("/me/avatar")
    public void imgUploadME(@RequestParam("avatar") MultipartFile img, @AuthenticationPrincipal Employee employeeAuthenticated) throws IOException, MaxUploadSizeExceededException {
        this.employeesService.imgUpload(img, employeeAuthenticated.getId());
    }

    //DELETE ELIMINA MIO PROFILO
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyProfile(@AuthenticationPrincipal Employee employeeAuthenticated) {
        this.employeesService.delete(employeeAuthenticated.getId());
    }


}
