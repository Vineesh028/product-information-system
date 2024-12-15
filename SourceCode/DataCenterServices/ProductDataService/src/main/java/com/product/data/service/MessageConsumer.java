package com.product.data.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.data.entity.ProductEntity;
import com.product.data.repository.ProductRepository;
import com.product.data.util.Constants;
import com.product.data.util.ProductMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the messages from product source service
 */
@Component
@Slf4j
@Transactional
public class MessageConsumer {

	@Value("${app.dlq.topic}")
	private String dlqTopic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	ProductRepository productRepository;

	@KafkaListener(id = "ProductListener", topics = "${app.message.topic}")
	public void consume(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment)
			throws IllegalStateException, UnsupportedEncodingException, JsonProcessingException {

		String json = String.valueOf(consumerRecord.value());
		log.info("Consuming message {}", consumerRecord);

		try {

			readAndSaveProductData(consumerRecord.value());

		} catch (Exception e) {
			log.info("Message consumption failed for message {}", json);
			String originalTopic = consumerRecord.topic();
			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(dlqTopic, json);
			producerRecord.headers().add(Constants.ORIGINAL_TOPIC_HEADER_KEY, originalTopic.getBytes(StandardCharsets.UTF_8));
			Header retryCount = consumerRecord.headers().lastHeader(Constants.RETRY_COUNT_HEADER_KEY);
			if (retryCount != null) {
				producerRecord.headers().add(retryCount);
			}
			kafkaTemplate.send(producerRecord);
		} finally {
			acknowledgment.acknowledge();
		}
	}

	private void readAndSaveProductData(String products) {

		Object json = new JSONTokener(products).nextValue();

		if (json instanceof JSONObject) {

			if (ProductMapper.mapToProduct(products).isPresent()) {

				ProductEntity product = ProductMapper.mapToProduct(products).get();

				productRepository.save(product);

			}

		}

		else if (json instanceof JSONArray) {

			List<ProductEntity> productsList = ProductMapper.mapToProductList(products);

			if (!productsList.isEmpty()) {
				productRepository.saveAll(productsList);
			}

		}

	}
}