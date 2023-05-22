package com.galaxy.andromeda.service;

import com.galaxy.andromeda.controller.VersionControlRepositoryController;
import com.galaxy.andromeda.vo.GithubDataVO;
import com.galaxy.andromeda.vo.GithubResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VersionControlRepositoryController.class)
public class VersionControlRepositoryServiceTest {

    @MockBean
    private VersionControlRepositoryService versionControlRepositoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenDateParamIsEmpty_thenStatus_InternalServerError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/github")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
                .andExpect(result -> assertEquals("Required request parameter 'date' for method parameter type LocalDate is not present", result.getResolvedException().getMessage()));;

    }

    @Test
    void whenDataParamFormatWrong_thenStatus_BadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/github?date=10.10.2020")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(result -> assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate'; " +
                        "nested exception is org.springframework.core.convert.ConversionFailedException: " +
                        "Failed to convert from type [java.lang.String] to type [@javax.validation.Valid @org.springframework.lang.NonNull " +
                        "@org.springframework.format.annotation.DateTimeFormat @org.springframework.web.bind.annotation.RequestParam java.time.LocalDate] " +
                        "for value '10.10.2020'; " +
                        "nested exception is java.lang.IllegalArgumentException: Parse attempt failed for value [10.10.2020]", result.getResolvedException().getMessage()));

    }

    @Test
    void whenDateParamIsNonEmpty_thenStatus_Success() throws Exception {

        when(versionControlRepositoryService.getGithubRepositories(anyInt(), anyInt(), anyString(), anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(GithubResponseVO.builder()
                        .itemsPerRequest(10)
                        .total(1000l)
                        .isIncompleteResult(true)
                        .items(Arrays.asList(
                                GithubDataVO.builder()
                                        .nodeId("node_id_abc")
                                        .build()
                        ))
                        .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/github?date=2020-10-10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
