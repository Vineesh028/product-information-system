package com.user.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quantity")
public class QuantityEntity {
	
	@Id
	private long id;
	
	private int quantityTotal;
	
    private int   quantityAvailable;

}
