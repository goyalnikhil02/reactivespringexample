package fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static reactor.core.scheduler.Schedulers.parallel;

public class TransformFluxMonotest {

	List<String> alist = Arrays.asList("nikhil", "niikai", "any", "neeraj");

	@Test
	public void tansformUsingMap() {
		Flux<String> dataLower = Flux.fromIterable(alist).map(String::toUpperCase).log();
		StepVerifier.create(dataLower).expectNext("NIKHIL", "NIIKAI", "ANY", "NEERAJ").verifyComplete();

	}

	@Test
	public void tansformUsingMap_Repeat() {
		Flux<Integer> dataLower = Flux.fromIterable(alist).filter(a -> a.startsWith("n")).map(String::length) // 6 6 3 6
				.repeat(1).log();

		StepVerifier.create(dataLower).expectNext(6, 6, 6, 6, 6, 6).verifyComplete();

	}

	@Test
	public void tansformUsingFlatMap() {
		Flux<String> dataLower = Flux.fromIterable(alist).flatMap(s -> {
			return Flux.fromIterable(convertToList(s));
		}).log();

		StepVerifier.create(dataLower).expectNext("nikhil", "goyal", "niikai","goyal", "any","goyal","neeraj","goyal").verifyComplete();

	}

	
	
	 private List<String> convertToList(String s)  {

	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        return Arrays.asList(s, "goyal");
	    }
	
	 @Test //maintian order usaing other option of flatmap
	    public void tranformUsingFlatMap_usingparallel(){

	        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) // Flux<String>
	                .window(2)
	                //.flatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))//flux<List<String>>
	               // .concatMap((s) -> s.map(this::convertToList).subscribeOn(parallel()))//flux<List<String>>  //maintian the order
	                .flatMapSequential((s) -> s.map(this::convertToList).subscribeOn(parallel()))//flux<List<String>>  //maintian the order
		               
	                .flatMap(s -> Flux.fromIterable(s)) //Flux<String>
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNextCount(12)
	                .verifyComplete();
	    }

}
