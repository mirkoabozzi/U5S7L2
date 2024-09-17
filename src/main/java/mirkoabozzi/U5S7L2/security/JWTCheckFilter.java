package mirkoabozzi.U5S7L2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mirkoabozzi.U5S7L2.entities.Employee;
import mirkoabozzi.U5S7L2.exceptions.UnauthorizedException;
import mirkoabozzi.U5S7L2.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private EmployeesService employeesService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization"); //cerco nell header l'autorizzazione
        if (header == null || !header.startsWith("Bearer ")) // verifico la presenza della stringa Bearer prima del token quindi se non ! inizia con Bearer o se è null
            throw new UnauthorizedException("Token required"); // lancio l'eccezione se non passo le due condizioni
        String token = header.substring(7); // rimuovo i primi 7 caratteri contenenti Bearer + spazio vuoto
        jwtTools.verifyToken(token); //verifico il token col metodo creato in JWTTools
        String id = jwtTools.extractIdFromToken(token); // estraggo l'id del token che arriva nella richiesta http tramite il metodo creato in JWTTools
        Employee found = this.employeesService.findById(UUID.fromString(id)); //trovo nel DB il dipendente tramite id appena estratto
        Authentication authentication = new UsernamePasswordAuthenticationToken(found, null, found.getAuthorities()); //associo l'utente trovato alla richiesta corrente
        SecurityContextHolder.getContext().setAuthentication(authentication); //associo l'utente autenticato al context
        filterChain.doFilter(request, response); // applico i filtri
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/authentication/**", request.getServletPath()); // escludo dal filtro i path che contengono /authentication all'interno
    }
}
