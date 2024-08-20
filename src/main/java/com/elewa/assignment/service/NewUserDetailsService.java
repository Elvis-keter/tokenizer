package com.elewa.assignment.service;

import com.elewa.assignment.mapper.UsersMapper;
import com.elewa.assignment.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
//public class NewUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UsersMapper usersMapper;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        Users users = usersMapper.findByUsername(username);
////
////        if(users == null) {
////            throw new UsernameNotFoundException("User not found with username: " + username);
////        };
////
//
//        if ("elvis".equals(username)) {
//            return new User("elvis", passwordEncoder.encode("elvis"),AuthorityUtils.createAuthorityList("ROLE_USER"));
//        } else if ("user".equals(username)) {
//            return new User("user", passwordEncoder.encode("password"), AuthorityUtils.createAuthorityList("ROLE_USER"));
//        } else if ("admin".equals(username)) {
//            return new User("admin", passwordEncoder.encode("admin"), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//    }
//}
//


public class NewUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userMapper.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(users.getRole()));

        return new org.springframework.security.core.userdetails.User(
                users.getUsername(), users.getPassword(),
                authorities
        );
    }
}

//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//    }
//}
