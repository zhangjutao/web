package com.gooalgene.iqgs.others;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OptionalTest extends TestCase {
    @Test
    public void testAbsent(){
        String a = null;
        String result = Optional.<String>fromNullable(a).or("");
        assertEquals("", result);
        a = "Crabime";
        Optional<String> option = Optional.<String>fromNullable(a);
        assertEquals("Crabime", option.get());
    }

    @Test
    public void testReplaceNullableObject(){
        Mrna m = null;
        Mrna bean = Optional.<Mrna>fromNullable(m).or(new Mrna("Lucy"));
        assertEquals("Lucy", bean.getName());
        Mrna mrna = new Mrna("张三");
        Mrna completeBean = Optional.<Mrna>fromNullable(mrna).or(new Mrna("Lucy"));
        assertEquals("张三", completeBean.getName());
    }

    @Test
    public void testGuavaTransform(){
        List<Mrna> list = new ArrayList<>();
        Mrna mrna = new Mrna("hello");
        list.add(mrna);
        Mrna mrna1 = new Mrna("wo");
        list.add(mrna1);
        Collection<Integer> transformResult = Collections2.transform(list, new Function<Mrna, Integer>() {
            @Override
            public Integer apply(Mrna input) {
                return input.name.length();
            }
        });
        assertTrue(transformResult.containsAll(Arrays.asList(5, 2)));
    }

    private class Mrna{
        private String name;

        public Mrna(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
