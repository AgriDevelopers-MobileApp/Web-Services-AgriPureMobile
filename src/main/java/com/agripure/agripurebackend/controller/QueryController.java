package com.agripure.agripurebackend.controller;

import com.agripure.agripurebackend.entities.Query;
import com.agripure.agripurebackend.service.IQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/query")
@Api(tags = "Querys", value = "Web Service RESTful - Query")
public class QueryController {
    private final IQueryService queryService;

    public QueryController(IQueryService queryService) {
        this.queryService = queryService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List querys", notes = "Method for list all querys")
    @ApiResponses({
            @ApiResponse(code = 201, message = "querys found"),
            @ApiResponse(code = 404, message = "querys not found"),
            @ApiResponse(code = 501, message = "Internal Server Error")
    })
    public ResponseEntity<List<Query>> findAllQuerys(){
        try{
            List<Query> querys = queryService.getAll();
            return new ResponseEntity<>(querys, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find querys by Id", notes = "Method for list one querys by Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "query found"),
            @ApiResponse(code = 404, message = "query not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Query> findQuerysById(@PathVariable("id") Long id){
        try{
            Optional<Query> query = queryService.getById(id);
            if(!query.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(query.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/username?{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Found query by UserName", notes = "Method for list all query by username")
    public ResponseEntity<List<Query>> findQuerysByUserName(@PathVariable("username") String username){
        try{
            List<Query> querys = queryService.findByUsername(username);
            return new ResponseEntity<>(querys, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert query", notes = "Method for insert new query")
    @ApiResponses({
            @ApiResponse(code = 201, message = "query created"),
            @ApiResponse(code = 404, message = "query not created"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Query> insertQuery(@Valid @RequestBody Query query){
        try{
            Query queryNew = queryService.save(query);
            return ResponseEntity.status(HttpStatus.CREATED).body(queryNew);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update data for query", notes = "Method for update data for query")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Data for query updated"),
            @ApiResponse(code = 404, message = "query not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Query> updateQuery(@PathVariable("id") Long id, @Valid @RequestBody Query query){
        try{
            Optional<Query> queryOld = queryService.getById(id);
            if(!queryOld.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else{
                query.setId(id);
                queryService.save(query);
                return new ResponseEntity<>(query, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete query by Id", notes = "Method for delete query")
    @ApiResponses({
            @ApiResponse(code = 201, message = "query deleted"),
            @ApiResponse(code = 404, message = "query not found"),
            @ApiResponse(code = 501, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Unauthorized")
    })
    public ResponseEntity<Query> deleteQuery(@PathVariable("id") Long id){
        try{
            Optional<Query> bookingDelete = queryService.getById(id);
            if(bookingDelete.isPresent()){
                queryService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
