package fluxandmonoplayground;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoConbineTest {

	@Test
	public void testMerge() {

		Flux<String> flux1 = Flux.just("nikhil", "neelam", "any", "neeraj").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("NIKHIL", "AKHIL", "GOYAL");

		Flux<String> mergeFlux = Flux.merge(flux1, flux2).log();

		StepVerifier.create(mergeFlux).expectNextCount(7).verifyComplete();
	}

	
	@Test
	public void testMerge_WithDelay() {

		Flux<String> flux1 = Flux.just("nikhil", "neelam", "any", "neeraj").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("NIKHIL", "AKHIL", "GOYAL").delayElements(Duration.ofSeconds(1));

		Flux<String> mergeFlux = Flux.merge(flux1, flux2).log();   //merge will not wait for other

		StepVerifier.create(mergeFlux).expectNextCount(7).verifyComplete();
	}
	
	@Test
	public void testConcat_WithDelay() {

		Flux<String> flux1 = Flux.just("nikhil", "neelam", "any", "neeraj").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("NIKHIL", "AKHIL", "GOYAL").delayElements(Duration.ofSeconds(1));

		Flux<String> mergeFlux = Flux.concat(flux1, flux2).log();  //concat will wait till first flux completed

		StepVerifier.create(mergeFlux).expectNext("nikhil", "neelam", "any", "neeraj","NIKHIL", "AKHIL", "GOYAL").verifyComplete();
	}

	@Test
	public void testZip() {

		Flux<String> flux1 = Flux.just("A","B","C");
		Flux<String> flux2 = Flux.just("D","E","F");

		Flux<String> mergeFlux = Flux.zip(flux1, flux2,(t1,t2) ->{
			return t1.concat(t2);
		});

		StepVerifier.create(mergeFlux.log()).expectNext("AD", "BE","CF").verifyComplete();
	}
}
