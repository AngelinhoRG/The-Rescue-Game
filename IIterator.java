package com.mycompany.a4;

//Interface for the custom iterator
public interface IIterator<T> {
 boolean hasNext();
 T getNext();
 void remove();
}
