package com.davidechiarelli.taxcodeapi.rest;

import com.davidechiarelli.taxcodeapi.model.User;
import com.davidechiarelli.taxcodeapi.service.impl.TaxCodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaxCodeControllerTest {
    private TaxCodeController apiController;
    private TaxCodeServiceImpl taxCodeService = mock(TaxCodeServiceImpl.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        apiController = new TaxCodeController(taxCodeService);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    void calculateTaxCode200() throws Exception {
        when(taxCodeService.calculateTaxCode(any())).thenReturn("a-valid-tax-code");

        String jsonRequest="{\n" +
                "  \"firstName\": \"Davide\",\n" +
                "  \"lastName\": \"Chiarelli\",\n" +
                "  \"gender\": \"M\",\n" +
                "  \"dateOfBirth\": \"1989-12-18\",\n" +
                "  \"cityOfBirth\": \"Mottola\",\n" +
                "  \"provinceOfBirth\": \"TA\"\n" +
                "}";

        mockMvc.perform(post("/calculateTaxCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void calculateTaxCode400_missingField() throws Exception {
        when(taxCodeService.calculateTaxCode(any())).thenReturn("a-valid-tax-code");

        String jsonRequest="{\n" +
                "  \"lastName\": \"Chiarelli\",\n" +
                "  \"gender\": \"M\",\n" +
                "  \"dateOfBirth\": \"1989-12-18\",\n" +
                "  \"cityOfBirth\": \"Mottola\",\n" +
                "  \"provinceOfBirth\": \"TA\"\n" +
                "}";

        mockMvc.perform(post("/calculateTaxCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateTaxCode400_emptyTaxCode() throws Exception {
        when(taxCodeService.calculateTaxCode(any())).thenReturn(" ");

        String jsonRequest="{\n" +
                "  \"firstName\": \"Empty\",\n" +
                "  \"lastName\": \"Response\",\n" +
                "  \"gender\": \"M\",\n" +
                "  \"dateOfBirth\": \"1989-12-18\",\n" +
                "  \"cityOfBirth\": \"Mottola\",\n" +
                "  \"provinceOfBirth\": \"TA\"\n" +
                "}";

        mockMvc.perform(post("/calculateTaxCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void parseUser200() throws Exception {
        when(taxCodeService.parseUser(any())).thenReturn(getUser1());

        String jsonRequest="{\n" +
                "  \"taxCode\": \"NREPLA89M12H501H\"\n" +
                "}";

        mockMvc.perform(post("/parseUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void parseUser400_nullUser() throws Exception {
        when(taxCodeService.parseUser(any())).thenReturn(null);

        String jsonRequest="{\n" +
                "  \"taxCode\": \"NREPLA89M12H501H\"\n" +
                "}";

        mockMvc.perform(post("/parseUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void parseUser400_blankTaxCode() throws Exception {
        when(taxCodeService.parseUser(any())).thenReturn(getUser1());

        String jsonRequest="{\n" +
                "  \"taxCode\": \"\"\n" +
                "}";

        mockMvc.perform(post("/parseUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void parseUser400_tooMuchChars() throws Exception {
        when(taxCodeService.parseUser(any())).thenReturn(getUser1());

        String jsonRequest="{\n" +
                "  \"taxCode\": \"NREPLA89M12H501HAAAAAAAAAAAAAAAAAAA\"\n" +
                "}";

        mockMvc.perform(post("/parseUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateTaxCode400_handleBadCityError() throws Exception {
        when(taxCodeService.calculateTaxCode(any())).thenReturn("a-valid-tax-code");

        String jsonRequest="{\n" +
                "  \"firstName\": \"Davide\",\n" +
                "  \"lastName\": \"Chiarelli\",\n" +
                "  \"gender\": \"M\",\n" +
                "  \"dateOfBirth\": \"1989-12-18\",\n" +
                "  \"cityOfBirth\": \"Mottola\",\n" +
                "  \"provinceOfBirth\": \"TA\"\n" +
                "}";

        mockMvc.perform(post("/calculateTaxCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private User getUser1(){
        return new User("Paolo",
                "Neri",
                "M",
                LocalDate.of(1989, 8, 12),
                "Roma",
                "RM");
    }
}
