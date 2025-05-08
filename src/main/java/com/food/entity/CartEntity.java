package com.food.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "carts")
public class CartEntity {
	@Id
	private String id;
	private String userId;
	private Map<String, Integer> items=new HashMap<>();
	public CartEntity(String userId,Map<String, Integer>items) {
		this.userId=userId;
		this.items=items;
	}
}
