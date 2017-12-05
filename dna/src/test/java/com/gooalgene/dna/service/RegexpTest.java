package com.gooalgene.dna.service;

import junit.framework.TestCase;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
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

    @Test
    public void testBigDecimalKeepTwoPoint(){
        BigDecimal result = new BigDecimal("3.53456").round(new MathContext(4, RoundingMode.HALF_UP));
        assertEquals(3.535, result.doubleValue());
        double major = 0.9984;
        assertNotSame(99.84, major*100);
        BigDecimal decimal = new BigDecimal(major);
        BigDecimal majorForBigDecimal = decimal.multiply(new BigDecimal(100));
        StringBuffer convertValue = new StringBuffer();
        StringBuffer finalResult = new DecimalFormat("###0.00").format(majorForBigDecimal, convertValue, new FieldPosition(NumberFormat.INTEGER_FIELD));
        System.out.println(finalResult.toString());
    }

    @Test
    public void testFieldPosition(){
        NumberFormat numForm = NumberFormat.getInstance();
        StringBuffer dest1 = new StringBuffer();
        FieldPosition pos = new FieldPosition(NumberFormat.INTEGER_FIELD);
        BigDecimal bd1 = new BigDecimal(22.3423D);
        dest1 = numForm.format(bd1, dest1, pos);
        System.out.println("dest1 = " + dest1);
        System.out.println("INTEGER portion is at: " + pos.getBeginIndex() +
                ", " + pos.getEndIndex());
        pos = new FieldPosition(NumberFormat.FRACTION_FIELD);
        dest1 = numForm.format(bd1, dest1, pos);
        System.out.println("FRACTION portion is at: " + pos.getBeginIndex() +
                ", " + pos.getEndIndex());
    }
}
