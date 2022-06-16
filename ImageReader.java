import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class reads the file and returns the contents of the file as an ImageView.
 *
 * @author Nina Peire (k19026756), Bhavik Gilbert (k21004990), Lavish Kamal Kumar (k21013224), Heman Seegolam (k21003628)
 * @version 28/03/2022
 */
public class ImageReader
{
    /**
     * Opens and reads an image file at a given filepath.
     * Sets a predetermined height and width.
     * 
     * @param filename Path of file from images folder.
     * @param height Height to set to image object.
     * @param width Width to set to image object.
     * @return imageView An object containing the image data.
     */
    public static ImageView readImage(String filepath, int height, int width) {
        ImageView imageView = readImage(filepath);

        if (imageView == null) {
            return null;
        }
        
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        return imageView;
    }

    public static ImageView readImage(String filepath) {
        Image image;
        try {
            image = new Image(new FileInputStream(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        ImageView imageView = new ImageView(image);

        return imageView;
    }
}