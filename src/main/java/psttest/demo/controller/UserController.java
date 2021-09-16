package psttest.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import psttest.demo.dao.GoodsRepository;
import psttest.demo.dao.UserRepository;
import psttest.demo.domain.Goods;
import psttest.demo.domain.Role;
import psttest.demo.domain.User;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;

    public UserController(UserRepository userRepository, GoodsRepository goodsRepository) {
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
    }

    @ApiOperation(value = "Find all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading users"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Page number",
                    example = "0", defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page",
                    example = "3", defaultValue = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort",
                    example = "id", defaultValue = "id", dataType = "string", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<Page<User>> findAll(@ApiIgnore Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Find user by username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Search query - username", example = "ED",
                    required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/searchByUsername")
    public ResponseEntity<User> findByUsername(@RequestParam("username") String query) {
        Optional<User> byUsername = userRepository.findByUsername(query);
        User user = byUsername.orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user update"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User database id", example = "2", required = true,
                    dataType = "long", paramType = "path")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody UserRequest request) {
        User userFromDb = userRepository.findById(userId).orElseThrow();
        userFromDb.setUsername(request.getUsername());
        userFromDb.setEmail(request.getEmail());
        return new ResponseEntity<>(userRepository.save(userFromDb), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful delete user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User database id", example = "3", required = true,
                    dataType = "long", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        User userFromDb = userRepository.findById(userId).orElseThrow();
        userRepository.delete(userFromDb);
        String delete = "User with ID = " + userId + " deleted";
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @ApiOperation(value = "Create user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful creation user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PostMapping("/registration")
    public ResponseEntity<User> create(@RequestBody UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRoles(Collections.singleton(Role.USER));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add goods in basket")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful adding goods in basket"),
            @ApiResponse(code = 500, message = "Server error, something wrong, maybe the product is already in the basket")
    })
    @PutMapping("/{id}/addGoods")
    public ResponseEntity<User> byeGoods(@PathVariable("id") Long userId,
                                         @RequestBody GoodsRequest request) {
        Goods goods = goodsRepository.findByGoodName(request.getGoodName()).orElseThrow();
        userRepository.byeGoods(userId, goods.getId());
        User userFromDb = userRepository.findById(userId).orElseThrow();
        return new ResponseEntity<>(userFromDb, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove goods from basket")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful removing goods from basket"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PutMapping("/{id}/removeGoods")
    public ResponseEntity<User> removeGoods(@PathVariable("id") Long userId,
                                            @RequestBody GoodsRequest request) {
        Goods goods = goodsRepository.findByGoodName(request.getGoodName()).orElseThrow();
        userRepository.removeGoods(userId, goods.getId());
        User userFromDb = userRepository.findById(userId).orElseThrow();
        return new ResponseEntity<>(userFromDb, HttpStatus.OK);
    }
}