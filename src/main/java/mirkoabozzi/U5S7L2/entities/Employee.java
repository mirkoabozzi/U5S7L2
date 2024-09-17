package mirkoabozzi.U5S7L2.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mirkoabozzi.U5S7L2.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    public Employee(String username, String name, String surname, String email, String avatar, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.role = Role.EMPLOYEE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name())); // ritorna lista ruoli utenti
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
