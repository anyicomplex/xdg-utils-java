/*
 * MIT License
 *
 * Copyright (c) 2022 Yi An
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.anyicomplex.xdg.utils;

import static com.anyicomplex.xdg.utils.XDGScreenSaver.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGScreenSaver.Option.*;

/**
 * <p>xdg-screensaver provides commands to control the screensaver.</p>
 *<br>
 * <p> xdg-screensaver is for use inside a desktop session only. It is<br>
 *  not recommended to use xdg-screensaver as root.</p>
 */
public final class XDGScreenSaver {

    private XDGScreenSaver(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-open";

    /**
     * All available subcommands.
     */
    public static final class Subcommand {
        private Subcommand(){}
        /**
         * <p>Suspends the screensaver and monitor power management.<br>
         *  WindowID must be the X Window ID of an existing window<br>
         *  of the calling application. The window must remain in<br>
         *  existence for the duration of the suspension.</p>
         *<br>
         * <p> WindowID can be represented as either a decimal number<br>
         *  or as a hexadecimal number consisting of the prefix 0x<br>
         *  followed by one or more hexadecimal digits.</p>
         *<br>
         * <p> The screensaver can be suspended in relation to multiple<br>
         *  windows at the same time. In that case screensaver<br>
         *  operation is only restored once the screensaver has been<br>
         *  resumed in relation to each of the windows</p>
         */
        public static final String SUSPEND = "suspend";
        /**
         * <p>Resume the screensaver and monitor power management<br>
         *  after being suspended. WindowID must be the same X<br>
         *  Window ID that was passed to a previous call of<br>
         *  xdg-screensaver suspend</p>
         */
        public static final String RESUME = "resume";
        /**
         * <p>Turns the screensaver on immediately. This may result in<br>
         *  the screen getting locked, depending on existing system<br>
         *  policies.<br>
         * </p>
         */
        public static final String ACTIVATE = "activate";
        /**
         * Lock the screen immediately.
         */
        public static final String LOCK = "lock";
        /**
         * <p>Turns the screensaver off immediately. If the screen was<br>
         *  locked the user may be asked to authenticate first.</p>
         */
        public static final String RESET = "reset";
        /**
         * <p>Prints enabled to stdout if the screensaver is enabled<br>
         *  to turn on after a period of inactivity and prints<br>
         *  disabled if the screensaver is not enabled.</p>
         */
        public static final String STATUS = "status";
    }

    /**
     * All available options.
     */
    public static final class Option extends XDGUtils.Option {
        private Option(){}
    }

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * <p>Suspends the screensaver and monitor power management.<br>
     *  WindowID must be the X Window ID of an existing window<br>
     *  of the calling application. The window must remain in<br>
     *  existence for the duration of the suspension.</p>
     *<br>
     * <p> WindowID can be represented as either a decimal number<br>
     *  or as a hexadecimal number consisting of the prefix 0x<br>
     *  followed by one or more hexadecimal digits.</p>
     *<br>
     * <p> The screensaver can be suspended in relation to multiple<br>
     *  windows at the same time. In that case screensaver<br>
     *  operation is only restored once the screensaver has been<br>
     *  resumed in relation to each of the windows</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param WindowID the window id
     * @return the exit code
     */
    public static int suspend(StringBuilder output, long WindowID) {
        return XDGUtils.process(output, getScriptPath(), SUSPEND, "0x" + Long.toHexString(WindowID));
    }

    /**
     * <p>Resume the screensaver and monitor power management<br>
     *  after being suspended. WindowID must be the same X<br>
     *  Window ID that was passed to a previous call of<br>
     *  xdg-screensaver suspend</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param WindowID the window id
     * @return the exit code
     */
    public static int resume(StringBuilder output, long WindowID) {
        return XDGUtils.process(output, getScriptPath(), RESUME, "0x" + Long.toHexString(WindowID));
    }

    /**
     * <p>Turns the screensaver on immediately. This may result in<br>
     *  the screen getting locked, depending on existing system<br>
     *  policies.<br>
     * </p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int activate(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), ACTIVATE);
    }

    /**
     * Lock the screen immediately.
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int lock(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), LOCK);
    }

    /**
     * <p>Turns the screensaver off immediately. If the screen was<br>
     *  locked the user may be asked to authenticate first.</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int reset(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), RESET);
    }

    /**
     * <p>Prints enabled to stdout if the screensaver is enabled<br>
     *  to turn on after a period of inactivity and prints<br>
     *  disabled if the screensaver is not enabled.</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int status(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), STATUS);
    }

    /**
     * Output command synopsis.
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int help(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), HELP);
    }

    /**
     * Output manual page.
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int manual(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), MANUAL);
    }

    /**
     * Output the version information.
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int version(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), VERSION);
    }

}
