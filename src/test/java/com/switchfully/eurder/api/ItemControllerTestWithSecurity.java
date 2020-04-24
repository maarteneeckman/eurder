//package com.switchfully.eurder.api;
//
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
//import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;
//
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = ItemController.class)
//class ItemControllerTestWithSecurity {
//
//
//    @Autowired
//    ApplicationContext context;
//    WebTestClient webTestClient;
//
//    @BeforeEach
//    public void setup() {
//        this.webTestClient = WebTestClient
//                .bindToApplicationContext(this.context)
//                // add Spring Security test Support
//                .apply(springSecurity())
//                .configureClient()
//                .filter(basicAuthentication())
//                .build();
//    }
//
//   @Test
//    @WithMockUser(roles = "ADMIN")
//    void createItem_whenItemIsNotValid_returnStatus400() {
//
//        WebTestClient.ResponseSpec response = this.webTestClient.post()
//                .uri("/items")
//                .contentType(MediaType.APPLICATION_JSON)
//                .exchange();
//        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//}