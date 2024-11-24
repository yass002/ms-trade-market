package tn.iteam.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String resourceName, String fieldName, String fieldValue){
        super(String.format("Resource %s not found for field %s and value%s",resourceName,fieldName,fieldValue));
    }
}
