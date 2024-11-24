package tn.iteam.dtos;

import lombok.*;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class ResponseDTO {
    private String statusCode;
    private String statusMessage;
}
