package org.projectx.platform.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.common.DataMapper;
import org.projectx.platform.orderservice.dto.UserDTO;
import org.projectx.platform.orderservice.entity.UserEntity;
import org.projectx.platform.orderservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userService.saveUser(DataMapper.convertUserDTOToUserEntity(userDTO));
        return ResponseEntity.ok(DataMapper.convertUserEntityToUserDTO(userEntity));
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<UserDTO>> getUsers() {
        Iterable<UserEntity> users = userService.getAllUsers();
        List<UserDTO> userDTOList = new ArrayList<>();
        Iterator<UserEntity> iterator = users.iterator();
        while(iterator.hasNext()) {
            userDTOList.add(DataMapper.convertUserEntityToUserDTO(iterator.next()));
        }
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUsers(@PathVariable long id) {
        try
        {
            UserEntity userEntity = userService.getUser(id).orElseThrow(() -> new RuntimeException("User not found...!!!"));
            return ResponseEntity.ok(DataMapper.convertUserEntityToUserDTO(userEntity));
        } catch (Exception ex) {
            log.error("user id: {} , {}", id, ex.getMessage());
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage())).build();
        }
    }

}
