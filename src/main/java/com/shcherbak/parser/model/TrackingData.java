package com.shcherbak.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "L_id", "sales_percent" })

public class TrackingData {

	private String id;
	private String variant;
	private String name;
	private String sku;
	private String brand;
	private String brandId;
	private String category;
	private String category_name;
	private String about_number;
	private String price;
	private String price_recalc;
	private String price_gross;
	private String currency;
	private String tax;
	private String size;
	private String color;
	private String position;
	private String app_id;
	private String type;
	private String sale;
	private String dimension23;
	private String sale_amount;
	private String sale_percent;
	private String availability;
	private String style_key;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getAbout_number() {
		return about_number;
	}

	public void setAbout_number(String about_number) {
		this.about_number = about_number;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice_recalc() {
		return price_recalc;
	}

	public void setPrice_recalc(String price_recalc) {
		this.price_recalc = price_recalc;
	}

	public String getPrice_gross() {
		return price_gross;
	}

	public void setPrice_gross(String price_gross) {
		this.price_gross = price_gross;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public String getDimension23() {
		return dimension23;
	}

	public void setDimension23(String dimension23) {
		this.dimension23 = dimension23;
	}

	public String getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(String sale_amount) {
		this.sale_amount = sale_amount;
	}

	public String getSale_percent() {
		return sale_percent;
	}

	public void setSale_percent(String sale_percent) {
		this.sale_percent = sale_percent;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getStyle_key() {
		return style_key;
	}

	public void setStyle_key(String style_key) {
		this.style_key = style_key;
	}

	
}
