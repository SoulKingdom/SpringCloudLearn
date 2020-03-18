package com.springcloud.core.exception;

/**
 * <p>服务端异常</p>
 * <p>创建日期：2018-02-28</p>
 *
 * @author 刘昊鑫
 */
public class ScServerException extends ScRuntimeException {

    public ScServerException() {
    }

    public ScServerException(String message) {
        super(message);
    }

    public ScServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScServerException(Throwable cause) {
        super(cause);
    }

    public ScServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
