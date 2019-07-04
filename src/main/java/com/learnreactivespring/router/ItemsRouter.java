package com.learnreactivespring.router;

import static com.learnreactivespring.constant.ItemConstant.FUNCTIONAL_END_POINT_V1;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.learnreactivespring.handler.ItemHandler;

@Configuration
public class ItemsRouter {

	@Bean
	public RouterFunction<ServerResponse> route(ItemHandler itemhandler) {
		return RouterFunctions
				.route(GET(FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON)), itemhandler::getAllItem)
				.andRoute(GET(FUNCTIONAL_END_POINT_V1 + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
						itemhandler::getItemById)
				.andRoute(POST(FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON)),
						itemhandler::createItem)
				.andRoute(DELETE(FUNCTIONAL_END_POINT_V1 + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
						itemhandler::deleteItem);

	}

}
