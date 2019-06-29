package com.learnreactivespring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.learnreactivespring.document.Item;

import reactor.core.publisher.Flux;

public interface ItemReativeRepository  extends ReactiveMongoRepository<Item, String> {

	Flux<Item> findByDescription(String name);
	
	
}
