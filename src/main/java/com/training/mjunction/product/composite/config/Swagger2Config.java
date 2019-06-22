package com.training.mjunction.product.composite.config;

import static java.util.Arrays.asList;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Primary
@Component
@EnableSwagger2
@Configuration
public class Swagger2Config implements SwaggerResourcesProvider {

	@Resource
	private InMemorySwaggerResourcesProvider inMemorySwaggerResourcesProvider;

	@Override
	public List<SwaggerResource> get() {
		final SwaggerResource jerseySwaggerResource = new SwaggerResource();
		jerseySwaggerResource.setLocation("/api/swagger.json");
		jerseySwaggerResource.setSwaggerVersion("2.0");
		jerseySwaggerResource.setName("product-catalog");
		return Stream.concat(Stream.of(jerseySwaggerResource), inMemorySwaggerResourcesProvider.get().stream())
				.collect(Collectors.toList());
	}

	@Bean
	public Docket userApi() {
		return new Docket(SWAGGER_2).select().apis(basePackage("com.training.mjunction.product.composite.api"))
				.paths(PathSelectors.any()).build().enable(true).apiInfo(
						new ApiInfo("Mjunction Training API", "Spring Boot REST API for user", "1.0", "Traning Purpose",
								new Contact("Sanjib Talukdar", "https://expogrow.org", "expogrow.org@gmail.com"),
								"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
								asList(new VendorExtension<String>() {

									@Override
									public String getName() {
										return "ExpoGrow.org";
									}

									@Override
									public String getValue() {
										return "mjunction-traning";
									}

								})));
	}

}
