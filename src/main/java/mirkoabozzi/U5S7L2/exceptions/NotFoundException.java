package mirkoabozzi.U5S7L2.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Element with id " + id + " not found");
    }

    // override per sfruttare la stessa eccezione
    public NotFoundException(String msg) {
        super(msg);
    }
}
