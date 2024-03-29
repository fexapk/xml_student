package com.cesu.xml_students.data_acess;

import java.util.List;

public interface Dao<T> 
{
    T get(int id);

    List<T> getAll();

    void save(T t);

    void update(int id, T t);

    void delete(int id);
}
