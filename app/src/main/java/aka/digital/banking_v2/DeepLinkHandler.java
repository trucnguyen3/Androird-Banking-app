package aka.digital.banking_v2;

public class DeepLinkHandler {
    private static String deepLinkValue;

    public static void setDeepLinkValue(String value) { deepLinkValue = value; }
    public static String getDeepLinkValue() { return deepLinkValue; }
    public static void clear() { deepLinkValue = null; }
}

