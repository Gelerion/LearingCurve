package com.denis.learning.dsl.example;

import com.denis.learning.dsl.Parameters;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        List<Customer> customers = CustomerQueryOptions.listCustomers(config -> {
            config.includeHidden = true;
            config.companyName = "A Company";
        });

        customers.forEach(System.out::println);

        foo(list -> {
            list.add("Hello");
            list.add("World");
            // list.addTokenInfo(5); this would be a compile failure
        });
    }

    public static void foo(Parameters<ArrayList<String>> params) {
        params.get().forEach(System.out::println);
    }
}
