package cl.zpricing.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErroresUtils {
	public static String extraeStackTrace(Throwable t) {
        if(t == null) {
            return "";
        } 
        else {
            StringWriter s = new StringWriter();
            t.printStackTrace(new PrintWriter(s, true));
            return s.toString();
        }
    }
}
