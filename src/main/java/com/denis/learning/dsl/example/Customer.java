package com.denis.learning.dsl.example;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class Customer {
    public Date from;
    public boolean includeHidden;
    public String companyName;
    public boolean haveOrders;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("from", from)
                .append("includeHidden", includeHidden)
                .append("companyName", companyName)
                .append("haveOrders", haveOrders)
                .toString();
    }
}
