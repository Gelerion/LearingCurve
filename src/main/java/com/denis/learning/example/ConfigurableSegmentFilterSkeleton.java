package com.denis.learning.example;

public abstract class ConfigurableSegmentFilterSkeleton<T> implements ConfigurableSegmentFilter<T> {
    @Override
    public ConfigurableSegmentFilter<T> configure(String settings) {
        return null;
    }
}