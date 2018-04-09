package com.shcherbak.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.shcherbak.util.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shcherbak.parser.model.Product;

public class App {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws InterruptedException {

		String urlName = Constants.HTTPS_WWW_ABOUTYOU_DE_MAENNER_BEKLEIDUNG;

		Set<String> links = new HashSet<>();
		Set<String> colorVariantLinks = new HashSet<>();

		ExecutorService executor = Executors.newFixedThreadPool(10);
		System.out.println("Starting data extraction...\n");
		try {
			Document document;

			// get all product urls from page
			document = Jsoup.connect(urlName).referrer(Constants.HTTP_WWW_GOOGLE_COM).get();
			Elements elements = document.getElementsByClass("anchor_wgmchy");
			for (Element element : elements) {
				String link = element.attr("abs:href");
				links.add(link);

			}

			// get data from products and gather urls for their color variations
			for (String link : links) {
				JSoupConnector productConnector = new JSoupConnector(link, urlName, true);
				executor.execute(productConnector);
			}
			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executor = Executors.newFixedThreadPool(10);

			//get unique new url for color variations of products
			for (String link : JSoupConnector.getVariantUrls()) {
				if (!links.contains(link)) {
					colorVariantLinks.add(link);
				}
			}

			// TODO after all products extracted perform extraction for their color
			// variants. Better use something else without executor shutdown.
			

			for (String link : colorVariantLinks) {
				JSoupConnector productConnector = new JSoupConnector(link, urlName, false);
				executor.execute(productConnector);
			}

			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// save data to file
			List<Product> list = JSoupConnector.getProducts();
		

			mapper.writeValue(new File("products.json"), list);
			
			System.out.println("Saved data to file: products.json");
			System.out.println("Amount of products extracted: " + list.size());
			System.out.println("Amount of HTTP requests performed: " + JSoupConnector.getRequestCount());

		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}
}
