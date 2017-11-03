import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}
