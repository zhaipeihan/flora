package cc.peihan.flora.demo.api;


import cc.peihan.flora.core.annotation.FCPService;
import cc.peihan.flora.demo.model.Item;


@FCPService
public interface FloraTestService {

    String ping();

    String echo(String name);

    String echo2(String s1, String s2);

    Item getById(Integer id);

}
