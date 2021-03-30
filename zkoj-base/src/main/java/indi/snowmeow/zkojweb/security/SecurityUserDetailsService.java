package indi.snowmeow.zkojweb.security;

import indi.snowmeow.zkojweb.model.po.RolePO;
import indi.snowmeow.zkojweb.model.po.UserPO;
import indi.snowmeow.zkojweb.service.impl.RoleServiceImpl;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author snowmeow
 * @date 2021/3/22
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOGGER.info("进入验证流程：" + username);

        UserPO userPO = userService.findByUserName(username);
        if (userPO == null) {
            throw new UsernameNotFoundException("No user fond with username: " + username);
        }
        RolePO role = roleService.getFromUsername(userPO.getUsername());
        if (role == null) {
            //TODO: throw Exception
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return new User(userPO.getUsername(), userPO.getPassword(), authorities);
    }
}
