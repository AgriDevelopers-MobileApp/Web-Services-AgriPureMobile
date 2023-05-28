package com.agripure.agripurebackend.security.controller;

import com.agripure.agripurebackend.security.dto.NewUser;
import com.agripure.agripurebackend.security.dto.UpdatedUser;
import com.agripure.agripurebackend.security.entity.User;
import com.agripure.agripurebackend.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@Api(tags = "Users", value = "Web Service RESTful - Users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/username?{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find User by username", notes = "Method for find one User by username")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User found"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<User> findUserByUsername(@PathVariable("username") String username){
        try{
            Optional<User> user = userService.findByUserName(username);
            if(!user.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/username?{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update data for User", notes = "Method for update data for user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Data for user updated"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @Valid @RequestBody UpdatedUser user){
        try{
            Optional<User> userOld = userService.findByUserName(username);
            if(!userOld.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else{
                User userToSave = new User(user.getName(), user.getUserName(), user.getEmail(),userOld.get().getPassword());
                userToSave.setId(userOld.get().getId());
                userToSave.setRoles(userOld.get().getRoles());
                userService.save(userToSave);
                return new ResponseEntity<>(userToSave, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
