package utility;

import java.util.Optional;

public interface Mover {
    void moveAbsolutely(Optional<Double[]> parseResult);
    void moveRelatively(Optional<Double[]> parseResult);
    void moveOnX(double distance);
    void moveOnY(double distance);

}
