package br.com.marcelpinotti.jsonviewsexample.controllers;

import br.com.marcelpinotti.jsonviewsexample.dtos.user.UserDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.marcelpinotti.jsonviewsexample.execption.handler.ControllerExceptionHandler;
import br.com.marcelpinotti.jsonviewsexample.services.UserService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
class UserControllerTest {

    private static final Long ID_ONE = 1L;
    private static final String NAME_ONE = "Marcel";
    private static final String LASTNAME_ONE = "Pinotti";
    private static final String EMAIL_ONE = "marcel@xpto.com";
    private static final String PASSWORD_ONE = "Marcel@123";
    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "Pedro";
    private static final String LASTNAME_TWO = "Pires";
    private static final String EMAIL_TWO = "pedro@xpto.com";
    private static final String PASSWORD_TWO = "Pedro@123";

    private UserDto userOne;
    private UserDto userTwo;

    private UserDto userUpdate;

    private final List<UserDto> list = new ArrayList<>();

    @Mock
    private UserService service;

    private MockMvc mockMvc;

    @InjectMocks
    private UserController controller;

    void initializingObjects() {
        userOne = new UserDto(ID_ONE,NAME_ONE, LASTNAME_ONE, EMAIL_ONE, PASSWORD_ONE);
        userTwo = new UserDto(ID_TWO, NAME_TWO, LASTNAME_TWO, EMAIL_TWO, PASSWORD_TWO);
        userUpdate = new UserDto(ID_TWO, NAME_ONE, LASTNAME_ONE, EMAIL_ONE, PASSWORD_ONE);
        list.add(userOne);
        list.add(userTwo);

    }

    //This is required for JsonViews. (É necessário para o JsonViews)
    public static MappingJackson2HttpMessageConverter createJacksonConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @BeforeEach
    void setUp() {
        //Build the controller mock handler (Construção do manipulador de simulação do controller)
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .setMessageConverters(createJacksonConverter())
                .build();

        initializingObjects();
    }

    @Test
    void testFindByIdUser() throws Exception {
        Mockito.when(service.findByIdUser(ID_ONE)).thenReturn(userOne);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Marcel\",\"lastname\":\"Pinotti\"," +
                        "\"email\":\"marcel@xpto.com\",\"password\":\"Marcel@123\"}"));
    }

    @Test
    void testFindAllUsers() throws Exception {
        Mockito.when(service.findAllUsers()).thenReturn(list);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"Marcel\",\"lastname\":\"Pinotti\"," +
                        "\"email\":\"marcel@xpto.com\",\"password\":\"Marcel@123\"},{\"id\":2,\"name\":\"Pedro\"," +
                        "\"lastname\":\"Pires\",\"email\":\"pedro@xpto.com\",\"password\":\"Pedro@123\"}]"));
    }

    @Test
    void testFindAllRestrictedUsers() throws Exception {
        Mockito.when(service.findAllUsers()).thenReturn(list);

        mockMvc.perform(get("/user/restrict"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"Marcel\",\"lastname\":\"Pinotti\"," +
                        "\"email\":\"marcel@xpto.com\"},{\"id\":2,\"name\":\"Pedro\",\"lastname\":" +
                        "\"Pires\",\"email\":\"pedro@xpto.com\"}]"));
    }

    @Test
    void testSaveUser() throws Exception {
        Mockito.when(service.saveUser(Mockito.any())).thenReturn(userOne);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userOne))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"Marcel\",\"lastname\":\"Pinotti\"," +
                        "\"email\":\"marcel@xpto.com\",\"password\":\"Marcel@123\"}"));
    }

    @Test
    void testUpdateUser() throws Exception {
        Mockito.when(service.findByIdUser(ID_TWO)).thenReturn(userTwo);
        Mockito.when(service.updateUser(ID_TWO, userTwo)).thenReturn(userUpdate);

        mockMvc.perform(put("/user/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdate))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"name\":\"Marcel\",\"lastname\":\"Pinotti\"," +
                        "\"email\":\"marcel@xpto.com\",\"password\":\"Marcel@123\"}"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.when(service.findByIdUser(ID_ONE)).thenReturn(userOne);
        Mockito.doNothing().when(service).deleteUser(ID_ONE);

        mockMvc.perform(delete("/user/2"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}