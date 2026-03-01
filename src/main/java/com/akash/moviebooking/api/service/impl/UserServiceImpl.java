package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.mapper.UserDetailsMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//    private final UserDetailsMapper userMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    //Register new users with role-based logic.
//    @Override
//    public UserResponse addUser(UserRegistrationRequest user) {
//        if (userRepository.existsByEmail(user.email()))
//            throw new UserExistByEmailException("User with the Email is already exists");
//
//        UserDetails userDetails = switch (user.userRole()) {
//            case USER -> copy(new User(), user);
//            case THEATER_OWNER -> copy(new TheaterOwner(), user);
//        };
//        return userMapper.userDetailsResponseMapper(userDetails);
//
//    }
//
//    //Safely update user details (including changing email). Change their name, phone, or email.
////    @Override
////    public UserResponse editUser(UserUpdationRequest userRequest, String email) {
////        log.info("editing user...");
////        if (userRepository.existsByEmail(email)) //Check if the user exists by their current email. If not → throw “User not found” error.
////        {
////            UserDetails user = userRepository.findByEmail(email);
////            log.info("user is unique");
////            if (!user.getEmail().equals(userRequest.email()) && userRepository.existsByEmail(userRequest.email()))//No two users can have the same email
////            {
////                //Check if the new email is already used by someone else. If yes → throw “Email already exists” error.if no → continues.
////                throw new UserExistByEmailException("User with the email already exists");
////            }
////
////
////            log.info("mapping data...");
////            user = copy(user, userRequest); //Update the user’s details with the new information from the request.
////
////            return userMapper.userDetailsResponseMapper(user); //Return the updated user info to show that the update was successful.
////        }
////
////        throw new UserNotFoundByEmailException("Email not found in the Database");
////
////    }
//
//    @Override
//    public UserResponse editUser(UserUpdationRequest userRequest, String email) {
//
//        UserDetails user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            throw new UserNotFoundByEmailException("Email not found in database");
//        }
//
//        if (!user.getEmail().equals(userRequest.email())
//                && userRepository.existsByEmail(userRequest.email())) {
//            throw new UserExistByEmailException("User with this email already exists");
//        }
//
//        user = copy(user, userRequest);
//        return userMapper.userDetailsResponseMapper(user);
//    }
//
//
//    //This method is used to delete a user “softly”. Soft delete means: we don’t actually remove the user from the database.nstead, we just mark the user as deleted.
//    @Override
//    public UserResponse softDeleteUser(String email) {
//        if (userRepository.existsByEmail(email)) {
//            UserDetails user = userRepository.findByEmail(email);
//            user.setDelete(true);
//            user.setDeletedAt(Instant.now());
//            userRepository.save(user);
//            return userMapper.userDetailsResponseMapper(user);
//        }
//        throw new UserNotFoundByEmailException("Email not found in the Database");
//    }
//
//    //Map registration request → entity.
//    private UserDetails copy(UserDetails userRole, UserRegistrationRequest user) {
//        userRole.setUserRole(user.userRole());
//        userRole.setPassword(passwordEncoder.encode(user.password()));
//        userRole.setEmail(user.email());
//        userRole.setDateOfBirth(user.dateOfBirth());
//        userRole.setPhoneNumber(user.phoneNumber());
//        userRole.setUsername(user.username());
//        userRole.setDelete(false);
//        userRepository.save(userRole);
//        return userRole;
//    }
//
//    //Map update request → entity.
//    private UserDetails copy(UserDetails userRole, UserUpdationRequest user) {
//        userRole.setDateOfBirth(user.dateOfBirth());
//        userRole.setPhoneNumber(user.phoneNumber());
//        userRole.setEmail(user.email());
//        userRole.setUsername(user.username());
//        userRole.setDelete(false);
//        userRepository.save(userRole);
//        return userRole;
//    }
//}
import com.akash.moviebooking.api.dto.UserRegistrationRequest;
import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.dto.UserUpdationRequest;
import com.akash.moviebooking.api.entity.TheaterOwner;
import com.akash.moviebooking.api.entity.User;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.exceptions.UserExistByEmailException;
import com.akash.moviebooking.api.exceptions.UserNotFoundByEmailException;
import com.akash.moviebooking.api.mapper.UserDetailsMapper;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new UserExistByEmailException("An account with this email already exists.");
        }

        UserDetails user = switch (request.userRole()) {
            case USER -> new User();
            case THEATER_OWNER -> new TheaterOwner();
        };

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setDateOfBirth(request.dateOfBirth());
        user.setUserRole(request.userRole());
        user.setDelete(false);

        return mapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(UserUpdationRequest request, String email) {
        UserDetails user = userRepository.findByEmailAndIsDeleteFalse(email).orElseThrow(() -> new UserNotFoundByEmailException("User not found with the provided email."));


        user.setUsername(request.username());
        //user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setDateOfBirth(request.dateOfBirth());

        return mapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse softDeleteUser(String email) {
        UserDetails user = userRepository.findByEmailAndIsDeleteFalse(email).orElseThrow(() -> new UserNotFoundByEmailException("User not found with the provided email."));

        user.setDelete(true);
        user.setDeletedAt(Instant.now());

        return mapper.toDto(userRepository.save(user));
    }
}