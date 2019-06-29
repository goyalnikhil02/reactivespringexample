package com.learnreactivespring.initializer;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReativeRepository;

import reactor.core.publisher.Flux;

@Component
public class ItemDataIntializer implements CommandLineRunner {

	@Autowired
	ItemReativeRepository repo;

	@Override
	public void run(String... args) throws Exception {
		initalDatasetup();

	}

	public List<Item> data() {

		return Arrays.asList(new Item("Kanishk", "Samsung TV", 400.12), new Item(null, "LG TV", 1412.12),
				new Item(null, "watch", 123.12), new Item("NIKHIL", "apple TV", 900.12));

	}

	private void initalDatasetup() {

		repo.deleteAll().thenMany(Flux.fromIterable(data())).flatMap(repo::save)
				.thenMany(repo.findAll())
				.subscribe(item -> {
					System.out.println("Item is inserted from Command line" + item);

				});

	}

	

}
