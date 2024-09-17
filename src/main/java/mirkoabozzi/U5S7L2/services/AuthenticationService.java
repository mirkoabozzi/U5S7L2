package mirkoabozzi.U5S7L2.services;

import mirkoabozzi.U5S7L2.dto.EmployeeLoginDTO;
import mirkoabozzi.U5S7L2.entities.Employee;
import mirkoabozzi.U5S7L2.exceptions.UnauthorizedException;
import mirkoabozzi.U5S7L2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private EmployeesService employeesService; //importiamo il service dei dipendenti
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String checkCredentialAndCreateToken(EmployeeLoginDTO payload) {
        Employee found = this.employeesService.findByEmail(payload.email());  //cerco il dipendente tramite email
        if (passwordEncoder.matches(payload.password(), found.getPassword())) { // controllo se la password inserita corrisponde a quella presente nel DB
            return this.jwtTools.generateToken(found); // tramite il metodo dei JWTTools genero un nuovo token
        } else {
            throw new UnauthorizedException("Incorrect credentials");  // se non passo le condizioni lancio un'eccezione
        }
    }
}
