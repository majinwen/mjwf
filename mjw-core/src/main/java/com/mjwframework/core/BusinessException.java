package com.mjwframework.core;

/**
 * Created by pony on 2016/5/8.
 */
/**
 * 业务异常。
 */
@SuppressWarnings("serial")
    public class BusinessException extends RuntimeException {

        private String code;

        /**
         * 构造方法。
         *
         * @param code    消息编码
         * @param message 异常信息
         */
        public BusinessException(String code, String message) {
            super(message);
            this.code = code;
        }

        /**
         * 构造方法。
         *
         * @param message 异常信息
         */
        public BusinessException(String message) {
            super(message);
        }

        /**
         * 构造方法。
         *
         * @param cause 异常原因
         */
        public BusinessException(Throwable cause) {
            super(cause);
        }

        /**
         * 构造方法。
         *
         * @param message 异常信息
         * @param cause   异常原因
         */
        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }

        public String getCode() {
            return code;
        }

}
