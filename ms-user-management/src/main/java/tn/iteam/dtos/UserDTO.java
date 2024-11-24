package tn.iteam.dtos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embedded;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import tn.iteam.entities.Adresse;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String cin;



}
