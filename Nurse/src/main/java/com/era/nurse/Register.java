package com.era.nurse;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Register {

    private Map<String, Object> register = new HashMap<>();
    private Map<Field, Object> injectionsPlaces = new HashMap<>();

    public Register() {

    }

    public Optional<Object> get(String absent){
        Object something = register.get(absent);
        return Optional.ofNullable(something);
    }

    void add(String name, Object something) {
        if (register.containsKey(name)){
            throw new RuntimeException();
        }

        for (Field field : something.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(Inject.class)){
                injectionsPlaces.put(field, something);
            }
        }

        register.put(name, something);
    }


    void add(Object component){
        add(component.getClass().getName(), component);
    }

    void add(Class type) {
        Object instance = null;
        try {
            instance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        add(type.getName(), instance);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Class<T> type) {
        return (Optional<T>) get(type.getName());
    }

    void inject(){
        for (Field field : injectionsPlaces.keySet()){
            Object something = injectionsPlaces.get(field);
            Object injection = this.get(field.getType()).get();
            field.setAccessible(true);
            try {
                field.set(something, injection);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
