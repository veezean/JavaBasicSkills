package com.veezean.skills.cache.jsr;

import javax.cache.annotation.*;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/10/26
 */
public class BookService {

    @CacheResult(cacheName = "books")
    public Book findBookByName(@CacheKey String name) {
        return new Book();
    }

    @CachePut(cacheName = "books")
    public void updateBookInfo(@CacheKey String bookName, @CacheValue Book book) {

    }

    @CacheRemove(cacheName = "books")
    public void deleteBookInfo(@CacheKey String bookName) {

    }
}
