package com.learnreactivespring.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnreactivespring.document.Item;
import com.learnreactivespring.repository.ItemReativeRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import static com.learnreactivespring.constant.ItemConstant.END_POINT_V1;
@RestController
@Slf4j
public class ItemController {
	
	@Autowired
	ItemReativeRepository repo; 
	
	
	@GetMapping(END_POINT_V1)
	public Flux<Item> getallItems(){
		
		return repo.findAll();
		
		
	}

}
