package com.emrehmrc.kodhamuru.model;

import java.io.Serializable;

public class BlogNotif implements Serializable {

    private String TITLE;
    private String READCOUNT;
    private String COMMENTCOUNT;
    private String ID;

    public BlogNotif(String TITLE, String READCOUNT, String COMMENTCOUNT, String ID) {
        this.TITLE = TITLE;
        this.READCOUNT = READCOUNT;
        this.COMMENTCOUNT = COMMENTCOUNT;
        this.ID = ID;
    }

    public BlogNotif() {

    }

    public String getTITLE() {

        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getREADCOUNT() {
        return READCOUNT;
    }

    public void setREADCOUNT(String READCOUNT) {
        this.READCOUNT = READCOUNT;
    }

    public String getCOMMENTCOUNT() {
        return COMMENTCOUNT;
    }

    public void setCOMMENTCOUNT(String COMMENTCOUNT) {
        this.COMMENTCOUNT = COMMENTCOUNT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
