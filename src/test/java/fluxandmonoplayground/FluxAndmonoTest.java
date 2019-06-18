package fluxandmonoplayground;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndmonoTest {

	@Test
	public void fluxTest() {
		Flux<String> fluxString = Flux.just("Spring", "Spring Boot", "Reactive Stream")
				/* .concatWith(Flux.error(new RuntimeException("Exception Occcured"))) */
				.concatWith(Flux.just("After Error")).log();

		fluxString.subscribe(System.out::println, (e) -> System.err.println("Exception : " + e.getMessage()),
				() -> System.out.println("Completed"));

	}

	@Test
	public void fluxTest_WithoutError() {
		Flux<String> fluxString = Flux.just("Nikhil", "Goyal", "TPM").log();

		StepVerifier.create(fluxString).expectNext("Nikhil").expectNext("Goyal").expectNext("TPM").verifyComplete();

	}

	@Test
	public void fluxTest_WithError() {
		Flux<String> fluxString = Flux.just("Nikhil", "Goyal", "TPM")
				.concatWith(Flux.error(new RuntimeException("Exception occurs"))).log();

		StepVerifier.create(fluxString).expectNext("Nikhil").expectNext("Goyal").expectNext("TPM")
				// .expectError(RuntimeException.class)
				.expectErrorMessage("Exception occurs").verify();

	}

	@Test
	public void fluxTest_WithError1() {
		Flux<String> fluxString = Flux.just("Nikhil", "Goyal", "TPM")
				.concatWith(Flux.error(new RuntimeException("Exception occurs"))).log();

		StepVerifier.create(fluxString).expectNext("Nikhil", "Goyal", "TPM")
				// .expectError(RuntimeException.class)
				.expectErrorMessage("Exception occurs").verify();

	}

	@Test
	public void fluxTest_Count() {
		Flux<String> fluxString = Flux.just("idemia", "smartchip", "morpho").log();

		StepVerifier.create(fluxString).expectNextCount(3).verifyComplete();

	}

	
	@Test
	public void MonoTest() {
		Mono<String> monoString= Mono.just("Nikhil >> MONO").log();
		
		
		StepVerifier.create(monoString).expectNext("Nikhil >> MONO").verifyComplete();

	}
	
	@Test
	public void MonoTest_Error() {
		Mono monoString= Mono.error(new RuntimeException("Exception occurs")).log();
		
		StepVerifier.create(monoString).expectError(RuntimeException.class).verify();

	}
}
