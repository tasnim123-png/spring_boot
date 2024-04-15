package com.example.pfe.Employé.Entity;



import com.example.pfe.Compétence.Skill;
import com.example.pfe.Employé.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Employés")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Matricule")
    private Integer Matricule ;

    @Column(name="Nom",nullable = false)
    private String Nom;

    @Column(name="Prénom",nullable = false)
    private String Prénom;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="Email",nullable = false)
    private String Email;

    @Column(name="Poste",nullable = false)
    private String Poste ;

    @Column(name="Lieu_de_travail",nullable = false)
    private String Lieu_de_travail;

    @Column(name="Département",nullable = false)
    private String Département ;

    @Column(name="Grade",nullable = false)
    private Integer Grade;

    @Column(name="Note",nullable = false)
    private String Note ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String username;



    //These methods define the user's authorities, account status, and account enabling status required by Spring Security for authentication and authorization.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())) ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

