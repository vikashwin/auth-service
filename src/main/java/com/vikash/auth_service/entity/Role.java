package com.vikash.auth_service.entity;

import com.vikash.auth_service.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate  sequence unique id
    @Column(
            name = "role_id",
            updatable = false
    )
    private Long id;


    @Column(
            name = "user_role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private UserRole name;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
