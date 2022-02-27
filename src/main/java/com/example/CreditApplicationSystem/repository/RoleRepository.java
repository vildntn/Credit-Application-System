package com.example.CreditApplicationSystem.repository;

import com.example.CreditApplicationSystem.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
