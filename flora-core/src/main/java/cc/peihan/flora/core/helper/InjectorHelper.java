package cc.peihan.flora.core.helper;

import cc.peihan.flora.core.module.DatabaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Set;

public enum InjectorHelper {

    INSTANCE;

    private Injector injector;


    public void init(Set<Class<?>> mybatisMapperClasses) {
        //注入mybatis配置
        this.injector = Guice.createInjector(new DatabaseModule(mybatisMapperClasses));
    }


    public Object getObject(Class clazz) {
        return injector.getInstance(clazz);
    }

}
