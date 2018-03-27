package com.gooalgene.dna.service;

import com.gooalgene.utils.ConsequenceTypeUtils;
import org.junit.Test;

public class ConsequenceTypeUtilsTest {

    @Test
    public void testInterpretConsequenceType() {
        String type = "Exonic_stoploss";
        String result = ConsequenceTypeUtils.reverseFrontStyleToDB(type);
        System.out.println(result);

    }
}
