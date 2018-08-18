package cc.peihan.flora.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonUtilTest {


    @Test
    public void read() {
        String res = "[{\"foo\":\"Hello\"},{\"foo\":\"Hello\"},{\"foo\":\"Hello\"}]";
        List<Foo<String>> result = JsonUtil.read(res, new TypeReference<List<Foo<String>>>() {
        });
        System.out.println(result);
    }

    @Test
    public void read1() {
    }

    @Test
    public void writeAsString() {
        Foo<String> foo = new Foo<String>();
        foo.setFoo("Hello");
        List<Foo<String>> list = new ArrayList<Foo<String>>() {{
            add(foo);
            add(foo);
            add(foo);
        }};
        String result = JsonUtil.writeAsString(list);
        System.out.println(result);
        assert result != null;
    }
}