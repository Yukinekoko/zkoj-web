package indi.snowmeow.zkoj.auth.security;

import indi.snowmeow.zkoj.auth.model.dto.UserLoginDTO;
import indi.snowmeow.zkoj.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author snowmeow
 * @date 2021/4/13
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUserService.class);

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOGGER.info(s + "进入验证流程");
        UserLoginDTO userLoginDTO = userService.findByUsername(s);
        if (userLoginDTO == null) {
            throw new UsernameNotFoundException("No username: " + s);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userLoginDTO.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
        return new User(s, userLoginDTO.getPassword(), authorities);
    }
}
