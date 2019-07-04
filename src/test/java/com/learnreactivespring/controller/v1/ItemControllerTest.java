package com.learnreactivespring.controller.v1;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.learnreactivespring.constant.ItemConstant;
import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReativeRepository;

import ch.qos.logback.core.net.SyslogOutputStream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ItemControllerTest {

	@Autowired
	ItemReativeRepository repo;

	@Autowired
	WebTestClient client;

	public List<Item> data() {

		return Arrays.asList(new Item("Kanishk", "Samsung TV", 400.12), new Item(null, "LG TV", 1412.12),
				new Item(null, "watch", 123.12), new Item("NIKHIL", "apple TV", 900.12));

	}

	@Before
	public void setup() {
		repo.deleteAll().thenMany(Flux.fromIterable(data())).flatMap(repo::save)
				// .thenMany(repo.findAll())
				.doOnNext(item -> {
					System.out.println("Item is inserted from Command line" + item);
				}).blockLast();

	}

	@Test
	public void getallItemsTest() {
		client.get().uri(ItemConstant.END_POINT_V1).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON_UTF8).expectBodyList(Item.class).hasSize(4);
	}

	@Test
	public void getallItemsTest_approach2() {
		client.get().uri(ItemConstant.END_POINT_V1).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON_UTF8).expectBodyList(Item.class).hasSize(4)
				.consumeWith((response) -> {
					List<Item> items = response.getResponseBody();
					// items.forEach(item -> System.out.println(item.getId()));
					items.forEach((item) -> {
						assertTrue(item.getId() != null);
					});
				});
	}

	@Test
	public void getallItemsTest_approach3() {
		Flux<Item> itemFlux = client.get().uri(ItemConstant.END_POINT_V1).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).returnResult(Item.class).getResponseBody();

		StepVerifier.create(itemFlux).expectNextCount(4).verifyComplete();
	}

	@Test
	public void getItemsTest_approach1() {
		client.get().uri(ItemConstant.END_POINT_V1.concat("/{id}"), "NIKHIL").exchange().expectStatus().isOk()
				.expectBody().jsonPath("$.price", 900.12);
	}

	@Test
	public void getItemsTest_approach2() {
		client.get().uri(ItemConstant.END_POINT_V1.concat("/{id}"), "Nikhil").exchange().expectStatus().isNotFound();

	}

	@Test
	public void createMonoItem_Test() {
		Item item = new Item("Kanishk New", "Samsung Watch", 141.12);

		client.post().uri(ItemConstant.END_POINT_V1, "Nikhil").contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(item), Item.class).exchange().expectStatus().isCreated().expectBody()
				.jsonPath("$.price", 141.12);

	}

	@Test
	public void deleteMonoItem_Test() {
		client.delete().uri(ItemConstant.END_POINT_V1.concat("/{id}"), "NIKHIL1")
		.accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk()
				.expectBody(String.class);

	}

	
	@Test
	public void updateItem_Test() {
		
		double price =121.12;
		
		Item item = new Item("NIKHIL", "Apppple  Watch", price);

		
		
		client.put().uri(ItemConstant.END_POINT_V1.concat("/{id}"), "DEF")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(item), Item.class)
		.exchange()
		.expectStatus().isOk()
		.expectBody();

	}
	
	@Test
    public void testDeleteOneItem() {

        client.delete().uri(ItemConstant.FUNCTIONAL_END_POINT_V1.concat("/{id}"), "NIKHIL")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

    }
	@Test
	public void testUpdateItem() {
	    double newPrice=129.99;
	    Item item = new Item(null,"Beats HeadPhones", newPrice);
	    client.put().uri(ItemConstant.FUNCTIONAL_END_POINT_V1.concat("/{id}"), "NIKHIL")
	            .contentType(MediaType.APPLICATION_JSON_UTF8)
	            .accept(MediaType.APPLICATION_JSON_UTF8)
	            .body(Mono.just(item), Item.class)
	            .exchange()
	            .expectStatus().isOk()
	            .expectBody()
	            .jsonPath("$.price",newPrice);

	}
}
