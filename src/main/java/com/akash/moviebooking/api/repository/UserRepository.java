//package com.akash.moviebooking.api.repository;
//
//import com.akash.moviebooking.api.entity.User;
//import com.akash.moviebooking.api.entity.UserDetails;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface UserRepository extends JpaRepository<UserDetails, String> {
//    boolean existsByEmail(String email);
//    UserDetails findByEmail(String email);
//
//}
package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetails, String> {

    boolean existsByEmail(String email);

    Optional<UserDetails> findByEmail(String email);

    Optional<UserDetails> findByEmailAndIsDeleteFalse(String email);

}