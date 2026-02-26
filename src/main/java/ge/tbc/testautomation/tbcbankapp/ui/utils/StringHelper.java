package ge.tbc.testautomation.tbcbankapp.ui.utils;

public class StringHelper {
    public static String normalize(String text) {
        return text == null ? "" : text.trim().replaceAll("\\s+", " ");
    }
}