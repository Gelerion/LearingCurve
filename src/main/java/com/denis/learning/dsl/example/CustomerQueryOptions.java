package com.denis.learning.dsl.example;

import com.denis.learning.dsl.Parameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerQueryOptions {
    public Date from = null;
    public boolean includeHidden = true;
    public String companyName = null;
    public boolean haveOrders = true;

    public static List<Customer> listCustomers(Parameters<CustomerQueryOptions> spec) {
        System.out.println(spec.get().haveOrders);
        Customer customer = new Customer();
        customer.haveOrders = spec.get().haveOrders;
        customer.companyName = spec.get().companyName;
        customer.includeHidden = spec.get().includeHidden;
        customer.from = spec.get().from;
        List<Customer> res = new ArrayList<>();
        res.add(customer);
        return res;
    }
}
