package tn.iteam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.iteam.enums.Status;

@Getter @Setter
public class ItemModel {

    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String imagePath;
    private Long userId;
}
