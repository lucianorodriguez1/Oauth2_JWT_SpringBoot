package com.myPacket.oauth2_jwt.services;

import com.myPacket.oauth2_jwt.models.UserEntity;
import com.myPacket.oauth2_jwt.repositories.IUserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserEntityRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity u = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("El usuario con username " + username + " no fue encontrado"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        u.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRol().name()))));

        return new User(u.getUsername(),
                u.getPassword(),
                u.isEnabled(),
                u.isAccountNoExpired(),
                u.isCredentialNoExpired(),
                u.isAccountNoLocked(),
                authorityList);

    }
}
