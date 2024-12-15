package com.product.information.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {

	@Value("${topic.name.product}")
	private String productTopicName;
	
	@Value("${topic.name.promotion}")
	private String promotionTopicName;

	@Bean
	public NewTopic createTopic() {
		return new NewTopic(productTopicName, 3, (short) 1);
	}

	@Bean
	public NewTopic createTopic2() {
		return new NewTopic(promotionTopicName, 3, (short) 1);
	}

}
