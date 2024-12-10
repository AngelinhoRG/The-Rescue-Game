package com.mycompany.a4;

//Interface for the collection class
public interface ICollection<T> {
 void add(T obj);
 IIterator<T> getIterator();
}



