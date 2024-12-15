package com.product.information.service.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.product.information.avro.model.ProductEvent;
import com.product.information.entity.OutboxProductEventEntity;
import com.product.information.repository.OutboxRepository;

@Service
public class SenderService {

	@Value("${topic.name.product}")
	private String productTopicName;

	@Value("${topic.name.promotion}")
	private String promotionTopicName;

	@Autowired
	OutboxRepository outboxRepository;

	@Autowired
	private KafkaTemplate<String, ProductEvent> producTemplate;

//	@Autowired
//	private KafkaTemplate<String, PromotionEvent> promotionTemplate;

	public void send(ProductEvent event, OutboxProductEventEntity outboxEvent)
			throws InterruptedException, ExecutionException {

		CompletableFuture<SendResult<String, ProductEvent>> future = producTemplate.send(productTopicName,
				UUID.randomUUID().toString(), event);
		
		future.whenComplete((result,ex) -> {
			
			   if (ex == null){
				   outboxRepository.delete(outboxEvent);
			   }

			});


	}


}
