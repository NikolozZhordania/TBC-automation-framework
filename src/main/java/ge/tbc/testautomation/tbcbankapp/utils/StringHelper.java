package ge.tbc.testautomation.tbcbankapp.utils;

public class StringHelper {
    public static String normalize(String text) {
        return text == null ? "" : text.trim().replaceAll("\\s+", " ");
    }
}