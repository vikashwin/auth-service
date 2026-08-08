package com.vikash.auth_service.entity;

import com.vikash.auth_service.enums.UserGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_name_dob",
                        columnNames = {"user_name", "date_of_birth"})
        },     //It can create a constraints where name+dob are always unique
        indexes = {
                @Index(
                        name = "idx_user_dob",
                        columnList = "date_of_birth"
                ),
                @Index(
                        name = "idx_user_email",
                        columnList = "user_email"
                )

        } // It is creating a new table that help to find user by age rapidly

)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate  sequence unique id
    @Column(name = "user_id", updatable = false)
    private Long id;


    @Column(name = "user_name", nullable = false)
    @NotBlank(message = "Name is required") // they also reject "  " not only null
    private String name;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email", nullable = false, unique = true)
    @Email(message = "Please enter a valid email")
    @NotBlank
    private String email;

    @Column(name = "user_number",
            nullable = false, // means null not store
            unique = true
    )
    @NotBlank
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender" , nullable = false )
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "profile_image")
    private String profileImage;


    @Builder.Default
    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Builder.Default
    @Column(name = "enabled")
    private Boolean enabled = true;

    @Builder.Default
    @Column(name = "account_locked")
    private Boolean accountLocked = false;

    @Builder.Default
    @Column(name = "deleted")
    private Boolean deleted = false;

    // ================= Relationships =================

    //@OneToMany relationship always exit in inverse side

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default // Help to deal with nullPointerException when work with empty set
    private Set<Role> roles = new HashSet<>(); // this created a default empty set


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp //Create data and time automatically
    @Column(name = "created_at",
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

}
