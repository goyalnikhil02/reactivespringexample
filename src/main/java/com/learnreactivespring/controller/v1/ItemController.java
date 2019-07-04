package com.learnreactivespring.controller.v1;

import static com.learnreactivespring.constant.ItemConstant.END_POINT_V1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReativeRepository;

import io.netty.handler.codec.http.multipart.HttpPostStandardRequestDecoder;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemController {

	@Autowired
	ItemReativeRepository repo;

	@GetMapping(END_POINT_V1)
	public Flux<Item> getallItems() {

		return repo.findAll();

	}

	@GetMapping(END_POINT_V1 + "/{id}")
	public Mono<ResponseEntity> getItem(@PathVariable String id) {

		return repo.findById(id).map(item -> new ResponseEntity(item, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));

	}

	@PostMapping(END_POINT_V1)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Item> createMonoItem(@RequestBody Item item) {
		return repo.save(item);
	}

	@DeleteMapping(END_POINT_V1 + "/{id}")
	// @ResponseStatus(HttpStatus.OK)
	public Mono<Void> deleteMonoItem(@PathVariable String id) {
		return repo.deleteById(id);
	}

	@PutMapping(END_POINT_V1 + "/{id}")
	// @ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<Object>> UpdateItem(@PathVariable String id, @RequestBody Item item) {

		return repo.findById(id).flatMap((currentItem) -> {
			currentItem.setPrice(item.getPrice());
			currentItem.setDescription(item.getDescription());
			return repo.save(currentItem);
		}).map(updatedItem -> new ResponseEntity<>(HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	
	}

}
