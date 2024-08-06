package com.jwt.Controller;


import com.jwt.Repository.IUserDao;
import com.jwt.config.JwtService;
import com.jwt.config.UserDetailsServiceImpl;
import com.jwt.model.LoginForm;
import com.jwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;;

    @GetMapping("/home")
    public String home(){
        return "Hello World";
    }

    @GetMapping("/admin/home")
    public String adminHome(){
        return "Welcome to admin home";
    }

    @GetMapping("/user/home")
    public String userHome(){
        return "Welcome to user home";
    }

    @PostMapping("/register/user")
    public User registerUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    //Authenticate the user
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginForm loginForm){
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        Authentication authentication = Authenticate(username,password); // authenticate user details from database
        SecurityContextHolder.getContext().setAuthentication(authentication); // set the user as logged in.

        UserDetails userDetails =userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails); // generate the jwt token


    }

    public Authentication Authenticate(String username, String password){
        UserDetails userDetails =userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
    }
}
