package com.shcherbak.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shcherbak.parser.model.Product;
import com.shcherbak.parser.model.Size;
import com.shcherbak.parser.model.TrackingData;
import com.shcherbak.util.Constants;

public class JSoupConnector implements Runnable {

	private static int requestCount = 1;
	private static ReentrantLock counterLock = new ReentrantLock(true);
	private String urlName;
	private String refererUrl;
	private Boolean isToGatherVariants;
	private static List<Product> products = Collections.synchronizedList(new ArrayList<Product>());
	private static List<String> variantUrls = Collections.synchronizedList(new ArrayList<String>());
	private static final ObjectMapper mapper = new ObjectMapper();

	private static void incrementCounter(String urlName) {
		counterLock.lock();

		try {
			// System.out.println(urlName);
			requestCount++;
		} finally {
			counterLock.unlock();
		}
	}

	public JSoupConnector(String urlName, String referenceUrl, boolean isToGatherVariants) {
		this.urlName = urlName;
		this.refererUrl = referenceUrl;
		this.isToGatherVariants = isToGatherVariants;
	}

	public static void initForTest() {
		requestCount = 1;
		products = Collections.synchronizedList(new ArrayList<Product>());
		variantUrls = Collections.synchronizedList(new ArrayList<String>());
	}

	@Override
	public void run() {

		try {

			Document productDocument = Jsoup.connect(urlName).referrer(refererUrl).get();

			// count url requests. first request already included.
			incrementCounter(urlName);

			Product product = new Product();
			// Get data from JS json
			List<Element> elementList = new ArrayList<>();
			for (int i = 41; i > 36; i--) {
				elementList.add(productDocument.getElementsByTag("script").select("[data-reactid=" + i).first());
			}

			for (Element element : elementList) {
				if (element != null) {
					String html = element.data();

					if (html.indexOf("{") > 0) {
						JsonNode productNode = getProductNodeFromJS(html);
						getParametersFromJS(productNode, product);

						// getting other color variants urls only for first call
						addColorVariants(productNode);
						break;
					}
				}
			}
			// get data from html
			getProductDataFromHtml(productDocument, product);

			product.setUrl(urlName);
			products.add(product);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addColorVariants(JsonNode productNode) {

		if (isToGatherVariants) {
			JsonNode stylesNode = productNode.path("styles");
			for (final JsonNode node : stylesNode) {
				String url = node.get("url").asText();
				if (!urlName.equals(Constants.HTTPS_WWW_ABOUTYOU_DE + url)
						&& !variantUrls.contains(Constants.HTTPS_WWW_ABOUTYOU_DE + url)) {
					variantUrls.add(Constants.HTTPS_WWW_ABOUTYOU_DE + url);
				}
			}
		}
	}

	private JsonNode getProductNodeFromJS(String html) throws IOException {
		String json = html.substring(html.indexOf("{"));

		// convert String to JSON node
		JsonNode actualObj = mapper.readTree(json);

		JsonNode productDataNode = actualObj.path("adpPage");

		JsonNode productNode = productDataNode.path("product");
		return productNode;
	}

	private void getProductDataFromHtml(Document productDocument, Product product) {

		String brand = productDocument.getElementsByClass("brand_1h3c7xk").first().getElementsByTag("img").first()
				.attr("alt");

		String name = productDocument.getElementsByClass("name_1jqcvyg").first().html();

		String articleId = urlName.substring(urlName.lastIndexOf("-") + 1);

		product.setName(name);
		product.setBrand(brand);
		product.setArticleId(articleId);
		
		// in case JS json haven't contained data
		if (product.getPrice() == null) {
			String price;

			if (productDocument.getElementsByClass("finalPrice_klth9m-o_O-highlight_1t1mqn4").first() != null) {

				price = productDocument.getElementsByClass("finalPrice_klth9m-o_O-highlight_1t1mqn4").first().html();

			} else if (productDocument.getElementsByClass("finalPrice_klth9m").first().childNodeSize() > 1) {
				price = productDocument.getElementsByClass("finalPrice_klth9m").first().getElementsByTag("span").last()
						.html();
			} else {
				price = productDocument.getElementsByClass("finalPrice_klth9m").first().html();
			}
			product.setPrice(price);
		}
		if (product.getColor() == null || product.getColor().equals("")) {
			String color = productDocument.getElementsByClass("rc-tooltip-inner").html();
			product.setColor(color);
		}
		if (product.getSizes().size() == 0) {
			List<String> sizes = new ArrayList<>();
			Elements sizeElements = productDocument.getElementsByClass("js-size-dropdown-wrapper wrapper_e296pg")
					.select("span[class=\"\"]"). // get rid of disabled sizes
					select("span.paddingLeft_1c9k9z2");

			for (Element element : sizeElements) {

				sizes.add(element.text());
			}
			product.setSizes(sizes);
		}
		
		

	}

	private void getParametersFromJS(JsonNode productNode, Product product)
			throws IOException, JsonParseException, JsonMappingException {
		//get color and price
		JsonNode trackingData = productNode.path("trackingData");
		TrackingData data = mapper.readValue(trackingData.toString(), TrackingData.class);
		product.setColor(data.getColor());
		product.setPrice(data.getPrice_gross());
		
		//get sizes
		JsonNode sizesNode = productNode.path("sizes");
		List<String> sizesList = new ArrayList<>();
		for (JsonNode node : sizesNode) {
			Size size = mapper.readValue(node.toString(), Size.class);
			if (!size.getIsDisabled()) {
				sizesList.add(size.getShopSize());
			}
		}
		product.setSizes(sizesList);

		//get color when it's null
		if (product.getColor() == null || product.getColor().equals("")) {
			JsonNode dataNode = productNode.path("data");
			String color = dataNode.get("detailColors").traverse().getValueAsString();
			product.setColor(color);
		}

	}

	public static int getRequestCount() {
		return requestCount;
	}

	public static List<Product> getProducts() {
		return products;
	}

	public static List<String> getVariantUrls() {
		return variantUrls;
	}

}
