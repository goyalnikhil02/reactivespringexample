package fluxandmonoplayground;

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

}
