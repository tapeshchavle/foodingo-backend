package com.food.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.food.io.OrderItem;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
@Document(collection = "orders")
public class OrderEntity {
	@Id
	private String id;
	private String userId;
	private String userAddress;
	private String phoneNumber;
	private String email;
	private List<OrderItem> orderedItems;
	private double amount;
	private String paymentStatus;
	private String razorpayOrderId;
	private String razorpaySignature;
	private String razorpayPaymentId;
	private String orderStatus;
	

}
