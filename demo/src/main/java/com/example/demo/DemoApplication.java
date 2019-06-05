package com.example.demo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.*;
import java.io.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.bson.Document;

import com.example.demo.Feed;
import com.example.demo.FeedMessage;
import com.example.demo.RSSFeedParser;



@SpringBootApplication

public class DemoApplication {

	public static void main(String[] args) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		System.out.println("connection successfully");
		MongoDatabase db = mongo.getDatabase("article");
		System.out.println(db.getName());

		//List <String> dbname = mongo.getDatabaseNames();
		//System.out.println(dbname);.

		//db.createCollection("sample");
		MongoCollection<Document> collection = db.getCollection("rssfeed");
		System.out.println("Collection  selected successfully");


		RSSFeedParser parser = new RSSFeedParser(
				"https://timesofindia.indiatimes.com/rssfeedstopstories.cms");
		Feed feed = parser.readFeed();
		//System.out.println(feed);

		int count = 0;
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);

			Document document;
			document = new Document("id", count + 1)
					.append("title", message.getTitle())
					.append("pub date", message.getpubDate())
					.append("Description", message.getDescription())
					.append("link", message.getLink());

			collection.insertOne(document);
			System.out.println("Document inserted successfully");


			count++;
		}


		MongoDatabase database = mongo.getDatabase("article");
		MongoCollection<Document> collect = database.getCollection("rssfeed");

		List<Document> documents = (List<Document>) collect.find().into(new ArrayList<Document>());

		for (Document document : documents) {
			System.out.println(document);


		}
	}

}

