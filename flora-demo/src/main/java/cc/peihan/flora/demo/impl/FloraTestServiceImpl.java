package cc.peihan.flora.demo.impl;


import cc.peihan.flora.demo.action.FloraAction;
import cc.peihan.flora.demo.api.FloraTestService;
import cc.peihan.flora.demo.model.Item;

import javax.inject.Inject;


public class FloraTestServiceImpl implements FloraTestService {


    @Inject
    private FloraAction floraAction;


    @Override
    public String ping() {
        return floraAction.sayHello();
    }

    @Override
    public String echo(String name) {
        return name;
    }

    @Override
    public String echo2(String s1, String s2) {
        return s1 + s2;
    }

    @Override
    public Item getById(Integer id) {
        return floraAction.getItemById(id.longValue());
    }
}
