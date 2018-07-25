package com.era.jsonserializator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import javax.json.*;

public class JsonSerializator {

    private static JsonObjectBuilder createModel(JsonObjectBuilder jsonObjectBuilder, Object object){
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields){
            try {
                if (field.getType().isAssignableFrom(int.class)) {
                    jsonObjectBuilder.add(field.getName(), field.getInt(object));
                }
                else if (field.getType().isAssignableFrom(byte.class)){
                    jsonObjectBuilder.add(field.getName(), field.getByte(object));
                }
                else if (field.getType().isAssignableFrom(boolean.class)){
                    jsonObjectBuilder.add(field.getName(), field.getBoolean(object));
                }
                else if (field.getType().isAssignableFrom(char.class)){
                    jsonObjectBuilder.add(field.getName(), field.getChar(object));
                }
                else if (field.getType().isAssignableFrom(long.class)){
                    jsonObjectBuilder.add(field.getName(), field.getLong(object));
                }
                else if (field.getType().isAssignableFrom(double.class)){
                    jsonObjectBuilder.add(field.getName(), field.getDouble(object));
                }
                else if (field.getType().isAssignableFrom(String.class)){
                    jsonObjectBuilder.add(field.getName(), (String)field.get(object));
                }
                else if (field.getType().isAssignableFrom(BigInteger.class)){
                    jsonObjectBuilder.add(field.getName(), (BigInteger)field.get(object));
                }
                else if (field.getType().isAssignableFrom(BigDecimal.class)){
                    jsonObjectBuilder.add(field.getName(), (BigDecimal) field.get(object));
                }
                else {
                    JsonObjectBuilder builder = Json.createObjectBuilder();
                    jsonObjectBuilder.add(field.getName(), createModel(builder, field.get(object)));
                }

            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return jsonObjectBuilder;
    }

    private static String serializeObject(Object object){
        StringWriter stringWriter = new StringWriter();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        JsonObject model = createModel(jsonObjectBuilder, object).build();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)){
            jsonWriter.writeObject(model);
        }

        return stringWriter.toString();
    }

    private static String serializeArray(Object object){
        StringWriter stringWriter = new StringWriter();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        Class ofArray = object.getClass().getComponentType();
        if (ofArray.isPrimitive()) {
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object obj = Array.get(object, i);

                if (obj.getClass().isAssignableFrom(Integer.class))
                    jsonArrayBuilder.add((int)obj);
                else if (obj.getClass().isAssignableFrom(Long.class))
                    jsonArrayBuilder.add((long)obj);
                else if (obj.getClass().isAssignableFrom(Double.class))
                    jsonArrayBuilder.add((double)obj);
                else if (obj.getClass().isAssignableFrom(Boolean.class))
                    jsonArrayBuilder.add((boolean)obj);
                else if (obj.getClass().isAssignableFrom(String.class))
                    jsonArrayBuilder.add((String)obj);
            }
        }
        else {
            Object[] objects = (Object[]) object;
            for (Object obj : objects) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                jsonArrayBuilder.add(createModel(objectBuilder, obj));
            }
        }

        JsonArray jsonArray = jsonArrayBuilder.build();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)){
            jsonWriter.writeArray(jsonArray);
        }

        return stringWriter.toString();
    }

    private static boolean isList(Object object){
        Type[] genericInterfaces = object.getClass().getGenericInterfaces();
        for (Type gInterface : genericInterfaces){
            if (gInterface.getTypeName().equals("java.util.List<E>")){
                return true;
            }
        }

        return false;
    }

    private static boolean isSet(Object object){
        Type[] genericInterfaces = object.getClass().getGenericInterfaces();
        for (Type gInterface : genericInterfaces){
            if (gInterface.getTypeName().equals("java.util.Set<E>")){
                return true;
            }
        }

        return false;
    }

    public static void writeObject (Object object, String filename){
        String jsonData;
        if (object.getClass().isPrimitive()) jsonData = serializePrimitive(object);
        else {

            if (object.getClass().isArray())
                jsonData = serializeArray(object);
            else if (isList(object)) jsonData = SerializeList(object);
            else if (isSet(object)) jsonData = SerializeSet(object);
            else  jsonData = serializeObject(object);

        }
        byte[] textBytes = jsonData.getBytes(Charset.forName("UTF-8"));
        try (FileOutputStream stream = new FileOutputStream(filename)){
            stream.write(textBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String SerializeSet(Object object) {
        Object array = ((Set)object).toArray();

        return serializeArray(array);
    }

    private static  String SerializeList(Object object){

        Object array = ((List)object).toArray();

        return serializeArray(array);
    }

    private static String serializePrimitive(Object object) {
        StringWriter stringWriter = new StringWriter();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        String name = object.getClass().getName();

        if (object.getClass().isAssignableFrom(int.class))
            jsonObjectBuilder.add(name, (int)object);
        else if (object.getClass().isAssignableFrom(long.class))
            jsonObjectBuilder.add(name, (long)object);
        else if (object.getClass().isAssignableFrom(double.class))
            jsonObjectBuilder.add(name, (double)object);
        else if (object.getClass().isAssignableFrom(boolean.class))
            jsonObjectBuilder.add(name, (boolean)object);
        else if (object.getClass().isAssignableFrom(String.class))
            jsonObjectBuilder.add(name, (String)object);

        JsonObject model = jsonObjectBuilder.build();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)){
            jsonWriter.writeObject(model);
        }

        return stringWriter.toString();
    }
}
