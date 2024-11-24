package tn.iteam.services;

import org.springframework.stereotype.Service;
import tn.iteam.dtos.ResponseDTO;
import tn.iteam.dtos.UserDTO;
import tn.iteam.entities.Adresse;
import tn.iteam.entities.User;
import tn.iteam.enums.Role;
import tn.iteam.exceptions.ResourceNotFound;
import tn.iteam.exceptions.UserAlreadyExists;
import tn.iteam.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDTO saveUser(User user){

        User userExists = userRepository.findByUsername(user.getUsername());
        if(userExists != null){
            throw new UserAlreadyExists("User already exists");
        }
        else {
            User toSave = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .phoneNumber(user.getPhoneNumber())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .imagePath(user.getImagePath())
                    .cinImage(user.getCinImage())
                    .email(user.getEmail())
                    .cin(user.getCin())
                    .role(Role.USER)
                    .build();
            userRepository.save(toSave);
            return new ResponseDTO("User saved successfully", "201");
        }
    }
    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }

    public ResponseDTO deleteUser(Long id){
        User userExists = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFound("User", "id", id.toString()));
        if (userExists != null) {
            userRepository.deleteById(id);
        }
        return new ResponseDTO("User deleted successfully", "200");
    }
    public ResponseDTO updateUser(Long id, User user){
        user.setRole(Role.USER);
        return userRepository.findById(id)
                .map(user1 -> {
                    user1.setFirstName(user.getFirstName());
                    user1.setLastName(user.getLastName());
                    user1.setPhoneNumber(user.getPhoneNumber());
                    user1.setUsername(user.getUsername());
                    user1.setPassword(user.getPassword());
                    user1.setRole(user.getRole());
                    user1.setImagePath(user.getImagePath());
                    user1.setAddress(user.getAddress());
                    userRepository.save(user1);
                    return new ResponseDTO("User updated successfully", "200");

                })
                .orElseThrow(() -> new ResourceNotFound("User", "id", id.toString()));

    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User", "id", id.toString()));
    }
}
