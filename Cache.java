package com.program.cache.impl;

/**
 * A memory cache interface.
 *
 * @author Rohit Kumar
 */
public interface Cache<K, V> {

    /**
     * Gets an value for the specified {@code key} or return {@code null}.
     *
     * @param key key
     * @return the value or {@code null}.
     */
    V get(K key);

    /**
     * Puts an value in the cache for the specified {@code key}.
     *
     * @param key   key
     * @param value image
     * @return the previous value.
     */
    V put(K key, V value);

}
