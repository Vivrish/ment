package com.example.authenticationservice.domain;

import com.example.authenticationservice.DTO.UserCredentialsDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user_credentials")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String password;



    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public User(String name, String password, Collection<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRole(Collection<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
    public void removeRole(Role role) {
        roles.remove(role);
    }

    public UserCredentialsDto toUserCredentialsDto() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(name, password);
        for (Role role: roles) {
            userCredentialsDto.addRole(role.toRoleDto());
        }
        return userCredentialsDto;
    }


}
