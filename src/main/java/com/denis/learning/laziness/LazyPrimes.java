package com.denis.learning.laziness;

import static com.denis.learning.laziness.LazyLists.primes;

/**
 * Created by denis.shuvalov on 04/02/2018.
 */
public class LazyPrimes {

    public static void main(String[] args) {
        LazyList<Integer> numbers = LazyLists.from(2);

/*        int two = primes(numbers).head();
        System.out.println("two = " + two);
        int three = primes(numbers).tail().head();
        System.out.println("three = " + three);
        int five = primes(numbers).tail().tail().head();
        System.out.println("five = " + five);
        int seven = primes(numbers).tail().tail().tail().head();
        System.out.println("seven = " + seven);*/

        LazyLists.printAll(primes(numbers));

    }




}
