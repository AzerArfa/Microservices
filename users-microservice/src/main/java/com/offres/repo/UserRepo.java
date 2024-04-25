package com.offres.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.offres.entity.User;
import com.offres.enums.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole role);

    List<User> findAllByRole(UserRole role);


}
