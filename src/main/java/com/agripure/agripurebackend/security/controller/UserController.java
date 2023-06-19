package com.agripure.agripurebackend.security.controller;

import com.agripure.agripurebackend.entities.Plant;
import com.agripure.agripurebackend.security.dto.UpdatedUser;
import com.agripure.agripurebackend.security.entity.User;
import com.agripure.agripurebackend.security.service.UserService;
import com.agripure.agripurebackend.service.IPlantService;
import com.agripure.agripurebackend.util.Message;
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
    private final IPlantService plantService;

    public UserController(UserService userService, IPlantService plantService) {
        this.userService = userService;
        this.plantService = plantService;
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "/username/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
                User userToSave = new User(user.getName(), userOld.get().getUserName(), user.getEmail(),userOld.get().getPassword());
                userToSave.setId(userOld.get().getId());
                userToSave.setRoles(userOld.get().getRoles());
                userService.save(userToSave);
                return new ResponseEntity<>(userToSave, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{userId}/assign/{plantId}")
    @ApiOperation(value = "Insert Plant to User", notes = "Method for insert plant to User")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plant assigned to User"),
            @ApiResponse(code = 404, message = "Plant not assigned"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<?> assignPlantToUser(@PathVariable("plantId") Long plantId, @PathVariable("userId") Long userId) {
        try {
            Optional<Plant> plant = plantService.getById(plantId);
            Optional<User> user = userService.findUserById(userId);
            if (plant.isPresent() && user.isPresent()){
                if(user.get().getPlants().contains(plant.get())){
                    return new ResponseEntity<>(new Message("Plant already assigned to User"), HttpStatus.OK);
                }
                user.get().getPlants().add(plant.get());
                userService.save(user.get());
                return new ResponseEntity<>(new Message("Plant assigned to User"), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new Message("Plant not assigned"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Message("Fail in the method"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{userId}/delete/{plantId}")
    @ApiOperation(value = "Delete Plant to User", notes = "Method for delete plant to User")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plant deleted to User"),
            @ApiResponse(code = 404, message = "Plant not deleted"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<?> deletePlantToUser(@PathVariable("plantId") Long plantId, @PathVariable("userId") Long userId) {
        try {
            Optional<Plant> plant = plantService.getById(plantId);
            Optional<User> user = userService.findUserById(userId);
            if (plant.isPresent() && user.isPresent()){
                if(!user.get().getPlants().contains(plant.get())){
                    return new ResponseEntity<>(new Message("Plant doesn't assigned to User"), HttpStatus.OK);
                }
                user.get().getPlants().remove(plant.get());
                userService.save(user.get());
                return new ResponseEntity<>(new Message("Plant deleted to User"), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new Message("Plant not deleted"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Message("Fail in the method"), HttpStatus.BAD_REQUEST);
        }
    }
}
