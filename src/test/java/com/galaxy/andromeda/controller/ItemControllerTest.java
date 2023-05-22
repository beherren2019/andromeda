package com.galaxy.andromeda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.andromeda.service.StoreItemService;
import com.galaxy.andromeda.spec.ItemSpecification;
import com.galaxy.andromeda.vo.store.StoreItemVO;
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
@WebMvcTest(StoreItemController.class)
public class ItemControllerTest {

    @MockBean
    private StoreItemService storeItemService;

    @MockBean
    ItemSpecification itemSpecification;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenProductIdNullToDelete_thenStatus_BadRequest() throws Exception {

        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.delete("/stores/" + 1 + "/products/" + 1 + "/items/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(result -> assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; " +
                        "nested exception is java.lang.NumberFormatException: " +
                        "For input string: \"null\"", result.getResolvedException().getMessage()));

    }

    @Test
    void whenDeleteItemWithValidId_thenStatus_BadRequest() throws Exception {

        Long id = 5l;

        mockMvc.perform(MockMvcRequestBuilders.delete("/stores/" +1 + "/products/" + 1 + "/items/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void whenAddItemsWithoutMandatoryValues_thenBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/stores/" +1 + "/products/" + 1 + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(StoreItemVO.builder()
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Item storeId is mandatory!")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Item productId is mandatory!")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Item name is mandatory!")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Item quantity must be greater than 0!")));
    }

    @Test
    void whenCreateItemWithValidInputs_thenStatus_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/stores/" +1 + "/products/" + 1 + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(StoreItemVO.builder()
                                .productId(1l)
                                .storeId(1l)
                                .name("prod 1")
                                .itemQuantity(10)
                                .build()).getBytes()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenEditItemWithValidInputs_thenStatus_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/stores/" +1 + "/products/" + 1 + "/items/" + 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(StoreItemVO.builder()
                                .name("prod 1")
                                .itemQuantity(19)
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
