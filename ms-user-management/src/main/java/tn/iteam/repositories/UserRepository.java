package tn.iteam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iteam.entities.User;

public interface UserRepository  extends JpaRepository<User, Long> {
}
