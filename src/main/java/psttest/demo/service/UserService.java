package psttest.demo.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import psttest.demo.controller.requests.GoodsRequest;
import psttest.demo.controller.requests.UserRequest;
import psttest.demo.dao.GoodsRepository;
import psttest.demo.dao.UserRepository;
import psttest.demo.domain.Goods;
import psttest.demo.domain.Role;
import psttest.demo.domain.User;

import java.util.Collections;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"user"})
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       GoodsRepository goodsRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(value = "user")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Cacheable(value = "user")
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Cacheable(value = "user")
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Cacheable(value = "user")
    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    @CacheEvict(value = "user", allEntries = true)
    public User save(User user){
        return userRepository.save(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void delete(User user){
        userRepository.delete(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void byeGoods(Long userId, Long goodId){
        userRepository.byeGoods(userId, goodId);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void removeGoods(Long userId,Long goodId){
        userRepository.removeGoods(userId, goodId);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void removeUser(Long userId){
        userRepository.removeUser(userId);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public User updateUser(Long userId, UserRequest request){
        User userFromDb = findById(userId).orElseThrow();
        userFromDb.setUsername(request.getUsername());
        userFromDb.setPassword(passwordEncoder.encode(request.getPassword()));
        userFromDb.setEmail(request.getEmail());
        return save(userFromDb);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public String deleteUser(Long userId){
        removeUser(userId);
        User userFromDb = findById(userId).orElseThrow();
        delete(userFromDb);
        return "User with ID = " + userId + " deleted";
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public User create(UserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(Collections.singleton(Role.USER));
        return save(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public User byeGoods(User user, GoodsRequest request){
        Goods goods = goodsRepository.findByGoodName(request.getGoodName()).orElseThrow();
        byeGoods(user.getId(), goods.getId());
        return findById(user.getId()).orElseThrow();
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public User removeGoods(User user, GoodsRequest request){
        Goods goods = goodsRepository.findByGoodName(request.getGoodName()).orElseThrow();
        removeGoods(user.getId(), goods.getId());
        return findById(user.getId()).orElseThrow();
    }
}
