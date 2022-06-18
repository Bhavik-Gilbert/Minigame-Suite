package Tools;

import javafx.scene.control.*;

/**
 * This class generates an alert based on the inputs given.
 *
 */
public class AlertBox
{
    /**
     * Displays a information type alert box for information.
     */
    public static void informationBox(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        addCSS(alert);

        alert.showAndWait();
    }

    /**
     * Displays an error type alert box for errors.
     */
    public static void errorBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        addCSS(alert);

        alert.showAndWait();
    }

    /**
     * Adds the CSS file to the given alert.
     * 
     * @param alert The alert the CSS file is applied to.
     */
    private static void addCSS(Alert alert)
    {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("resources/CSS/alert.css");
    }
}