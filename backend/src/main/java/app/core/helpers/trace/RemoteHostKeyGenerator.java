package app.core.helpers.trace;

import app.core.helpers.utils.BasicFunctions;


public class RemoteHostKeyGenerator {


    public String generateKey(String userAgent) {


        try {

            String key = "";

            if (BasicFunctions.isNotEmpty(userAgent)) {
                key = userAgent + "_";
            }
            return key;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
