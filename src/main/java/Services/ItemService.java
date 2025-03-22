package Services;

import java.util.List;

import models.Item;
import models.ItemDetails;

public interface ItemService {
	List<Item> loadItems(int userId);

	Item loaditem(int id) throws Exception;

	boolean addItem(Item item, int userId) throws Exception;

	boolean addDetailsItem(ItemDetails itemDetails) throws Exception;

	boolean deleteDetailsItem(int itemId) throws Exception;

	boolean deleteItem(int id) throws Exception;

	boolean updateItem(Item item) throws Exception;
}
