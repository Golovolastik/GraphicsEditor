package utility;

import java.util.Optional;

public interface Mover {
    public void moveAbsolutely(Optional<Double[]> parseResult);
    public void moveRelatively(Optional<Double[]> parseResult);
}
