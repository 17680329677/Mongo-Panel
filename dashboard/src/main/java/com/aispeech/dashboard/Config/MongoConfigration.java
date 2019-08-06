package com.aispeech.dashboard.Config;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@Configuration
public class MongoConfigration {
    private static final String MONGO_CONNECTION_STRING = "KG_MONGODB_INTERNAL";
    private static final String DEFAULT_DATABASE_NAME = "kg";
    private static final Logger logger = LoggerFactory.getLogger(MongoConfigration.class);

    @Autowired
    private ApplicationContext application;

    @Autowired
    private Environment environment;

    @Bean
    public MongoClient createMongoClient() {
        String mongoConnectionString = this.environment.getProperty(MONGO_CONNECTION_STRING, "");
        return MongoClients.create(mongoConnectionString);
    }

    @Bean
    public MongoDatabase getMongoDatabase() {
        MongoClient client = this.application.getBean(MongoClient.class);
        String mongoConnectionString = this.environment.getProperty(MONGO_CONNECTION_STRING, "");
        ConnectionString connectionString = new ConnectionString(mongoConnectionString);
        String databaseName = connectionString.getDatabase() == null ? DEFAULT_DATABASE_NAME : connectionString.getDatabase();
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClients.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return client.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
    }
}
