package com.training.mjunction.product.composite.domain;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "category", "features" })
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private String id = UUID.randomUUID().toString();

	@JsonProperty("name")
	private String name;

	@JsonProperty("category")
	private String category;

	@JsonProperty("features")
	private Features features;
}
