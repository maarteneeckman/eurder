package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.service.item.CreateItemDto;
import com.switchfully.eurder.service.item.ItemDto;
import com.switchfully.eurder.service.item.ItemMapper;
import com.switchfully.eurder.service.item.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {

    @Test
    void addItem_returnsCorrectDto(){
        //given
        ItemController itemController = new ItemController(new ItemService(new ItemRepository(), new ItemMapper()));
        CreateItemDto createItemDto = new CreateItemDto(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        Item item = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        ItemDto expectedItemDto = new ItemDto(item);
        //when
        ItemDto actual = itemController.addItem(createItemDto);
        //then
        Assertions.assertThat(actual).isEqualTo(expectedItemDto);
    }


    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createItem_whenItemIsNotValid_returnStatus400() {
        WebTestClient.ResponseSpec response = this.webTestClient.post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createCustomer_whenCustomerIsValid_returnStatus201() {
        CreateItemDto createItemDto = new CreateItemDto(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        WebTestClient.ResponseSpec response = webTestClient.post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createItemDto), CreateItemDto.class)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.CREATED);
    }


}