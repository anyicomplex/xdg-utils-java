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

import static com.anyicomplex.xdg.utils.XDGSettings.Option.*;
import static com.anyicomplex.xdg.utils.XDGSettings.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;

/**
 * <p>xdg-settings gets various settings from the desktop<br>
 *  environment. For instance, desktop environments often provide<br>
 *  proxy configuration and default web browser settings. Using<br>
 *  xdg-settings these parameters can be extracted for use by<br>
 *  applications that do not use the desktop environment's<br>
 *  libraries (which would use the settings natively).</p>
 *<br>
 * <p> xdg-settings is for use inside a desktop session only. It is<br>
 *  not recommended to use xdg-settings as root.<br>
 * </p>
 *<br>
 * <p>Properties</p>
 *<br>
 * <p> When using xdg-settings to get, check or set a desktop setting,<br>
 *  properties and possibly sub-properties are used to specify the<br>
 *  setting to be changed.</p>
 *<br>
 * <p> Some properties (such as default-web-browser) fully describe<br>
 *  the setting to be changed. Other properties (such as<br>
 *  default-url-scheme-handler) require more information (in this<br>
 *  case the actual scheme to set the default handler for) which<br>
 *  must be provided in a sub-property.</p>
 *
 * @see XDGMime
 * @see XDGOpen
 */
public final class XDGSettings {

    private XDGSettings(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-settings";

    /**
     * All available subcommands.
     */
    public static final class Subcommand {
        private Subcommand(){}
        /**
         * <p>Get various settings from the desktop<br>
         *  environment</p>
         */
        public static final String GET = "get";
        /**
         * <p>Check various settings from the desktop<br>
         *  environment</p>
         */
        public static final String CHECK = "check";
        /**
         * <p>Set various settings from the desktop<br>
         *  environment</p>
         */
        public static final String SET = "set";
    }

    /**
     * All available options.
     */
    public static final class Option extends XDGUtils.Option {
        private Option(){}
        /**
         * List all properties xdg-settings knows about.
         */
        public static final String LIST = "--list";
    }

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * <p>Get various settings from the desktop<br>
     *  environment</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param property the property to be got
     * @return the exit code
     */
    public static int get(StringBuilder output, String property) {
        return XDGUtils.process(output, getScriptPath(), GET, isEmpty(property) ? "" : property);
    }

    /**
     * <p>Check various settings from the desktop<br>
     *  environment</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param property the property to be checked
     * @param subproperty the subproperty to be checked
     * @return the exit code
     */
    public static int check(StringBuilder output, String property, String subproperty) {
        return XDGUtils.process(output, getScriptPath(), CHECK, isEmpty(property) ? "" : property, isEmpty(subproperty) ? "" : subproperty);
    }

    /**
     * <p>Set various settings from the desktop<br>
     *  environment</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param property the property to be set
     * @param subproperty the subproperty to be set
     * @param value the property value to be set
     * @return the exit code
     */
    public static int set(StringBuilder output, String property, String subproperty, String value) {
        return XDGUtils.process(output, getScriptPath(), SET, isEmpty(property) ? "" : property,
                isEmpty(subproperty) ? "" : subproperty, isEmpty(value) ? "" : value);
    }

    /**
     * List all properties xdg-settings knows about.
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @return the exit code
     */
    public static int list(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), LIST);
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
