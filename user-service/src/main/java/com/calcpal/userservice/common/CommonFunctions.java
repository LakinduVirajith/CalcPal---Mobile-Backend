package com.calcpal.userservice.common;

import com.calcpal.userservice.collection.User;
import com.calcpal.userservice.config.jwt.JwtService;
import com.calcpal.userservice.exception.NotFoundException;
import com.calcpal.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonFunctions {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private static String token;

    public void storeJWT(String jwt) {
        token = jwt;
    }

    public String getToken() throws NotFoundException {
        if(!token.isEmpty()){
            return token;
        }else{
            throw new NotFoundException("looks like the token is missing");
        }
    }

    public String getUserEmail(){
        return jwtService.extractUsername(token);
    }

    public User getUser() throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(getUserEmail());

        if(user.isPresent()){
            return user.get();
        }else{
            throw new NotFoundException("we couldn't find this account");
        }
    }
}
