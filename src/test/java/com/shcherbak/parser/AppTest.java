package com.shcherbak.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.shcherbak.parser.model.Product;
import com.shcherbak.util.Constants;

public class AppTest

{

	@Before
	public void before() {
		JSoupConnector.initForTest();
	}

	@Test
	public void testDisabledSizes() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector("https://www.aboutyou.de/p/calvin-klein-jeans/t-shirt-1843895",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(1, JSoupConnector.getProducts().size());

		assertEquals(5, JSoupConnector.getProducts().get(0).getSizes().size());

	}

	@Test
	public void testPriceFormat() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector(
				"https://www.aboutyou.de/p/jack-und-jones/chino-jjimarco-jjenzo-o-night-ww-420-noos-3663642",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(1, JSoupConnector.getProducts().size());

		assertEquals("34.90", JSoupConnector.getProducts().get(0).getPrice());

	}

	@Test
	public void testParameters() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector("https://www.aboutyou.de/p/lacoste/poloshirt-3814579",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Product> list = JSoupConnector.getProducts();

		assertEquals(1, list.size());

		assertEquals("69.99", list.get(0).getPrice());
		assertEquals("LACOSTE", list.get(0).getBrand());
		assertEquals("3814579", list.get(0).getArticleId());
		assertEquals("blau", list.get(0).getColor());
		assertEquals("Poloshirt", list.get(0).getName());

	}

	@Test
	public void colorTest() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector("https://www.aboutyou.de/p/s-oliver-red-label/2er-pack-t-shirts-2244313",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Product> list = JSoupConnector.getProducts();

		assertEquals(1, list.size());

		assertEquals("schwarz", list.get(0).getColor());

	}

	@Test
	public void anotherColorTest() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector("https://www.aboutyou.de/p/quiksilver/badeshorts-everydvl-3812428",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Product> list = JSoupConnector.getProducts();

		assertEquals(1, list.size());

		assertEquals("schwarz", list.get(0).getColor());

	}

	@Test
	public void anotherColorTest38() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector("https://www.aboutyou.de/p/levi-s/t-shirt-housemark-graphic-tee-3799321",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Product> list = JSoupConnector.getProducts();

		assertEquals(1, list.size());

		assertEquals("schwarz", list.get(0).getColor());

	}

	@Test
	public void testDiscount() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new JSoupConnector("https://www.aboutyou.de/p/urban-classics/jacke-contrast-windrunner-3610001",
				Constants.HTTPS_WWW_ABOUTYOU_DE, true));

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Product> list = JSoupConnector.getProducts();

		assertEquals(1, list.size());

		assertEquals("27.90", list.get(0).getPrice());

	}

}
