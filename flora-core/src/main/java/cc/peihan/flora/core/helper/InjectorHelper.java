package cc.peihan.flora.core.helper;

import cc.peihan.flora.core.module.DatabaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public enum InjectorHelper {

    INSTANCE;

    private Injector injector;


    public void init() {
        //注入mybatis配置
        this.injector = Guice.createInjector(new DatabaseModule());
    }


    public Object getObject(Class clazz) {
        return injector.getInstance(clazz);
    }

}
