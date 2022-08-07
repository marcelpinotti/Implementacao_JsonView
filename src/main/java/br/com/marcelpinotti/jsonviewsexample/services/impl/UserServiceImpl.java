package br.com.marcelpinotti.jsonviewsexample.services.impl;

import br.com.marcelpinotti.jsonviewsexample.dtos.user.UserDto;
import br.com.marcelpinotti.jsonviewsexample.entities.User;
import br.com.marcelpinotti.jsonviewsexample.execption.ObjectNotFoundException;
import br.com.marcelpinotti.jsonviewsexample.repositories.UserRepository;
import br.com.marcelpinotti.jsonviewsexample.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository repository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        repository.save(mapper.map(userDto, User.class));
        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return repository.findAll()
                .stream().map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findByIdUser(Long id) {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()) {
            return mapper.map(user.get(), UserDto.class);
        } else {
            throw new ObjectNotFoundException("User not found");
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto updatedData) {
        UserDto userDto = findByIdUser(id);
        userDto.setName(updatedData.getName());
        userDto.setLastname(updatedData.getLastname());
        userDto.setEmail(updatedData.getEmail());
        userDto.setPassword(updatedData.getPassword());
        repository.save(mapper.map(userDto,User.class));
        return userDto;
    }

    @Override
    public void deleteUser(Long id) {
        findByIdUser(id);
        repository.deleteById(id);
    }
}
