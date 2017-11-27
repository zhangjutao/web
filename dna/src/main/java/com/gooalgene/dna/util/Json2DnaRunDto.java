package com.gooalgene.dna.util;

import com.gooalgene.dna.dto.CompareHelper;
import com.gooalgene.dna.dto.DnaRunDto;
import net.sf.json.JSONObject;

/**
 * Created by liuyan on 2017/11/20.
 */
public class Json2DnaRunDto {

    public static DnaRunDto json2DnaRunDto(JSONObject object){
        DnaRunDto dnaRunDto=new DnaRunDto();
        dnaRunDto.setCultivar(object.getString("cultivar"));
        dnaRunDto.setSpecies(object.getString("species"));
        dnaRunDto.setLocality(object.getString("locality"));
        dnaRunDto.setSampleName(object.getString("sampleName"));
        dnaRunDto.setGroup(object.getString("population"));
        CompareHelper c1=new CompareHelper();
        c1.setOperation(object.getString("weightPer100seeds.operation"));
        c1.setValue(Float.parseFloat(object.getString("weightPer100seeds.value").equals("")?"0":object.getString("weightPer100seeds.value")));
        dnaRunDto.setWeightPer100seeds(c1);

        CompareHelper c2=new CompareHelper();
        c2.setOperation(object.getString("protein.operation"));
        c2.setValue(Float.parseFloat(object.getString("protein.value").equals("")?"0":object.getString("protein.value")));
        dnaRunDto.setProtein(c2);

        CompareHelper c3=new CompareHelper();
        c3.setOperation(object.getString("oil.operation"));
        c3.setValue(Float.parseFloat(object.getString("oil.value").equals("")?"0":object.getString("oil.value")));
        dnaRunDto.setOil(c3);

        dnaRunDto.setMaturityDate(object.getString("maturityDate"));


        CompareHelper c4=new CompareHelper();
        c4.setOperation(object.getString("height.operation"));
        c4.setValue(Float.parseFloat(object.getString("height.value").equals("")?"0":object.getString("height.value")));
        dnaRunDto.setHeight(c4);

        dnaRunDto.setSeedCoatColor(object.getString("seedCoatColor"));
        dnaRunDto.setHilumColor(object.getString("hilumColor"));
        dnaRunDto.setCotyledonColor(object.getString("cotyledonColor"));
        dnaRunDto.setFlowerColor(object.getString("flowerColor"));
        dnaRunDto.setPodColor(object.getString("podColor"));
        dnaRunDto.setPubescenceColor(object.getString("pubescenceColor"));

        CompareHelper c5=new CompareHelper();
        c5.setOperation(object.getString("yield.operation"));
        c5.setValue(Float.parseFloat(object.getString("yield.value").equals("")?"0":object.getString("yield.Value")));
        dnaRunDto.setYield(c5);

        CompareHelper c6=new CompareHelper();
        c6.setOperation(object.getString("upperLeafletLength.operation"));
        c6.setValue(Float.parseFloat(object.getString("upperLeafletLength.value").equals("")?"0":object.getString("upperLeafletLength.value")));
        dnaRunDto.setUpperLeafletLength(c6);

        CompareHelper c7=new CompareHelper();
        c7.setOperation(object.getString("linoleic.operation"));
        c7.setValue(Float.parseFloat(object.getString("linoleic.value").equals("")?"0":object.getString("linoleic.value")));
        dnaRunDto.setLinoleic(c7);

        CompareHelper c8=new CompareHelper();
        c8.setOperation(object.getString("linolenic.operation"));
        c8.setValue(Float.parseFloat(object.getString("linolenic.value").equals("")?"0":object.getString("linolenic.value")));
        dnaRunDto.setLinolenic(c8);

        CompareHelper c9=new CompareHelper();
        c9.setOperation(object.getString("oleic.operation"));
        c9.setValue(Float.parseFloat(object.getString("oleic.value").equals("")?"0":object.getString("oleic.value")));
        dnaRunDto.setOleic(c9);


        CompareHelper c10=new CompareHelper();
        c10.setOperation(object.getString("palmitic.operation"));
        c10.setValue(Float.parseFloat(object.getString("palmitic.value").equals("")?"0":object.getString("palmitic.value")));
        dnaRunDto.setPalmitic(c10);

        CompareHelper c11=new CompareHelper();
        c11.setOperation(object.getString("stearic.operation"));
        c11.setValue(Float.parseFloat(object.getString("stearic.value").equals("")?"0":object.getString("stearic.value")));
        dnaRunDto.setStearic(c11);


        return dnaRunDto;
    }
}
