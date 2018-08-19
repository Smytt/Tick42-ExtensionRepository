//package com.tick42.quicksilver.controllers;
//
//import com.tick42.quicksilver.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.validation.Valid;
//import java.util.List;
//
//
//@Controller
//public class RegistrationController {
//    @Autowired
//    private UserDetailsManager userDetailsManager;
//
//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @GetMapping("/register")
//    public String showRegisterPage(Model model) {
//        model.addAttribute("user", new User());
//
//        return "register";
//    }
//
//    @PostMapping("/registerUser")
//    public String registerUser(
//            @Valid @ModelAttribute("user") User user,
//            BindingResult bindingResult,
//            Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", new User());
//            model.addAttribute("registrationError",
//                    "Username/password can not be empty.");
//            return "register";
//        }
//
//        if (checkUserExist(user.getUsername())) {
//            model.addAttribute("user", new User());
//            model.addAttribute("registrationError",
//                    "User with the same username already exists.");
//            return "register";
//        }
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        encodedPassword = "{bcrypt}" + encodedPassword;
//        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
//        org.springframework.security.core.userdetails.User newUser
//                = new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                encodedPassword,
//                authorities);
//
//        userDetailsManager.createUser(newUser);
//
//        return "register-confirmation";
//    }
//
//    private boolean checkUserExist(String userName) {
//        return userDetailsManager.userExists(userName);
//    }
//}
