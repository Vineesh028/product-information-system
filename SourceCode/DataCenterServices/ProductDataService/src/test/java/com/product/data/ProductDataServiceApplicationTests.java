package com.product.data;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.Optional;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.product.data.config.KafkaConfig;
import com.product.data.entity.ProductEntity;
import com.product.data.repository.ProductRepository;

@SpringBootTest
@TestPropertySource(properties = { "spring.kafka.consumer.auto-offset-reset=earliest" })
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { ProductDataServiceApplication.class,
		KafkaConfig.class })
@Testcontainers
class ProductDataServiceApplicationTests {

	@Container
	static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
		registry.add("app.message.topic", () -> "producttopic");
		registry.add("app.dlq.topic", () -> "dlqtopic");
		registry.add("retry.count.limit", () -> 3);
		registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
		registry.add("spring.kafka.listener.ack-mode", () -> "MANUAL_IMMEDIATE");

	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ProductRepository productRepository;



	@Test
	void shouldHandleProductPriceChangedEvent() {

		String product = "{\"id\":1,\"name\":\"iPhone9\",\"description\":\"Anapplemobilewhichisnothinglikeapple\",\"price\":549,\"discountPercentage\":12.96,\"rating\":4.69,\"stock\":94,\"brand\":\"Apple\",\"category\":\"smartphones\",\"thumbnail\":\"https://i.dummyjson.com/data/products/1/thumbnail.jpg\",\"images\":[\"https://i.dummyjson.com/data/products/1/1.jpg\"]}";

		ProducerRecord<String, String> producerRecord = new ProducerRecord<>("producttopic", product);
		kafkaTemplate.send(producerRecord);

		await().pollInterval(Duration.ofSeconds(3)).atMost(10, SECONDS).untilAsserted(() -> {
			Optional<ProductEntity> optionalProduct = productRepository.findByProductName("iPhone9");

			assertThat(optionalProduct).isPresent();
			assertThat(optionalProduct.get().getPrice()).isEqualTo(549);
		});
	}
}
