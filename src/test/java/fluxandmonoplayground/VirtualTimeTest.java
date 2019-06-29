 package fluxandmonoplayground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class VirtualTimeTest {

	
	@Test
	public void testoutWithoutVirtualTime()
	{
		Flux<Long> longflux=Flux.interval(Duration.ofSeconds(1))
				.take(3);
		
		
		StepVerifier.create(longflux.log())
		.expectSubscription()
		.expectNext(0l,1l,2l)
		.verifyComplete();
	}
}
