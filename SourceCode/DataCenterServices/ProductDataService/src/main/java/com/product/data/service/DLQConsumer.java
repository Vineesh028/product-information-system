package com.product.data.service;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.data.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Consumes failed messages and retries
 */
@Component
@Slf4j
public class DLQConsumer {
	
	@Value("${retry.count.limit}")
	private int retryLimit;

	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@KafkaListener(id = "dlq-topic-consumer", groupId = "dlq-topic-group", topics = "${app.dlq.topic}")
	public void consume(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) throws  JsonProcessingException, InterruptedException {
		String json = String.valueOf(consumerRecord.value());

		try {
			log.info("Consuming DLQ message {}", json);
			Header originalTopicHeader = consumerRecord.headers().lastHeader(Constants.ORIGINAL_TOPIC_HEADER_KEY);
			if (originalTopicHeader != null) {
				String originalTopic = new String(originalTopicHeader.value(), StandardCharsets.UTF_8);
				Header retryCountHeader = consumerRecord.headers().lastHeader(Constants.RETRY_COUNT_HEADER_KEY);
				int retryCount = 0;
				if (retryCountHeader != null) {
					retryCount = Integer.parseInt(new String(retryCountHeader.value(), StandardCharsets.UTF_8));
				}
				if (retryCount < retryLimit) {
					retryCount += 1;
					log.info("Resending attempt {}", retryCount);
					ProducerRecord<String, String> producerRecord = new ProducerRecord<>(originalTopic, json);
					byte[] retryCountHeaderInByte = Integer.toString(retryCount).getBytes(StandardCharsets.UTF_8);
					producerRecord.headers().add(Constants.RETRY_COUNT_HEADER_KEY, retryCountHeaderInByte);
					Thread.sleep(5000);
					kafkaTemplate.send(producerRecord);
				} else {
					log.error("Retry limit exceeded for message {}", json);
				}
			} else {
				log.error("Unable to resend DLQ message because it's missing the originalTopic header");
			}
		}
		catch (InterruptedException e) {
			
			throw new InterruptedException("Unable to process DLQ message ")
;			
		} 
		finally {
			ack.acknowledge();
		}
	}
}