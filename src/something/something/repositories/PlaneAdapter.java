package something.something.repositories;

import com.google.gson.*;
import something.something.model.plane.BronzePlane;
import something.something.model.plane.GoldPlane;
import something.something.model.plane.Plane;
import something.something.model.plane.SilverPlane;

import java.lang.reflect.Type;

//https://technology.finra.org/code/serialize-deserialize-interfaces-in-java.html
//Alternativamente se podría hacer que la clase Plane guardara que tipo de avión es.
//https://stackoverflow.com/questions/5800433/polymorphism-with-gson

public class PlaneAdapter<T> implements JsonSerializer<T>, JsonDeserializer{

    private static final String TYPE = "type";
    private static final String DATA = "DATA";

    public T deserialize(JsonElement jsonElement, Type type,
                         JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        //System.out.println(jsonObject);
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(TYPE);
        //System.out.println("prim: " + prim);
        String className = prim.getAsString();
        //System.out.println("classname: " + className);
        Class klass = getObjectClass(className);
        //System.out.println("klass: " + klass);
        return jsonDeserializationContext.deserialize(jsonObject, klass);
    }
    public JsonElement serialize(T jsonElement, Type type, JsonSerializationContext context) {
        /*
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
         */
        JsonElement result = new JsonObject();

        if (jsonElement instanceof BronzePlane) {
            result = context.serialize(jsonElement, BronzePlane.class);
        }
        else if (jsonElement instanceof SilverPlane) {
            result = context.serialize(jsonElement, SilverPlane.class);
        }
        else if (jsonElement instanceof GoldPlane) {
            result = context.serialize(jsonElement, GoldPlane.class);
        }
        return result;
    }


    /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String type) {
        try {
            return Class.forName("something.something.model.plane." + type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase()+ "Plane");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }

}
