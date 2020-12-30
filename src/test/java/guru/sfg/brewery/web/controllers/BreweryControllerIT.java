package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BreweryControllerIT extends BaseIT {

    @Test
    void listBreweryAsCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/breweries")
                .with(httpBasic("scott","tiger")))
                .andExpect(status().isOk());
    }
    @Test
    void listBreweryAsUser() throws Exception {
        mockMvc.perform(get("/api/v1/breweries")
                .with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }
    @Test
    void listBreweryAsAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/breweries")
                .with(httpBasic("spring","guru")))
                .andExpect(status().isForbidden());
    }

}
