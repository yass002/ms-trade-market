package tn.iteam.entities;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter  @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Adresse {

    private String street;
    private String city;
    private String codePostal;
}
