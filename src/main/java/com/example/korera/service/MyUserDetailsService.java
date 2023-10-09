package com.example.korera.service;

import com.example.korera.exceptions.UserNotFoundException;
import com.example.korera.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRep userRep;
    @Autowired
    public MyUserDetailsService(UserRep userRep){
        this.userRep = userRep;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.korera.entity.User> optional = userRep.findById(username);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User is not exist");
        }else{
            com.example.korera.entity.User realUser= optional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(realUser.getRole().name()));
            return new User(realUser.getUserName(),realUser.getPassword(),authorities);
        }
    }
}
