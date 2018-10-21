package cc.peihan.flora.core.helper;

import com.google.inject.Guice;
import com.google.inject.Injector;

public enum InjectorHelper {

    INSTANCE;

    private Injector injector;

    InjectorHelper() {
        this.injector = Guice.createInjector();
    }

    public Object getObject(Class clazz) {
        return injector.getInstance(clazz);
    }

}
