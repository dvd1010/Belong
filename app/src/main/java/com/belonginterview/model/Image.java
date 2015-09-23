package com.belonginterview.model;

import java.io.Serializable;

/**
 * Created by SuperProfs on 22/09/15.
 */
public class Image implements Serializable {

    private String s;
    private String m;
    private String l;
    private String xl;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }
}
