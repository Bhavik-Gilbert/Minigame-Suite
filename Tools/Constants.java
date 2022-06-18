package Tools;

public class Constants {
    private static String separator = System.getProperty("file.separator");
    private static String homeSoundPath = "Resources" + Constants.separator + "Sounds" + Constants.separator + "Skyarrowbridge.wav";

    public static String getSeparator() {
        return separator;
    }
    public static String getHomeSoundPath() {
        return homeSoundPath;
    }
}
