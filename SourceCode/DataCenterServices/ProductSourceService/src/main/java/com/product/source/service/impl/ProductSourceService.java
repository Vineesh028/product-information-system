package com.product.source.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.product.source.service.IProductSourceService;


/**
 * Implementation of IProductSourceService
 * 
 */
@Service
public class ProductSourceService implements IProductSourceService {

	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${spring.data.mongodb.database}")
	private String dbName;

	@Value("${mongo.collection.name}")
	private String collectionName;

	@Value("${mongo.outbox.collection.name}")
	private String outboxCollectionName;

	@Value("${app.message.topic}")
	private String messageTopic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Reads the json string , converts it into Bson documents and stores in mongo
	 * 
	 * @param products
	 * @return void
	 */
	@Override
	public void addProducts(String products) throws JSONException {

		Object json = new JSONTokener(products).nextValue();
		if (json instanceof JSONObject) {
			JSONObject data = new JSONObject(products);
			Document doc = Document.parse(data.toString());
			mongoTemplate.insert(doc, collectionName);
			mongoTemplate.insert(doc, outboxCollectionName);

			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(messageTopic, products);
			kafkaTemplate.send(producerRecord);

			mongoTemplate.remove(doc, outboxCollectionName);
		}

		else if (json instanceof JSONArray) {
			JSONArray dataArray = new JSONArray(products);
			List<Document> docList = new ArrayList<Document>();
			for (int i = 0; i < dataArray.length(); i++) {

				JSONObject obj = dataArray.getJSONObject(i);
				docList.add(Document.parse(obj.toString()));
			}

			mongoTemplate.insert(docList, collectionName);
			mongoTemplate.insert(docList, outboxCollectionName);
			

			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(messageTopic, products);
			kafkaTemplate.send(producerRecord);
			
			docList.forEach(e -> mongoTemplate.remove(e, outboxCollectionName));

		}

	}

	/**
	 * Retrying to send messages in the outbox
	 */
	@Scheduled(fixedDelayString = "10800")
	public void retry() {
		
	MongoCollection<Document> collection = mongoTemplate.getCollection(outboxCollectionName);	
	
		if(collection.countDocuments() > 0) {
			collection.find().forEach(doc -> {
				
				ProducerRecord<String, String> producerRecord = new ProducerRecord<>(messageTopic, doc.toString());
				kafkaTemplate.send(producerRecord);
				mongoTemplate.remove(doc, outboxCollectionName);
				
			});
			
			
		}
		
	}

}
