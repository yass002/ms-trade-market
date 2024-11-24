package tn.iteam.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tn.iteam.enums.Role;
import tn.iteam.model.ItemModel;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String cin;
    private String username;
    private String password;
    private String imagePath;
    private String cinImage;
    @Embedded
    private Adresse address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private List<ItemModel> objects;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

}
