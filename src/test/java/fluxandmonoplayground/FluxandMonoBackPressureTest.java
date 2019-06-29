package fluxandmonoplayground;

import org.junit.Test;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxandMonoBackPressureTest {

	@Test
	public void backPresuureTest() {
		Flux longFlux = Flux.range(1, 10).log();

		StepVerifier.create(longFlux).expectSubscription().thenRequest(1).expectNext(1).thenRequest(2).expectNext(2, 3)
				.thenCancel().verify();

	}

	@Test
	public void backResssureTest() {
		Flux<Integer> finiteData = Flux.range(1, 10).log();

		finiteData.subscribe((element) -> System.out.println("Element is :" + element),
				e -> System.out.println("Error is :" + e), () -> System.out.println("Done"),
				subscription -> subscription.request(4));

	}

	@Test
	public void backResssureCustomisedTest() {
		Flux<Integer> finiteData = Flux.range(1, 10).log();

		finiteData.subscribe(new BaseSubscriber<Integer>() {
			@Override
			protected void hookOnNext(Integer value) {
				request(2);
				System.out.println("Value reciecved is " + value);
				if (value == 9) {
					cancel();
				}

			};

		}

		);

	}

}
