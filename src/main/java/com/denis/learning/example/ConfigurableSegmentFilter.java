package com.denis.learning.example;

public interface ConfigurableSegmentFilter<T> extends SegmentFilter<T>, SelfConfigurable<ConfigurableSegmentFilter<T>> {
    @Override
    ConfigurableSegmentFilter<T> configure(String settings);
}