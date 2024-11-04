package tn.iteam.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.iteam.dtos.UserDTO;
import tn.iteam.entities.User;
import tn.iteam.services.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }




    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public void saveUser(@RequestPart("user")  String userDTOString,
                       @RequestPart("file")    MultipartFile file) throws JsonProcessingException {
        UserDTO userDTO = convertToDto(userDTOString);
        User user = modelMapper.map(userDTO, User.class);
        System.out.println(user.getPhoneNumber());

        System.out.println(file.getOriginalFilename());
    }


    public UserDTO convertToDto(String user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(user, UserDTO.class);
    }

}
