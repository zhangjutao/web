package com.gooalgene.dna.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;

/**
 * decimal保留特定小数位工具类
 *
 * @author crabime
 */
public class DataFormatUtils {

    /**
     * 保留两位小数
     * @param origin 原始double数字
     * @return 保留两位小数的数字
     */
    public static double keepTwoFraction(double origin) {
        BigDecimal decimalMajor = new BigDecimal(origin);
        BigDecimal majorDecimalToPercent = decimalMajor.multiply(new BigDecimal(100));
        StringBuffer convertValue = new StringBuffer();
        StringBuffer resultData = new DecimalFormat("###0.00").format(
                majorDecimalToPercent, convertValue, new FieldPosition(NumberFormat.INTEGER_FIELD));
        return Double.parseDouble(resultData.toString());
    }
}
