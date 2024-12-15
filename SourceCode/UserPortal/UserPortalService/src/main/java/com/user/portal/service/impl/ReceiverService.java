package com.user.portal.service.impl;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.portal.model.ProductDto;
import com.user.portal.model.ProductEvent;
import com.user.portal.service.IProductInformationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Receiving  product changes from Product information services
 */
@Service
@Slf4j
@AllArgsConstructor
public class ReceiverService {

	private final IProductInformationService productInformationService;

	@KafkaListener(topics = "${topic.name.product}", groupId = "${spring.kafka.consumer.group-id}")
	public void read(ConsumerRecord<String, GenericRecord> consumerRecord) {
		String key = consumerRecord.key();

		ObjectMapper mapper = new ObjectMapper();

		try {
			String json = consumerRecord.value().toString();
			ProductEvent productEvent = mapper.readValue(json, ProductEvent.class);
			log.info("Message received with key : " + key + " value : " + productEvent);

			handleEvent(productEvent);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void handleEvent(ProductEvent event) {

		String eventType = event.getEventType();

		ProductDto product = event.getProduct();

		switch (eventType) {

		case "INSERT" -> {

			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);

			productInformationService.insertProduct(productDto);
		}
		case "UPDATE" -> {

			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);

			productInformationService.updateProduct(product.getProductId(), productDto);

		}

		case "DELETE" ->

			productInformationService.deleteProduct(product.getProductId());

		case "PUBLISH" -> productInformationService.publishChanges(event);

		default -> {
			break;
		}
		}

	}

}