package com.food.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.entity.CartEntity;
import com.food.io.CartRequest;
import com.food.io.CartResponse;
import com.food.io.FoodRequest;
import com.food.repository.CartRepository;
import com.food.service.CartService;
import com.food.service.UserService;
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public CartResponse addToCart(String foodId) {
	  String loggedInUserId=userService.findByUserId();	
	  Optional<CartEntity> cartOptional=cartRepository.findByUserId(loggedInUserId);
	  CartEntity cartEntity= cartOptional.orElseGet(()->new CartEntity(loggedInUserId,new HashMap<>()));
	  Map<String, Integer> cartItems=cartEntity.getItems();
	  cartItems.put(foodId,cartItems.getOrDefault(foodId, 0)+1);
	  cartEntity.setItems(cartItems);
	  cartEntity= cartRepository.save(cartEntity);
	  return convertToCartResponse(cartEntity);
		
	}
	private CartResponse convertToCartResponse(CartEntity entity) {
		return CartResponse.builder()
				.id(entity.getId())
				.items(entity.getItems())
				.userId(entity.getUserId())
				.build();
	}
	@Override
	public CartResponse getCart() {
		String loggedInUserId=userService.findByUserId();
	   CartEntity entity=cartRepository.findByUserId(loggedInUserId).orElse(new CartEntity(null,loggedInUserId,new HashMap<>()));
	   return convertToCartResponse(entity);
	}
	@Override
	public void clearCart() {
		String loggedInUserId=userService.findByUserId();
		cartRepository.deleteByUserId(loggedInUserId);
	}
	@Override 
	public CartResponse removeFromCart(CartRequest cartRequest) {
		String loggedInUserId=userService.findByUserId();
		
		CartEntity entity= cartRepository.findByUserId(loggedInUserId)
		.orElseThrow(()->new RuntimeException("Cart is not found"));
		
		Map<String, Integer> cartItems=entity.getItems();
		if(cartItems.containsKey(cartRequest.getFoodId())) {
			int currentQty=cartItems.get(cartRequest.getFoodId());
			if(currentQty>1) {
				cartItems.put(cartRequest.getFoodId(), currentQty-1);
			}else {
				cartItems.remove(cartRequest.getFoodId());
			}
		}
		entity=cartRepository.save(entity);
		return convertToCartResponse(entity);
		
	}
	@Override
	public String deleteItemFromCart(CartRequest cartRequest) {
        String loggedInUserId=userService.findByUserId();		
		CartEntity entity= cartRepository.findByUserId(loggedInUserId)
		.orElseThrow(()->new RuntimeException("Cart is not found"));
		
		Map<String, Integer> cartItems=entity.getItems();
		if(cartItems.containsKey(cartRequest.getFoodId())) {			
				cartItems.remove(cartRequest.getFoodId());
		}
		entity=cartRepository.save(entity);	
		return "Cart is Deleted";
	}
	
	
}
