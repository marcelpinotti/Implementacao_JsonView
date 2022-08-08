package br.com.marcelpinotti.jsonviewsexample.controllers;

import br.com.marcelpinotti.jsonviewsexample.dtos.user.UserDto;
import br.com.marcelpinotti.jsonviewsexample.dtos.views.UserViews;
import br.com.marcelpinotti.jsonviewsexample.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findByIdUser(@PathVariable("id") Long id) {
        UserDto user = service.findByIdUser(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = service.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @JsonView(UserViews.RestrictedData.class)
    @GetMapping("/restrict")
    public ResponseEntity<List<UserDto>> findAllRestrictedUsers(String s) {
        List<UserDto> users = service.findAllUsers();
        return ResponseEntity.ok().body(users);
    }
    @JsonView(UserViews.SaveData.class)
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto saveData) {
        UserDto user = service.saveUser(saveData);
        return ResponseEntity.ok().body(user);
    }

    @JsonView(UserViews.CompleteData.class)
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id")Long id, @RequestBody UserDto updatedData) {
        UserDto user = service.updateUser(id, updatedData);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id")Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
