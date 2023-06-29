package com.agripure.agripurebackend.controller;

import com.agripure.agripurebackend.entities.Event;
import com.agripure.agripurebackend.security.entity.User;
import com.agripure.agripurebackend.security.service.UserService;
import com.agripure.agripurebackend.service.IEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/events")
@Api(tags = "Events", value = "Web Service RESTful - Events")
public class EventController {
    private final IEventService eventService;
    private final UserService userService;

    public EventController(IEventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List Events By Username", notes = "Method for list all Events by Username")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Events found"),
            @ApiResponse(code = 404, message = "Events not found"),
            @ApiResponse(code = 501, message = "Internal Server Error")
    })
    public ResponseEntity<List<Event>> findAllByUsername(@PathVariable("username") String username) {
        try {
            Optional<User> user = userService.findByUserName(username);
            if (user.isPresent()) {
                List<Event> events = eventService.findAllByUsername(username);
                return ResponseEntity.status(HttpStatus.OK).body(events);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add Events", notes = "Method for add new events")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Events created"),
            @ApiResponse(code = 404, message = "Event not created"),
            @ApiResponse(code = 501, message = "Internal Server Error")
    })
    public ResponseEntity<Event> insertEvent(@PathVariable("username") String username, @Valid @RequestBody Event event){
        try{
            Optional<User> optionalUser = userService.findByUserName(username);
            if (optionalUser.isPresent()) {
                List<Event> events = eventService.findAllByUsername(username);
                Event newEvent = eventService.save(event);
                events.add(newEvent);
                optionalUser.get().setEvents(events);
                userService.save(optionalUser.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
            }
            else
               return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/user/{username}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Event by Id", notes = "Method for delete event")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Event deleted"),
            @ApiResponse(code = 404, message = "Event not found"),
            @ApiResponse(code = 501, message = "Internal Server Error")
    })
    public ResponseEntity<Event> deleteEvent (@PathVariable("username") String username, @PathVariable("id") Long id){
        try{
            Optional<Event> eventDelete = eventService.getById(id);
            Optional<User> optionalUser = userService.findByUserName(username);
            if(optionalUser.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else{
                if(eventDelete.isPresent()){
                    List<Event> events = optionalUser.get().getEvents();
                    events.remove(eventDelete.get());
                    optionalUser.get().setEvents(events);
                    userService.save(optionalUser.get());
                    eventService.delete(id);
                    return ResponseEntity.status(HttpStatus.OK).body(eventDelete.get());
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}