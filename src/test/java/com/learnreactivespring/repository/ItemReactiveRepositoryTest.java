package com.learnreactivespring.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.learnreactivespring.document.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveRepositoryTest {

	@Autowired
	ItemReativeRepository repo;

	List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.12), new Item(null, "LG TV", 1412.12),
			new Item(null, "watch", 123.12), new Item("NIKHIL", "apple TV", 900.12));

	@Before
	public void setup() {
		repo.deleteAll().thenMany(Flux.fromIterable(itemList)).flatMap(repo::save).doOnNext(item -> {
			System.out.println("Item insert is :" + item);
		}).blockLast();

	}

	@Test
	public void getallItemsTest() {
		StepVerifier.create(repo.findAll()).expectSubscription().expectNextCount(4).verifyComplete();

	}

	@Test
	public void getItemsByIdTest() {

		StepVerifier.create(repo.findById("NIKHIL")).expectSubscription()
				// .expectNextCount(1)
				.expectNextMatches(item -> item.getDescription().equals("apple TV") && item.getPrice() == 900.12)
				.verifyComplete();

	}

	@Test
	public void findItembyDescriptionTest() {

		StepVerifier.create(repo.findByDescription("apple TV")).expectSubscription()
				.expectNextCount(1)
				//.expectNextMatches(item -> item.getDescription().equals("apple TV") && item.getPrice() == 900.12)
				.verifyComplete();

	}

	@Test
	public void saveItemTest() {
       Item  item=new Item("Google","Casting", 499.99);
       Mono<Item> insertItem =repo.save(item);
       
		StepVerifier.create(insertItem.log()).expectSubscription()
				//.expectNextCount(1)
				.expectNextMatches(item1 -> item1.getDescription().equals("Casting") && item.getPrice() == 499.99)
				.verifyComplete();

	}
	
	@Test
	public void UpdateItemTest() {
		
       double newPrice=420.12;
       
       Flux<Item> updateItem=repo.findByDescription("apple TV")       
       .map(item -> {
           item.setPrice(newPrice);//seting the new price
           return item;
       })
       .flatMap(item -> {
    	   return repo.save(item).log(); //saving the item with nbew price
       });
       
       StepVerifier.create(updateItem)
       .expectSubscription()
		// .expectNextCount(1)
		.expectNextMatches(item -> item.getPrice() == 420.12)
		.verifyComplete();

	}
	
	@Test
	public void deleteItemByIdTest() {
		
		
		Mono<Void> itemDeleted=repo.findById("Nikhil")
				.map(Item::getId)
				.flatMap(id ->{
					return repo.deleteById(id);
				});
		
		
		
		
       StepVerifier.create(itemDeleted.log())
       .expectSubscription()
		// .expectNextCount(1)
		//.expectNextMatches(item -> item.getPrice() == 420.12)
		.verifyComplete();
       
      StepVerifier.create(repo.findAll().log("The data is :"))
       .expectSubscription()
		.expectNextCount(4)
		//.expectNextMatches(item -> item.getPrice() == 420.12)
		.verifyComplete();

	}
}
