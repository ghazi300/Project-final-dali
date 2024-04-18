package com.example.demo.services;

import java.util.List;

public interface ICrudeService<Class,TypeId>{
        List<Class> findAll();

        Class retrieveItem(TypeId idItem);
        Class add(Class class1) ;

        void delete(TypeId id);

        Class update(Class Classe1,TypeId id);


    }
