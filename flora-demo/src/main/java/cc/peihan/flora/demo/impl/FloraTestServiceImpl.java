package cc.peihan.flora.demo.impl;


import cc.peihan.flora.demo.api.FloraTestService;

public class FloraTestServiceImpl implements FloraTestService {

    @Override
    public String ping() {
        return null;
    }

    @Override
    public String echo(String name) {
        return name;
    }

    @Override
    public String echo2(String s1, String s2) {
        return s1 + s2;
    }
}