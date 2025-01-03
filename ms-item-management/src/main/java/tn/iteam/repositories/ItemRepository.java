package tn.iteam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iteam.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
