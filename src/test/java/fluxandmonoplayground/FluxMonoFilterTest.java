package fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoFilterTest {

	List<String> alist = Arrays.asList("nikhil", "neelam", "any", "neeraj");

	@Test
	public void filterTest() {
		Flux<String> filterdata=Flux.fromIterable(alist)
				.filter((p) -> p.startsWith("n"))
				.log();
		
		StepVerifier.create(filterdata).expectNext("nikhil", "neelam", "neeraj").verifyComplete();

       	
	}
}
