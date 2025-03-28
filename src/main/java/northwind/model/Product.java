package northwind.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class Product implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@JsonProperty("ProductID")
	private String ProductID;
	@JsonProperty("ProductName")
	private String ProductName;
	@JsonProperty("CategoryID")
	private String CategoryID;
	@JsonProperty("QuantityPerUnit")
	private String QuantityPerUnit;
	@JsonProperty("UnitPrice")
	private String UnitPrice;

	public Product(String productID, String productName, String categoryID, String quantityPerUnit, String unitPrice) {
		super();
		this.ProductID = productID;
		this.ProductName = productName;
		this.CategoryID = categoryID;
		this.QuantityPerUnit = quantityPerUnit;
		this.UnitPrice = unitPrice;
	}

	
	
	public Product() {
		super();
	}



	public String getProductID() {
		return ProductID;
	}

	public String getProductName() {
		return ProductName;
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public String getQuantityPerUnit() {
		return QuantityPerUnit;
	}

	public String getUnitPrice() {
		return UnitPrice;
	}
	
	public double getUnitPriceAsDouble() {
		return Double.parseDouble(UnitPrice);
	}

	@Override
	public String toString() {
		return "Product{" +
				"ProductID='" + ProductID + '\'' +
				", ProductName='" + ProductName + '\'' +
				'}';
	}
}
