package com.learnreactivespring.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReativeRepository;

import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ItemHandler {

	@Autowired
	ItemReativeRepository repo;

	static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

	public Mono<ServerResponse> getAllItem(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repo.findAll(), Item.class);
	}

	public Mono<ServerResponse> getItemById(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Item> data = repo.findById(id);
		return data.flatMap(item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(item))
				.switchIfEmpty(notFound));

	}

	public Mono<ServerResponse> createItem(ServerRequest request) {
		Mono<Item> itemtoBeInsert = request.bodyToMono(Item.class);

		return itemtoBeInsert.flatMap(
				item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repo.save(item), Item.class));

	}

	public Mono<ServerResponse> deleteItem(ServerRequest request) {

		String id = request.pathVariable("id");

		Mono<Void> data = repo.deleteById(id);

		return ServerResponse.ok().body(data, Void.class);

	}
}
