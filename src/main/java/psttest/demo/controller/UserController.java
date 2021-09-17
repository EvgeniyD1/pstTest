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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import psttest.demo.controller.requests.GoodsRequest;
import psttest.demo.controller.requests.UserRequest;
import psttest.demo.domain.User;
import psttest.demo.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        Page<User> users = userService.findAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Find user by username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Search query - username", example = "Ivan",
                    required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/searchByUsername")
    public ResponseEntity<User> findByUsername(@RequestParam("username") String query) {
        User user = userService.findByUsername(query).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User database id", example = "2", required = true,
                    dataType = "long", paramType = "path")
    })
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
                                           @ApiIgnore @AuthenticationPrincipal User user,
                                           @RequestBody UserRequest request) {
        if (userId.equals(user.getId())) {
            return new ResponseEntity<>(userService.updateUser(userId, request), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Create user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful creation user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PostMapping("/registration")
    public ResponseEntity<User> create(@RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add goods in basket")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful adding goods in basket"),
            @ApiResponse(code = 500, message = "Server error, something wrong, maybe the product is already in the basket")
    })
    @PutMapping("/addGoods")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<User> byeGoods(
            @ApiIgnore @AuthenticationPrincipal User user,
            @RequestBody GoodsRequest request) {
        return new ResponseEntity<>(userService.byeGoods(user, request), HttpStatus.OK);
    }

    @ApiOperation(value = "Remove goods from basket")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful removing goods from basket"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PutMapping("/removeGoods")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<User> removeGoods(
            @ApiIgnore @AuthenticationPrincipal User user,
            @RequestBody GoodsRequest request) {
        return new ResponseEntity<>(userService.removeGoods(user, request), HttpStatus.OK);
    }

}
