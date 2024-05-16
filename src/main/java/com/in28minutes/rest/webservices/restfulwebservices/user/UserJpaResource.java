package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {


    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository,PostRepository postRepository)
    {
        this.userRepository =userRepository;
        this.postRepository=postRepository;
    }


    @GetMapping("/jpa/users")
    public List<User> retrieveallUsers()
    {
        return userRepository.findAll();
    }



    @GetMapping("/jpa/users/{id}")
    public EntityModel<Optional<User>> retrieveUserByiD(@PathVariable int id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            throw new UserNotFoundException("id:"+id);
        }

        EntityModel<Optional<User>> entityModel= EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveallUsers());

        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user)
    {
        User createdUser= userRepository.save(user);

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


    @DeleteMapping("/jpa/users/{id}")
    public void deleteUserByiD(@PathVariable int id)
    {

            userRepository.deleteById(id);

    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveallPostsForUsers(@PathVariable int id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            throw new UserNotFoundException("id:"+id);
        }

        return user.get().getPosts();

    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUsers(@PathVariable int id, @Valid @RequestBody Post post)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            throw new UserNotFoundException("id:"+id);
        }
        post.setUser(user.get());

        Post createdPost=postRepository.save(post);

        // Constructing a URI that represents the location of the newly created User resource.
        // The URI is built from the current request's URI, appending the path "/{id}".
        // The "{id}" placeholder is then replaced with the ID of the cr eated user.
        // The resulting URI is something like: http://localhost:8080/users/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/jpa/users/{id}/posts/{id2}")
    public void deletePostById(@PathVariable int id, @PathVariable int id2)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            throw new UserNotFoundException("id:"+id);
        }

        Optional<Post> post= postRepository.findById(id2);
        if(post.isEmpty())
        {
            throw new UserNotFoundException("id2: "+id2);
        }

        postRepository.deleteById(id2);

    }


    
}
