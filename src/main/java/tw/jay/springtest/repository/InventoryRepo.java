package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.jay.springtest.entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {
    
}
