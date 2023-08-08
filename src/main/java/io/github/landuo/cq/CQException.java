package io.github.landuo.cq;

import lombok.Data;

/**
 * @author accidia
 */
@Data
public class CQException extends RuntimeException{
    private String msg;
    private Throwable cause;

    public CQException(String message) {
        this.msg = message;
    }

    public CQException(String message, Throwable cause) {
        this.cause = cause;
        this.msg = message;
    }
}
