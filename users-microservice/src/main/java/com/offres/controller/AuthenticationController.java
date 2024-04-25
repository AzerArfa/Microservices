package com.offres.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.offres.dto.AuthenticationRequest;
import com.offres.dto.ChangePasswordDto;
import com.offres.dto.UserDto;
import com.offres.entity.User;
import com.offres.repo.UserRepo;
import com.offres.service.user.UserService;
import com.offres.utils.JwtUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepo userRepo;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepo userRepo, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect username or password.");
            return;
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepo.findFirstByEmail(userDetails.getUsername());

        try {
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Check if the password needs to be updated
                if (userService.checkIfPasswordNeedsUpdate(user)) {
                	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                	response.getWriter().write("Password change required.");
                	response.getWriter().flush();
                	return;
                }

                final String jwt = jwtUtil.generateToken(userDetails); // Corrected line

                JSONObject userJson = new JSONObject()
                        .put("userId", user.getId())
                        .put("role", user.getRole().name()); // Make sure to convert the role to String

                response.getWriter().write(userJson.toString());
                response.addHeader("Access-Control-Expose-Headers", "Authorization");
                response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
                response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (JSONException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error creating JSON response.");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@ModelAttribute UserDto userDto) throws IOException {
        UserDto updatedUser = userService.updateUser(userDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        UserDto userDto = userService.getUserById(userId);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        try {
            return userService.updatePasswordById(changePasswordDto);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }


}
