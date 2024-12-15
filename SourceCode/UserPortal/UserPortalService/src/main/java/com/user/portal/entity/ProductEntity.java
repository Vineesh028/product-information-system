package com.user.portal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "product")
public class ProductEntity {

	@Id
	private long productId;
	
	private String productName;
	
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "brand_id")
	private BrandEntity brand;
	
	private String material;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "size_id")
	private SizeEntity size;
	
	private String colour;
	
	private String ageGroup;
	
	private String category;
	
	private String imageUrl;
	
	private float price;
	
	private String country;
		
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "quantity_id")
	private QuantityEntity quantity;

}
