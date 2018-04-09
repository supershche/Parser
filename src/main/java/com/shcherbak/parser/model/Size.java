package com.shcherbak.parser.model;

import java.io.Serializable;

public class Size implements Serializable{
	
	
	private String pxSorting;
	private String shopSize;
	private String vendorSize;
	private String isPantsSize;
	private String variantId;
	private Boolean isDisabled;
	
	public Size() {}
	
	public String getPxSorting() {
		return pxSorting;
	}
	public void setPxSorting(String pxSorting) {
		this.pxSorting = pxSorting;
	}
	public String getShopSize() {
		return shopSize;
	}
	public void setShopSize(String shopSize) {
		this.shopSize = shopSize;
	}
	public String getVendorSize() {
		return vendorSize;
	}
	public void setVendorSize(String vendorSize) {
		this.vendorSize = vendorSize;
	}
	public String getIsPantsSize() {
		return isPantsSize;
	}
	public void setIsPantsSize(String isPantsSize) {
		this.isPantsSize = isPantsSize;
	}
	public String getVariantId() {
		return variantId;
	}
	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	

	
	
	
}
