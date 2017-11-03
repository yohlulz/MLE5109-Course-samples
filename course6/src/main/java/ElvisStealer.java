/**
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Nov 03, 2017
 */

import java.io.Serializable;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public class ElvisStealer implements Serializable {
    static Elvis impersonator;
    private Elvis payload;

    private Object readResolve() {
        // Save a reference to the "unresolved" SingletonElvis instance
        impersonator = payload;

        // Return an object of correct type for favorites field
        return new String[]{"A Fool Such as I"};
    }

    private static final long serialVersionUID = 0;
}
