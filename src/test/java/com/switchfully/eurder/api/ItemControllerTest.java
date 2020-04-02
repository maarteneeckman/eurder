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
    void createItem_whenItemIsValid_returnStatus201() {
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

    @Test
    void getAllItems_returnsCorrectItemDtos(){
        //given
        CreateItemDto createItemDto1 = new CreateItemDto(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        webTestClient.post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createItemDto1), CreateItemDto.class)
                .exchange();
        Item item1 = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        ItemDto expectedItemDto1 = new ItemDto(item1);

        CreateItemDto createItemDto2 = new CreateItemDto(
                "Coffee",
                "Keeps you awake.",
                2,
                150);
        webTestClient.post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createItemDto2), CreateItemDto.class)
                .exchange();
        Item item2 = new Item(
                "Coffee",
                "Keeps you awake.",
                2.0,
                150);
        ItemDto expectedItemDto2 = new ItemDto(item2);

        WebTestClient.ResponseSpec response3 = webTestClient.get()
                .uri("/items")
                .exchange();
        response3.expectStatus().isOk()
                .expectBodyList(ItemDto.class)
                .contains(expectedItemDto1, expectedItemDto2);
    }


}