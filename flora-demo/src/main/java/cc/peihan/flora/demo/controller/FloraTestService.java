package cc.peihan.flora.demo.controller;


import cc.peihan.flora.core.annotation.FCPService;


@FCPService
public class FloraTestService {

    public String ping() {
        return "pong";
    }

    public String echo(String name) {
        return name;
    }

    public String echo2(String s1, String s2) {
        return s1 + s2;
    }

}
