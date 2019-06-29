package fluxandmonoplayground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoTimeTest {
	
	@Test
	public void infinteFlux() throws InterruptedException
	{
		Flux<Long> infinteFluxData = Flux.interval(Duration.ofSeconds(1))
				.log();
				
		infinteFluxData.subscribe(System.out::println);
		
		Thread.sleep(3000);
		
	}
	@Test
	public void infinteFluxTest() throws InterruptedException
	{
		Flux<Long> finteFluxData = Flux.interval(Duration.ofSeconds(1))
				.take(3)
				.log(); 
				
		
		StepVerifier.create(finteFluxData)
		.expectSubscription()
		.expectNext(0L,1L,2L)
		.verifyComplete(); //when error is not come
			//.verify(); //when error will come
			
		
		
	}


}
