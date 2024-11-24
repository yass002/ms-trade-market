package tn.iteam.web;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.iteam.dtos.ResponseDTO;
import tn.iteam.dtos.UserDTO;
import tn.iteam.services.KeycloakUserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@AllArgsConstructor
public class KeycloakUserController {

    private KeycloakUserService keycloakUserService;
    static String PATH_DIR = "C:\\Users\\yassi\\Desktop\\projet trademarket\\swap-market-services\\ms-user-management\\images\\";

    @PostMapping("/create")
    public UserDTO saveUser(@RequestPart("user")  UserDTO userDTO){
        System.out.println(userDTO.getUsername());
        return keycloakUserService.createUser(userDTO);
    }
    @PostMapping(value = "/add")
    public void createUser(
                              @RequestPart(required = false) MultipartFile img,
                              @RequestPart(required = false) MultipartFile cinImage) throws IOException {
        var profilePict= img.getOriginalFilename().
                substring(0,img.getOriginalFilename().lastIndexOf('.'))+
                System.currentTimeMillis()+'.'+img.getOriginalFilename().
                substring(img.getOriginalFilename().lastIndexOf('.')+1);
        Files.copy(img.getInputStream(), Paths.get(PATH_DIR+"profilePictures"+ File.separator+profilePict)
        );
        var cinImg= cinImage.getOriginalFilename().
                substring(0,cinImage.getOriginalFilename().lastIndexOf('.'))+
                System.currentTimeMillis()+'.'+cinImage.getOriginalFilename().
                substring(cinImage.getOriginalFilename().lastIndexOf('.')+1);
        Files.copy(cinImage.getInputStream(), Paths.get(PATH_DIR+"cin"+ File.separator+cinImg)
        );
    }


}
