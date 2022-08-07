package br.com.marcelpinotti.jsonviewsexample.services;

import br.com.marcelpinotti.jsonviewsexample.dtos.user.UserDto;
import java.util.List;

public interface UserService {

    public UserDto saveUser(UserDto userDto);
    public List<UserDto> findAllUsers();
    public UserDto findByIdUser(Long id);
    public UserDto updateUser(Long id, UserDto updatedData);
    public void deleteUser(Long id);
}
