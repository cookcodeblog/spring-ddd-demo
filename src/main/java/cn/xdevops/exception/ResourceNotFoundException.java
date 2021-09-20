package cn.xdevops.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super(String.format("Could not find resource %d", id));
    }

    public ResourceNotFoundException(Long id, String resource) {
        super(String.format("Could not find %s %d", resource, id));
    }
}
