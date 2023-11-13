package org.emmek.beu2w3d1.services;

import org.emmek.beu2w3d1.entities.User;
import org.emmek.beu2w3d1.exception.UnauthorizedException;
import org.emmek.beu2w3d1.payloads.UserLoginDTO;
import org.emmek.beu2w3d1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserServices usersService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUser(UserLoginDTO body) {
        User user;
        try {
            user = usersService.findByUsername(body.username());
        } catch (Exception e) {
            user = usersService.findByEmail(body.username());
        }
        if (body.password().equals(user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
}