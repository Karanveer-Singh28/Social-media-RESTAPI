package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    UserDaoService userDaoService;

    public UserResource( UserDaoService userDaoService)
    {
        this.userDaoService=userDaoService;
    }


    @GetMapping("/users")
    public List<User> retrieveallUsers()
    {
        return userDaoService.findAll();
    }



    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserByiD(@PathVariable int id)
    {
        User user = userDaoService.findById(id);
        if(user==null)
        {
            throw new UserNotFoundException("id:"+id);
        }

        EntityModel<User> entityModel= EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveallUsers());

        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user)
    {
        User createdUser=userDaoService.saveUser(user);

        // Constructing a URI that represents the location of the newly created User resource.
        // The URI is built from the current request's URI, appending the path "/{id}".
        // The "{id}" placeholder is then replaced with the ID of the cr eated user.
        // The resulting URI is something like: http://localhost:8080/users/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdUser.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUserByiD(@PathVariable int id)
    {

            userDaoService.deleteById(id);

    }
    
}
