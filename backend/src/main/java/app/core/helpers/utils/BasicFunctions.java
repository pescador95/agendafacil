package app.core.helpers.utils;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class BasicFunctions {

    public static <T> boolean isNotNull(T... objects) {
        for (T object : objects) {
            if (isNull(object)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isNull(T... objects) {
        for (T object : objects) {
            if (isNotNull(object)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isNotNull(T object) {
        return object != null;
    }

    public static <T> boolean isNull(T object) {
        return object == null;
    }

    public static <T> boolean isNotEmpty(T... objects) {
        for (T object : objects) {
            if (!isNotEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isNotEmpty(T object) {

        try {

            if (object == null) {
                return false;
            }

            if (object instanceof String) {
                return !((String) object).isEmpty();
            }

            if (object instanceof List) {
                return !((List<?>) object).isEmpty();
            }

            if (object instanceof Integer) {
                return (Integer) object != 0;
            }

            if (object instanceof Double) {
                return (Double) object != 0.0;
            }

            if (object instanceof Float) {
                return (Float) object != 0.0f;
            }

            if (object instanceof Long) {
                return (Long) object != 0;
            }

            if (object instanceof Boolean) {
                return (Boolean) object;
            }

            if (object instanceof BigDecimal) {
                return ((BigDecimal) object).compareTo(BigDecimal.ZERO) != 0;
            }

            if (object instanceof Object[]) {
                return ((Object[]) object).length != 0;
            }

            if (object instanceof byte[]) {
                return ((byte[]) object).length != 0;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> boolean isEmpty(T... objects) {
        for (T object : objects) {
            if (isNotEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isEmpty(T object) {
        return !isNotEmpty(object);
    }

    public static <T> boolean isInvalid(T... objects) {
        for (T object : objects) {
            if (isValid(object)) {
                return false;
            }
        }
        return true;
    }

    public static <t> boolean isInvalid(t object) {
        return !isValid(object);
    }


    public static <T> boolean isValid(T... objects) {
        for (T object : objects) {
            if (!isValid(object)) {
                return false;
            }
        }
        return true;
    }

    public static <t> boolean isValid(t object) {

        try {

            if (object == null) {
                return false;
            }

            if (object instanceof String) {
                return !((String) object).isEmpty();
            }

            if (object instanceof List) {
                return !((List<?>) object).isEmpty();
            }

            if (object instanceof Integer) {
                return (Integer) object > 0;
            }

            if (object instanceof Double) {
                return (Double) object > 0.0;
            }

            if (object instanceof Float) {
                return (Float) object > 0.0f;
            }

            if (object instanceof Long) {
                return (Long) object > 0;
            }
            if (object instanceof LocalDate) {
                return ((LocalDate) object).isAfter(LocalDate.MIN) && ((LocalDate) object).isBefore(LocalDate.MAX);
            }
            if (object instanceof LocalTime) {
                return ((LocalTime) object).isAfter(LocalTime.MIN) && ((LocalTime) object).isBefore(LocalTime.MAX);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void log(String message, Boolean trace) {
        if (trace) {
            log(message);
        }
    }

    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(List<String> messages, Boolean trace) {
        if (trace && BasicFunctions.isNotEmpty(messages)) {
            messages.forEach(System.out::println);
        }
    }

    public static void log(List<String> messages) {
        if (BasicFunctions.isNotEmpty(messages)) {
            messages.forEach(System.out::println);
        }
    }

    public static Boolean checkPassword(String userPassword, String password) {
        return BasicFunctions.isNotEmpty(password, userPassword)
                && BCrypt.checkpw(password, userPassword);
    }

    public static String setCryptedPassword(String password) {
        if (BasicFunctions.isNotEmpty(password)) {
            return BcryptUtil.bcryptHash(password);
        }
        return null;
    }

    public static <T> List<T> paginateList(List<T> fullList, int pageIndex, int pageSize) {
        int fromIndex = pageIndex * pageSize;
        if (fromIndex >= fullList.size()) {
            return Collections.emptyList();
        }
        int toIndex = Math.min(fromIndex + pageSize, fullList.size());
        return fullList.subList(fromIndex, toIndex);
    }
}
