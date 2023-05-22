package com.galaxy.andromeda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.andromeda.controller.ProductController;
import com.galaxy.andromeda.enums.ProductType;
import com.galaxy.andromeda.service.ProductService;
import com.galaxy.andromeda.vo.store.ProductVO;
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
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenProductIdNull_thenStatus_BadRequest() throws Exception {

        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(result -> assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; " +
                        "nested exception is java.lang.NumberFormatException: " +
                        "For input string: \"null\"", result.getResolvedException().getMessage()));

    }

    @Test
    void whenProductIdNullToDelete_thenStatus_BadRequest() throws Exception {

        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(result -> assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; " +
                        "nested exception is java.lang.NumberFormatException: " +
                        "For input string: \"null\"", result.getResolvedException().getMessage()));

    }

    @Test
    void whenDeleteProductWithValidId_thenStatus_BadRequest() throws Exception {

        Long id = 5l;

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void whenGetProductIdWithValidId_thenStatus_isOk() throws Exception {

        Long id = 5l;

        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenAddProductsWithoutMandatoryValues_thenBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ProductVO.builder()
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Product name is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Product code is mandatory!]")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("[Product type is mandatory!, any of VEG, NON_VEG, VEGAN]")));
    }

    @Test
    void whenCreateProductWithValidInputs_thenStatus_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ProductVO.builder()
                                .code("code 123")
                                .name("prod 1")
                                .type(ProductType.VEG)
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenEditProductWithValidInputs_thenStatus_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/" + 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ProductVO.builder()
                                .code("code 123")
                                .name("prod 1")
                                .type(ProductType.VEG)
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
