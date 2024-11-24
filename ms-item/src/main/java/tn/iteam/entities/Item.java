package tn.iteam.entities;


import jakarta.persistence.*;
import lombok.*;
import tn.iteam.enums.Status;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String imagePath;
    private Long userId;

}
