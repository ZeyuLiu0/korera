package com.example.korera.controller;

import com.example.korera.entity.User;
import com.example.korera.service.MyUserDetailsService;
import com.example.korera.service.UserService;
import com.example.korera.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userServiceImp;

    private final AuthenticationManager authenticate;

    private final MyUserDetailsService myUserDetailsService;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userServiceImp, AuthenticationManager authenticate, MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userServiceImp = userServiceImp;
        this.authenticate = authenticate;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userServiceImp.getUserById(user.getUserName()) != null) {
            return new ResponseEntity<>("the user is already existed",HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userServiceImp.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        try {
            authenticate.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new Exception("Incorrect username or password",e),HttpStatus.BAD_REQUEST);
        }


        final String jwt = jwtUtil.generateToken(user.getUserName(),user.getRole());

        return  new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User u = userServiceImp.createUser(user);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userServiceImp.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User u = userServiceImp.updateUser(user);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userServiceImp.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userServiceImp.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
