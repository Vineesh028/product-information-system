package com.product.information.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.product.information.avro.model.Product;
import com.product.information.avro.model.ProductEvent;
import com.product.information.entity.OutboxProductEventEntity;
import com.product.information.entity.ProductEntity;
import com.product.information.exception.ResourceNotFoundException;
import com.product.information.model.EventTypes;
import com.product.information.model.ProductDto;
import com.product.information.model.ProductEventModel;
import com.product.information.repository.OutboxRepository;
import com.product.information.repository.ProductRepository;
import com.product.information.service.IProductInformationService;
import com.product.information.util.ProductMapper;

import jakarta.validation.Valid;

/**
 * Implementation for IProductInformationService
 */
@Service
public class ProductInformationService implements IProductInformationService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OutboxRepository outboxRepository;
	
	@Autowired
	SenderService senderService;
	

	@Override
	public List<ProductDto> getAllProducts() {
		List<ProductEntity> productEntities = productRepository.findAll();
		return productEntities.stream().map(e -> ProductMapper.mapToProductDto(e, new ProductDto())).toList();
	}

	@Override
	public ProductDto getProductById(Long id) {
		ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", String.valueOf(id)));
		return ProductMapper.mapToProductDto(productEntity, new ProductDto());
	}

	@Override
	public List<ProductDto> getProductsByName(String name) {
		List<ProductEntity> productEntities = productRepository.findByProductName(name);
		 return productEntities.stream().map(e -> ProductMapper.mapToProductDto(e, new ProductDto())).toList();
	}
	

	@Override
	public void deleteProductById(Long id) throws InterruptedException, ExecutionException {
		
	
		OutboxProductEventEntity outboxEvent = new OutboxProductEventEntity();
		outboxEvent.setEventType(EventTypes.DELETE.toString());
		outboxEvent.setMessage("Product deleted");
		outboxEvent.setProductId(id);
		
		ProductEvent event = new ProductEvent();
		
		event.setEventType(EventTypes.DELETE.toString());
		event.setMessage("Product deleted");
		ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", String.valueOf(id)));
		Product product = new Product();
		BeanUtils.copyProperties(productEntity, product);		
		event.setProduct(product);
		
		productRepository.deleteById(id);
		outboxRepository.save(outboxEvent);
			
		senderService.send(event,outboxEvent);
		
	}


	@Override
	public ProductDto updateProduct(Long id, @Valid ProductDto productDto) throws InterruptedException, ExecutionException {
		
		ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", String.valueOf(id)));
		
		ProductEntity updatedProduct = ProductMapper.mapToProductEntity(productDto, productEntity);
		
		ProductEntity savedProduct = productRepository.save(updatedProduct);
		
		OutboxProductEventEntity outboxEvent = new OutboxProductEventEntity();
		outboxEvent.setEventType(EventTypes.PUBLISH.toString());
		outboxEvent.setMessage("Changes");
		outboxEvent.setProductId(id);
		
		
		ProductEvent event = new ProductEvent();		
		event.setEventType(EventTypes.UPDATE.toString());
		event.setMessage("Product update");
		Product product = new Product();
		BeanUtils.copyProperties(savedProduct, product);
		event.setProduct(product);
		
		outboxRepository.save(outboxEvent);
		senderService.send(event,outboxEvent);
		
		return ProductMapper.mapToProductDto(savedProduct, new ProductDto());
		
	}

	@Override
	public void publishProductEvent(@Valid ProductEventModel productEventModel) throws InterruptedException, ExecutionException {
		
		OutboxProductEventEntity outboxEvent = new OutboxProductEventEntity();
		outboxEvent.setEventType(EventTypes.PUBLISH.toString());
		outboxEvent.setMessage("Changes");
		outboxEvent.setProductId(productEventModel.getProductId());
		
		ProductEvent event = new ProductEvent();		
		event.setEventType(productEventModel.getEventType());
		event.setMessage(productEventModel.getMessage());
		
		ProductEntity productEntity = productRepository.findById(productEventModel.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", String.valueOf(productEventModel.getProductId())));
		Product product = new Product();
		BeanUtils.copyProperties(productEntity, product);
		
		event.setProduct(product);
	
		outboxRepository.save(outboxEvent);
		senderService.send(event,outboxEvent);
		
	}
	
	/**
	 * Retrying to send messages in the outbox
	 */
	@Scheduled(fixedDelayString = "10800")
	public void retry() {
		
	
	
		if(!outboxRepository.findAll().isEmpty()) {
			outboxRepository.findAll().forEach(outboxEvent -> {
				
				ProductEvent event = new ProductEvent();		
				event.setEventType(outboxEvent.getEventType());
				event.setMessage(outboxEvent.getMessage());
				
				if(productRepository.findById(outboxEvent.getProductId()).isPresent()) {
					
					ProductEntity productEntity = productRepository.findById(outboxEvent.getProductId()).get();
					Product product = new Product();
					BeanUtils.copyProperties(productEntity, product);
					event.setProduct(product);
					
				}

				try {
					senderService.send(event,outboxEvent);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException("Retrying outbox event failed!");
				}
				
			});
			
		}
		
	}


}
