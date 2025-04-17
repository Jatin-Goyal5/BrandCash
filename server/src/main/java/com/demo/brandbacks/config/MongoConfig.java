package com.demo.brandbacks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {
	
	@Value("${mongo.database.name}")
	private String databaseName;
	@Value("${mongo.database.uri}")
	private String connectionUri;
	
	@Override
	protected String getDatabaseName() {
		return databaseName;
	}
	private MongoProperties properties;
	public static String keyStoreType = "";
	public static String defaultKeyPassword = "";

	public MongoConfig(final MongoProperties properties) {
		super();
		this.properties = properties;
	}
	
	 @Override
	 public MongoClient mongoClient() {
		 return MongoClients.create(connectionUri);
	 }

}