package fluxandmonoplayground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdPublisherTest {
	
	
	@Test
	public void coldPublisherTest() throws InterruptedException
	{
		Flux<String> data=Flux.just("a","b","c","d")
				.delayElements(Duration.ofSeconds(1));
		
		
		data.subscribe(s -> System.out.println("Subscriber 1 :"+ s));
		Thread.sleep(2000);

		
		

		data.subscribe(a -> System.out.println("Subscriber 2 :"+ a)); //get the value from the beginnning always.that is default behaviour.
		Thread.sleep(4000);
		
		
	}

	@Test
	public void hotPublisherTest() throws InterruptedException
	{
		Flux<String> data=Flux.just("a","b","c","d")
				.delayElements(Duration.ofSeconds(1));
		
		
		ConnectableFlux<String> connectableflux=data.publish();
		connectableflux.connect();
		
		connectableflux.subscribe(s -> System.out.println("Subscriber 1 :"+ s));
		
		
		Thread.sleep(2000);

		
		connectableflux.subscribe(s -> System.out.println("Subscriber 2 :"+ s)); //emit the value from current poition of active flux
		

		Thread.sleep(4000);
		
		
	}
}
