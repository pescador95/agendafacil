package app.core.helper;

import app.api.agendaFacil.management.auth.entity.Auth;
import app.core.helpers.utils.StringFunctions;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.core.helpers.utils.BasicFunctions.log;

public class BasicHelper {

    public BasicHelper() {
    }

    public static void main(String[] args) {
        mapToJsonExample();
        objectToJsonExample();
        listObjectJsonExample();
        attributeToJsonExemple();
        dateFormatExemple();
    }


    public static void mapToJsonExample() {

        log("Method: mapToJson");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("token", "exampleToken");
        attributes.put("password", "examplePassword");
        attributes.put("age", 30);

        String result = StringFunctions.mapToJson(attributes);
        log(result);
        log("");
    }

    public static void objectToJsonExample() {

        log("Method: objectToJson");

        Auth auth = new Auth("foo", "123456");

        String result = StringFunctions.objectToJson(auth);
        log(result);
        log("");
    }

    public static void listObjectJsonExample() {

        log("Method: listObjectJson");

        Auth auth = new Auth("foo", "123456");
        Auth auth2 = new Auth("bar", "123456");

        List<Auth> params = new ArrayList<>();

        params.add(auth);
        params.add(auth2);

        String result = StringFunctions.listObjectToJson(params);
        log(result);
        log("");
    }


    public static void attributeToJsonExemple() {

        log("Method: attributeToJson");

        String key = "login";
        String value = "iedio";
        String result = StringFunctions.attributeToJson(key, value);
        log(result);
        log("");
    }

    public static void dateFormatExemple() {

        log("Method: dateFormat");

        String result = StringFunctions.dateFormat(LocalDate.now());
        log(result);
        log("");
    }

    public String mapToJson(Map<String, ?> attributes) {
        return StringFunctions.mapToJson(attributes);
    }

    public String objectToJson(Object obj) {
        return StringFunctions.objectToJson(obj);
    }

    public <T> String listObjectToJson(T... objs) {
        return StringFunctions.listObjectToJson(objs);
    }

    public String attributeToJson(String name, String value) {
        return StringFunctions.attributeToJson(name, value);
    }

    public String dateFormat(@NotNull LocalDate date) {
        return StringFunctions.dateFormat(date);
    }

}
