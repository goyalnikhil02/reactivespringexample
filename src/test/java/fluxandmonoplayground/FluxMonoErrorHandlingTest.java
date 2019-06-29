package fluxandmonoplayground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoErrorHandlingTest {
	
	@Test
	public void handleErrorFlux()
	{
		Flux<String> flux1 = Flux.just("A","B","C")
				.concatWith(Flux.error(new RuntimeException("Exception OCccured")))
				.concatWith(Flux.just("D"))
				.onErrorResume(s ->{
					System.out.println("Exception is :"+ s);
					return Flux.just("Default");
				});
		
		StepVerifier.create(flux1.log()).expectNext("A","B","C")
	//	.expectError(RuntimeException.class)
		.expectNext("Default")
		.verifyComplete(); //when error is not come
		//.verify(); //when error will come
		
	}

	
	@Test
	public void handleErrorFlux_onErrorReturn()
	{
		Flux<String> flux1 = Flux.just("A","B","C")
				.concatWith(Flux.error(new RuntimeException("Exception OCccured")))
				.concatWith(Flux.just("D"))
				.onErrorReturn("Default");
				
		
		StepVerifier.create(flux1.log()).expectNext("A","B","C")
		.expectNext("Default")
		.verifyComplete(); //when error is not come
		
	
	}
	
	
	@Test
	public void handleErrorFlux_onErrorMap()
	{
		Flux<String> flux1 = Flux.just("A","B","C")
				.concatWith(Flux.error(new RuntimeException("Exception OCccured")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e));
				
		
		StepVerifier.create(flux1.log()).expectNext("A","B","C")
		.expectError(CustomException.class)
		.verify(); //when error is not come
		
	}
	
	@Test
	public void handleErrorFlux_onErrorMap_Retry()
	{
		Flux<String> flux1 = Flux.just("A","B","C")
				.concatWith(Flux.error(new RuntimeException("Exception OCccured")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e))
				//.retry(2);
				.retryBackoff(2, Duration.ofSeconds(5));
				
		
		StepVerifier.create(flux1.log())
		.expectNext("A","B","C")
		.expectNext("A","B","C")
		.expectNext("A","B","C")
		.expectError(IllegalStateException.class)
		.verify(); //when error is not come
		
	}
}
