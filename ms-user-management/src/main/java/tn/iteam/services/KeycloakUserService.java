package tn.iteam.services;


import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import tn.iteam.dtos.UserDTO;
import tn.iteam.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class KeycloakUserService {
    private Keycloak keycloak;
    private UserService userService;
    private ModelMapper modelMapper;


    public KeycloakUserService(Keycloak keycloak, UserService userService, ModelMapper modelMapper) {
        this.keycloak = keycloak;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    public UserDTO createUser(UserDTO userRegistrationRecord) {
        System.out.println("userRegistrationRecord = " + userRegistrationRecord.getUsername());
        UserRepresentation user=new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.getUsername());
        user.setEmail(userRegistrationRecord.getEmail());
        user.setFirstName(userRegistrationRecord.getFirstName());
        user.setLastName(userRegistrationRecord.getLastName());

        user.setEmailVerified(false);
        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);
        user.setAttributes(Map.of(
                "phoneNumber", List.of(userRegistrationRecord.getPhoneNumber()),
                "cin", List.of(userRegistrationRecord.getCin())

        ));
        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);

        System.out.println(response.getStatus());
        System.out.println("Response Body: " + response.readEntity(String.class));

        if(Objects.equals(201,response.getStatus())){

            List<UserRepresentation> representationList = usersResource.searchByUsername(userRegistrationRecord.getUsername(), true);
            if(!CollectionUtils.isEmpty(representationList)){
                UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                assert userRepresentation1 != null;
                emailVerification(userRepresentation1.getId());
            }
            return  userRegistrationRecord;
        }


        System.out.println("problem");
        return null;
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm("trade");
        return realm1.users();
    }



    public void emailVerification(String userId){

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }


}
