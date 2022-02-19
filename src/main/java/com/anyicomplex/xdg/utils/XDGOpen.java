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

import static com.anyicomplex.xdg.utils.XDGOpen.Option.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;

/**
 * <p>xdg-open opens a file or URL in the user's preferred<br>
 *  application. If a URL is provided the URL will be opened in the<br>
 *  user's preferred web browser. If a file is provided the file<br>
 *  will be opened in the preferred application for files of that<br>
 *  type. xdg-open supports file, ftp, http and https URLs.</p>
 *<br>
 * <p> xdg-open is for use inside a desktop session only. It is not<br>
 *  recommended to use xdg-open as root.</p>
 *
 * @see XDGMime
 * @see XDGSettings
 */
public final class XDGOpen {

    private XDGOpen(){}

    /**
     * All available options.
     */
    public static final class Option extends XDGUtils.Option {
        private Option(){}
    }

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-open";

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * <p>xdg-open - opens a file or URL in the user's preferred<br>
     *  application</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param fileOrURL the file or URL to be open
     * @return the exit code
     */
    public static int process(StringBuilder output, String fileOrURL) {
        return XDGUtils.process(output, getScriptPath(), isEmpty(fileOrURL) ? "" : fileOrURL);
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
