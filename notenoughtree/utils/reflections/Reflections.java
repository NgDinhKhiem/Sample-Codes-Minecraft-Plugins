package com.mrnatsu.notenoughtree.utils.reflections;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reflections {
    private final String packageName;

    public Reflections(String packageName){
        this.packageName=packageName;
    }

    public <T> List<Class<? extends T>> getSubTypesOf(Class<T> type){
        //Removed
    }
}
