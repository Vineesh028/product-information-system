package com.product.information.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;

public class AvroGenerator {

	public static void main(String[] args) throws IOException {
		AvroMapper avroMapper = AvroMapper.builder()
				.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
				.addModule(new AvroJavaTimeModule())
				.build();
		createAvroSchemaFromClass(Object.class, avroMapper);
	}
	
	private static void createAvroSchemaFromClass(Class<?> clazz, AvroMapper avroMapper) throws IOException {
		 
	    AvroSchemaGenerator gen = new AvroSchemaGenerator();
	    gen.enableLogicalTypes();
	    avroMapper.acceptJsonFormatVisitor(clazz, gen);
	    AvroSchema schemaWrapper = gen.getGeneratedSchema();
	 
	    org.apache.avro.Schema avroSchema = schemaWrapper.getAvroSchema();
	    String avroSchemaInJSON = avroSchema.toString(true);
	 
	    //Write to File
	    Path fileName = Path.of(clazz.getSimpleName() + ".avsc");
	    Files.writeString(fileName, avroSchemaInJSON);
	 
	}

}
