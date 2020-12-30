package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;


    @DisplayName("Delete beer test")
    @Nested
    class DeleteTest {
        public Beer beerToDelete(){
            Beer beer = beerRepository.save(Beer.builder()
                    .beerName("Heineken")
                    .beerStyle(BeerStyleEnum.PILSNER)
                    .minOnHand(100)
                    .quantityToBrew(200)
                    .upc("654321")
                    .build());

            return beer;
        }

        @Test
        void deleteBeer() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete())
                    .header("Api-Key", "spring").header("Api-Secret", "guru"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerHttpBasicAdminRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete())
                    .with(httpBasic("spring","guru")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete())
                    .with(httpBasic("user","password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete())
                    .with(httpBasic("scott","tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerNoAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete()))
                    .andExpect(status().isUnauthorized());
        }


    }



//    @Test
//    void deleteBeerUruAuth() throws Exception {
//        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
//                .param("Api-Key", "spring").param("Api-Secret","guru"))
//                .andExpect(status().isOk());
//    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer")
                .with(httpBasic("spring","guru")))
//                .header("api-key", "spring").header("api-value", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeersByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }



}