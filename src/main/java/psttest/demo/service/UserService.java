package psttest.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import psttest.demo.controller.UserRequest;
import psttest.demo.dao.UserRepository;
import psttest.demo.domain.Role;
import psttest.demo.domain.User;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void byeGoods(Long userId, Long goodId){
        userRepository.byeGoods(userId, goodId);
    }

    public void removeGoods(Long userId,Long goodId){
        userRepository.removeGoods(userId, goodId);
    }

    public void removeUser(Long userId){
        userRepository.removeUser(userId);
    }

    public User updateUser(Long userId, UserRequest request){
        User userFromDb = findById(userId).orElseThrow();
        userFromDb.setUsername(request.getUsername());
        userFromDb.setPassword(passwordEncoder.encode(request.getPassword()));
        userFromDb.setEmail(request.getEmail());
        return save(userFromDb);
    }

    public String deleteUser(Long userId){
        removeUser(userId);
        User userFromDb = findById(userId).orElseThrow();
        delete(userFromDb);
        return "User with ID = " + userId + " deleted";
    }

    public User create(UserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(Collections.singleton(Role.USER));
        return save(user);
    }
}
