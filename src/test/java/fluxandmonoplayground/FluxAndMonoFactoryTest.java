package fluxandmonoplayground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

	List<String> alist = Arrays.asList("My", "name", "is", "Nikhil");

	@Test
	public void fluxIterable() {
		Flux<String> nameFlux = Flux.fromIterable(alist).log();

		StepVerifier.create(nameFlux).expectNext("My", "name", "is", "Nikhil").verifyComplete();

	}

	@Test
	public void fluxUsingArray() {
		String[] name = new String[] { "My", "name", "is", "Nikhil" };

		Flux<String> nameFlux = Flux.fromArray(name).log();

		StepVerifier.create(nameFlux).expectNext("My", "name", "is", "Nikhil").verifyComplete();

	}

	@Test
	public void fluxUsingStream() {
		String[] names = { "What is ", "you", "name", "Boss" };
	
		Flux<String> nameFlux = Flux.fromStream(Arrays.stream(names)).log();

		StepVerifier.create(nameFlux).expectNext("What is ", "you", "name", "Boss").verifyComplete();

	}

	@Test
	public void monoUsingJustEmpty() {
		
		//Mono<String> mono=Mono.justOrEmpty("BLaH BLaH");
		Mono<String> mono=Mono.justOrEmpty(null);
		
		//StepVerifier.create(mono.log()).expectNext("BLaH BLaH").verifyComplete();
		StepVerifier.create(mono.log()).verifyComplete();
	}

	
	@Test
	public void monoUsingSupplier() {
		
		Supplier<String> supplierString= () -> "Nikhil Goyal";
		Mono<String> data=Mono.fromSupplier(supplierString).log();
		System.out.println("##################"+supplierString.get());
		
		StepVerifier.create(data).expectNext("Nikhil Goyal").verifyComplete();
	}
	
	@Test
	public void fluxUsingRange() {
		
		Flux<Integer> intergerFlux=Flux.range(1, 4).log();
	
		System.out.println("@@@@@@@@@@@@"+intergerFlux.blockFirst());
		
		StepVerifier.create(intergerFlux).expectNext(1,2,3,4).verifyComplete();
	}
}
