package tn.iteam.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemModel {
    @JsonProperty("idItem")
    private Long idItem;
    @JsonProperty("idCategory")
    private Long idCategory;
    @JsonProperty("idUser")
    private Long idUser;
}
