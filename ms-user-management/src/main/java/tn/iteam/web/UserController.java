package tn.iteam.web;


import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.iteam.dtos.ResponseDTO;
import tn.iteam.dtos.UserDTO;
import tn.iteam.entities.User;
import tn.iteam.services.KeycloakUserService;
import tn.iteam.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final KeycloakUserService keycloakUserService;
    public UserController(ModelMapper modelMapper, UserService userService, KeycloakUserService keycloakUserService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.keycloakUserService = keycloakUserService;
    }

    static String PATH_DIR = "C:\\Users\\yassi\\Desktop\\projet trademarket\\swap-market-services\\ms-user-management\\images\\";


    @GetMapping(value = "/users/{id}")

    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping(value = "/users")
    public ResponseEntity<ResponseDTO> createUser(@RequestPart UserDTO request,
                                                  @RequestPart(required = false) MultipartFile img,
                                                  @RequestPart(required = false) MultipartFile cinImage
    ) throws IOException {
        User user = modelMapper.map(request, User.class);
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
        user.setCinImage(cinImg);
        user.setImagePath(profilePict);
        keycloakUserService.createUser(request);
        return  ResponseEntity.ok(userService.saveUser(user));
    }
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable Long id,
                                                  @RequestPart UserDTO user,
                                                  @RequestPart(required = false) MultipartFile file){

        return ResponseEntity.ok(userService.updateUser(id, modelMapper.map(user, User.class)));
    }


    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Resource resource = new FileSystemResource(PATH_DIR+"profilePictures//" + File.separator + fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(resource.contentLength())
                .body(resource);
    }

}
