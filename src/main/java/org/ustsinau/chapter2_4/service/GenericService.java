package org.ustsinau.chapter2_4.service;


import java.util.List;

public interface GenericService<T, ID> {
    T getById(ID id);

    T create(T t);

    T update(T t);

    void deleteById(ID id);

    List<T> getAll();
}
