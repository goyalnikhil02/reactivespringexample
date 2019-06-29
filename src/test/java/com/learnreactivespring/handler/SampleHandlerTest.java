package com.learnreactivespring.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class SampleHandlerTest {

	@Autowired
	WebTestClient webTestClient;

	@Test
   public void testFluxController()
   {
	   Flux<Integer> intergerFlux = webTestClient.get().uri("/functional/flux")
               .accept(MediaType.APPLICATION_JSON)
               .exchange()
               .expectStatus().isOk()
               .returnResult(Integer.class)
               .getResponseBody();

       StepVerifier.create(intergerFlux)
               .expectSubscription()
               .expectNext(1)
               .expectNext(2)
               .expectNext(3)
               .expectNext(4)
               .expectNext(5)
               .verifyComplete();

   }


	@Test
	   public void testMonoController()
	   {
		Flux<String> FluxStringData=Flux.just("Hi Mono");
		
		   Flux<String> intergerFlux = 
				   webTestClient.get().uri("/functional/mono")
	               .accept(MediaType.APPLICATION_JSON)
	               .exchange()
	               .expectStatus().isOk()
	               .returnResult(String.class)
	               .getResponseBody();

	               /*.consumeWith((  response) -> {
	            	   
	            	   //assertEquals(FluxStringData, response.getResponseBody().any(FluxStringData));
	            	   assertEquals("Hi Mono", response.getResponseBody().toString());
	               });*/
	               //.getResponseBody();

	       StepVerifier.create(intergerFlux)
	               .expectSubscription()
	               .expectNext("Hi Mono")
	               .verifyComplete();

	   }
}


