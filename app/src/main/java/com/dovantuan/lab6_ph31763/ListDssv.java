package com.dovantuan.lab6_ph31763;

import java.io.Serializable;

public class ListDssv implements Serializable {

    public String branch;

    public String name;
    public String address;

    public ListDssv(String branch, String name, String address) {
        this.branch = branch;
        this.name = name;
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
