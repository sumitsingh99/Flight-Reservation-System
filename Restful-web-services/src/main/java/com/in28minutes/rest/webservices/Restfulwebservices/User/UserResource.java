package com.in28minutes.rest.webservices.Restfulwebservices.User;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	//private static final int ResponseEntity = 0;
	@Autowired
	private UserDaoService service;
	
	//retriveAllUsers
	@GetMapping(path="/users")
	public List<User> retriveAllUsers(){
		return service.findAll();
	}
	
	//retriveUser
	@GetMapping(path="/users/{id}")
	public User retriveUser(@PathVariable int id) {
		
		User user = service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("id-"+id);
		}
		return user;
	}
	
	//Add User 
	@PostMapping(path="/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		//created
		//user/{} savedUser.getId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//delete User
	@DeleteMapping(path="/users/{id}")
	public void DeleteUser(@PathVariable int id) {
		
		User user = service.deleteOne(id);
		if(user==null) {
			throw new UserNotFoundException("id-"+id);
		}
	}
	
}
