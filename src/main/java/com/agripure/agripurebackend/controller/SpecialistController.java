package com.agripure.agripurebackend.controller;

import com.agripure.agripurebackend.entities.Specialist;
import com.agripure.agripurebackend.service.ISpecialistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/specialists")
@Api(tags = "Specialists", value = "Web Service RESTful - Specialists")
public class SpecialistController {

    private final ISpecialistService specialistService;

    public SpecialistController(ISpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List Specialists", notes = "Method for list all specialists")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialists found"),
            @ApiResponse(code = 404, message = "Specialists not found"),
            @ApiResponse(code = 501, message = "Internal Server error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<List<Specialist>> findAllSpecialists(){
        try{
            List<Specialist> specialists = specialistService.getAll();
            return new ResponseEntity<>(specialists, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find specialist by Id", notes = "Method for list one specialist by Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialists found"),
            @ApiResponse(code = 404, message = "Specialists not found"),
            @ApiResponse(code = 501, message = "Internal Server error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Specialist> findSpecialistsById(@PathVariable("id") Long id){
        try{
            Optional<Specialist> specialist = specialistService.getById(id);
            if(!specialist.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(specialist.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Found specialist by Name", notes = "Method for list one specialist by Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialist founded"),
            @ApiResponse(code = 404, message = "Specialist not founded"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Specialist> findSpecialistsByName(@PathVariable("name") String name){
        try{
            Specialist specialist = specialistService.findByName(name);
            if(specialist != null)
                return  new ResponseEntity<>(specialist, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert Specialist", notes = "Method for insert new specialist")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Specialist created"),
            @ApiResponse(code = 404, message = "Specialist not created"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Specialist> insertSpecialist(@Valid @RequestBody Specialist specialist){
        try{
            Specialist specialistNew = specialistService.save(specialist);
            return ResponseEntity.status(HttpStatus.CREATED).body(specialistNew);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update data for Specialist", notes = "Method for update data for specialist")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Data for specialist updated"),
            @ApiResponse(code = 404, message = "specialist not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Specialist> updateSpecialist(@PathVariable("id") Long id, @Valid @RequestBody Specialist specialist){
        try{
            Optional<Specialist> specialistOld = specialistService.getById(id);
            if(!specialistOld.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else{
                specialist.setId(id);
                specialistService.save(specialist);
                return new ResponseEntity<>(specialist, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete specialist by Id", notes = "Method for delete specialist")
    @ApiResponses({
            @ApiResponse(code = 201, message = "specialist deleted"),
            @ApiResponse(code = 404, message = "specialist not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Specialist> deleteSpecialist(@PathVariable("id") Long id){
        try{
            Optional<Specialist> bookingDelete = specialistService.getById(id);
            if(bookingDelete.isPresent()){
                specialistService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
