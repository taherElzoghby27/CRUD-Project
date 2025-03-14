package models;

public class ItemDetails {
	private int id;
	private String description;
	private String issueDate;
	private String expireDate;
	private int itemId;

	public ItemDetails(int id, String description, String issueDate, String expireDate, int itemId) {
		super();
		this.id = id;
		this.description = description;
		this.issueDate = issueDate;
		this.expireDate = expireDate;
		this.itemId = itemId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
