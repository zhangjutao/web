package com.gooalgene.dna.jackson;

import com.gooalgene.utils.Alias;

/**
 * Created by crabime on 3/18/18.
 */
public class TestSample {
    private String name;
    private String value;
    @Alias("deserve")
    private String deserveLocation;

    public TestSample() {
    }

    public String getDeserveLocation() {
        return deserveLocation;
    }

    public void setDeserveLocation(String deserveLocation) {
        this.deserveLocation = deserveLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
