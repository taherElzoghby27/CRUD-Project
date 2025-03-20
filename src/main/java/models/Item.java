package models;

public class Item {
	private int id;
	private String name;
	private double price;
	private double totalPrice;
	private ItemDetails itemDetails;

	public Item(String name, double price, double totalPrice, ItemDetails itemDetails) {
		this.name = name;
		this.price = price;
		this.totalPrice = totalPrice;
		this.itemDetails = itemDetails;
	}
	

	public Item(int id, String name, double price, double totalPrice, ItemDetails itemDetails) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.totalPrice = totalPrice;
		this.itemDetails = itemDetails;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public ItemDetails getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(ItemDetails itemDetails) {
		this.itemDetails = itemDetails;
	}

}
