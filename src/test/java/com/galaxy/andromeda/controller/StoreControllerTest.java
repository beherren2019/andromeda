package com.galaxy.andromeda.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.andromeda.controller.StoreController;
import com.galaxy.andromeda.service.StoreService;
import com.galaxy.andromeda.vo.store.StoreVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @MockBean
    private StoreService storeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenStoreIdNull_thenStatus_BadRequest() throws Exception {

        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/stores/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(result -> assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; " +
                        "nested exception is java.lang.NumberFormatException: " +
                        "For input string: \"null\"", result.getResolvedException().getMessage()));

    }

    @Test
    void whenStoreIdNullToDelete_thenStatus_BadRequest() throws Exception {

        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.delete("/stores/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(result -> assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; " +
                        "nested exception is java.lang.NumberFormatException: " +
                        "For input string: \"null\"", result.getResolvedException().getMessage()));

    }

    @Test
    void whenDeleteStoreWithValidId_thenStatus_BadRequest() throws Exception {

        Long id = 5l;

        mockMvc.perform(MockMvcRequestBuilders.delete("/stores/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void whenStoreIdWithValidId_thenStatus_isOk() throws Exception {

        Long id = 5l;

        mockMvc.perform(MockMvcRequestBuilders.get("/stores/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenAddStoreWithoutMandatoryValues_thenBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(StoreVO.builder()
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store country is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store city is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store street number is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store street name is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store name is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store code is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Store zipcode is mandatory!]")));

    }

    @Test
    void whenCreateStoreWithValidInputs_thenStatus_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(StoreVO.builder()
                                .code("code 123")
                                .streetNumber("123")
                                .zipcode("12112")
                                .country("DE")
                                .streetName("WEDRRERR")
                                .name("name123")
                                .city("BERLIN")
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenEditStoreWithValidInputs_thenStatus_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/stores/" + 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(StoreVO.builder()
                                .code("code 124")
                                .streetNumber("123")
                                .zipcode("12112")
                                .country("DE")
                                .streetName("WEDRRERR")
                                .name("name123")
                                .city("BERLIN")
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
