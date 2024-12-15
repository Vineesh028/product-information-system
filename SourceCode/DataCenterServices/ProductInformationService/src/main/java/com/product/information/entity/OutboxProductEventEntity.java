package com.product.information.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "outbox_product_event")
public class OutboxProductEventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eventId;
	
	private String eventType;
	
	private String message;
	
	private long productId;
		

}
