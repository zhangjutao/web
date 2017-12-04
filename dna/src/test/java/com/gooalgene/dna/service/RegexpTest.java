package com.gooalgene.dna.service;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpTest extends TestCase {

    @Test
    public void testMatchString(){
        String target = "SRR2173900";
        Pattern regexp = Pattern.compile("[a-zA-Z]");
        Matcher matcher = regexp.matcher(target);
        assertTrue(matcher.find());
    }
}
