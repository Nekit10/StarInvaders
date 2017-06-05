/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.classes.Exceptions;

/**
 * Settings access exception
 *
 * @author Nikita Serba
 * @version 1.0
 * @since 2.1
 */
public class SettingsAccessException extends Exception {

    /**
     * Create exception class
     *
     * @since 2.1
     */
    public SettingsAccessException() {
        super();
    }

    /**
     * Create exception class
     *
     * @since 2.1
     * @param msg - error message
     */
    public SettingsAccessException (String msg) {
        super(msg);
    }

    /**
     * Create exception class
     *
     * @since 2.1
     * @param msg - error message
     * @param cause - error cause
     */
    public SettingsAccessException (String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Create exception class
     *
     * @since 2.1
     * @param cause - error cause
     */
    public SettingsAccessException (Throwable cause) {
        super(cause);
    }

}
