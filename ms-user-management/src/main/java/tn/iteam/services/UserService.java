package tn.iteam.services;


import org.springframework.stereotype.Service;
import tn.iteam.entities.Adresse;
import tn.iteam.entities.User;
import tn.iteam.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        Adresse adresse = Adresse.builder()
                .street(user.getAddress().getStreet())
                .city(user.getAddress().getCity())
                .codePostal(user.getAddress().getCodePostal())
                .build();
        User toSave = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())

                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(adresse)
                .build();
        return userRepository.save(toSave);
    }
}
