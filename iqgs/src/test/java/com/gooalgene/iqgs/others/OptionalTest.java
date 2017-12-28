package com.gooalgene.iqgs.others;

import com.google.common.base.Optional;
import junit.framework.TestCase;
import org.junit.Test;

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
