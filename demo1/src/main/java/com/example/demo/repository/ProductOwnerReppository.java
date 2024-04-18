package com.example.demo.repository;

import com.example.demo.entity.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductOwnerReppository extends JpaRepository<ProductOwner, Integer> {
}
