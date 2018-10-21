package cc.peihan.flora.core.util;

import cc.peihan.flora.core.demo.TestInterface;
import cc.peihan.flora.core.demo.TestInterfaceImpl;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ReflexUtilTest {

    @Test
    public void isImpl() {
        Class parent = TestInterface.class;
        Class child = TestInterfaceImpl.class;
        HashSet<Class<?>> hashSet = new HashSet<>();
        hashSet.add(child);
        Set<Class> classes = ReflexUtil.findImplementationClass(parent, hashSet);
        System.out.println(classes);
    }

    @Test
    public void testNewInstance() throws IllegalAccessException, InstantiationException {
        Class interfacs = TestInterface.class;
        Object obj = interfacs.newInstance();
        System.out.println(obj);

    }
}