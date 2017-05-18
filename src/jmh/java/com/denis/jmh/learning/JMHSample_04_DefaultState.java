package com.denis.jmh.learning;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


/*
 * Fortunately, in many cases you just need a single state object.
 * In that case, we can mark the benchmark instance itself to be
 * the @State. Then, we can reference its own fields as any
 * Java program does.
 */


@State(Scope.Thread)
public class JMHSample_04_DefaultState {


    double x = Math.PI;

    @Benchmark
    public void measure() {
        x++;
    }
}