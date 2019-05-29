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

    public static void main(String[] args)
	{
        RSSFeedParser parser = new RSSFeedParser(
                "https://www.vogella.com/article.rss");
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages())
        {
            System.out.println(message);

        }

		MongoClient mongo = new MongoClient("localhost", 27017);
		System.out.println("connection successfully");
		MongoDatabase db=mongo.getDatabase("article");
		System.out.println(db.getName());

		//List <String> dbname = mongo.getDatabaseNames();
		//System.out.println(dbname);

		//db.createCollection("sample");
		MongoCollection<Document> collection = db.getCollection("rssfeed");
		System.out.println("Collection  selected successfully");


		Document document = new Document("id",1)
				.append("title",feed.getTitle())
				.append("pub date", feed.getPubDate())
				.append("Description", feed.getDescription())
				.append("link", feed.getLink());

		collection.insertOne(document);
		System.out.println("Document inserted successfully");




	}
}

