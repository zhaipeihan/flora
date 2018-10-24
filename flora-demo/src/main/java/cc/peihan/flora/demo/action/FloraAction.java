package cc.peihan.flora.demo.action;

import cc.peihan.flora.demo.dao.ItemDao;
import cc.peihan.flora.demo.model.Item;
import com.google.inject.Inject;

public class FloraAction {

    @Inject
    private ItemDao itemDao;

    public String sayHello() {
        return "Hello World!";
    }

    public Item getItemById(Long id) {
        return itemDao.getById(id);
    }

}
