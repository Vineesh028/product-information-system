package com.product.data.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {
	
	private Constants() {

	}
	public static final String RETRY_COUNT_HEADER_KEY = "retryCount";
	public static final String ORIGINAL_TOPIC_HEADER_KEY = "originalTopic";
	

	
	public static final Set<String> PROPERTIES_LIST = new HashSet<>(Arrays.asList("brand", "price"));
	public static final String NAME = "name";
	public static final String TITLE = "title";
	

}
