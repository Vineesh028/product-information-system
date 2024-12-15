package com.product.data.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import com.product.data.entity.Brand;
import com.product.data.entity.ProductEntity;
import com.product.data.entity.Quantity;
import com.product.data.entity.Size;

import lombok.experimental.UtilityClass;


/**
 * Maps json data to product entity or list of entities
 */
@UtilityClass
public class ProductMapper {


	public Optional<ProductEntity> mapToProduct(String json) {
		

		JSONObject product = new JSONObject(json);

	
		 ProductEntity productEntity = createProductEntity(product);
		
		
		return Optional.ofNullable(productEntity);
	}

	public List<ProductEntity> mapToProductList(String json) {

		JSONArray prodcuts = new JSONArray(json);

		List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();

		for (int i = 0; i < prodcuts.length(); i++) {

			JSONObject product = prodcuts.getJSONObject(i);

			if (product.keySet().containsAll(Constants.PROPERTIES_LIST)) {

				ProductEntity productEntity = createProductEntity(product);
				productEntityList.add(productEntity);

			}

		}
		return productEntityList;
	}

	private static ProductEntity createProductEntity(JSONObject product) {

		ProductEntity productEntity = new ProductEntity();
		product.keySet().stream().forEach(key -> {

			switch (key) {

			case "name", "title" -> 
				productEntity.setProductName(product.getString(key));
			
			case "description" -> 

				productEntity.setDescription(product.getString(key));

			case "brand" -> {

				Brand brand = new Brand();
				brand.setBrandName(product.getString(key));
				productEntity.setBrand(brand);

			}
			case "material" -> 

				productEntity.setMaterial(product.getString(key));

			case "stock", "quantity" -> {

				Quantity quantity = new Quantity();
				quantity.setQuantityTotal(product.getInt(key));
				productEntity.setQuantity(quantity);

			}
			case "size" -> {

				Size size = new Size();
				size.setSizeEU(product.getString(key));
				productEntity.setSize(size);

			}
			case "colour" -> 
				productEntity.setColour(product.getString(key));
			
			case "ageGroup" -> 
				productEntity.setAgeGroup(product.getString(key));
			
			case "category" -> 
				productEntity.setCategory(product.getString(key));

			
			case "images" -> 
				productEntity.setImageUrl(product.getString(key));
			
			case "price" -> 
				productEntity.setPrice(product.getInt(key));
			
			case "country" -> 
				productEntity.setCountry(product.getString(key));
			
			default -> {
				break;
			}
			}

		});

		return productEntity;
	}

}
