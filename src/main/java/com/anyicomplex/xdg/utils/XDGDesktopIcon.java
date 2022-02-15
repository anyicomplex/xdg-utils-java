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

import static com.anyicomplex.xdg.utils.XDGDesktopIcon.Option.*;
import static com.anyicomplex.xdg.utils.XDGDesktopIcon.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

/**
 * <p>The xdg-desktop-icon program can be used to install an<br>
 *  application launcher or other file on the desktop of the<br>
 *  current user.</p>
 *<br>
 * <p> An application launcher is represented by a *.desktop file.<br>
 *  Desktop files are defined by the freedesktop.org Desktop Entry<br>
 *  Specification. The most important aspects of *.desktop files<br>
 *  are summarized below.</p>
 *<br>
 *  <p>Desktop Files</p>
 *<br>
 * <p> An application launcher can be added to the desktop by<br>
 *  installing a *.desktop file. A *.desktop file consists of a<br>
 *  [Desktop Entry] header followed by several Key=Value lines.</p>
 *<br>
 * <p> A *.desktop file can provide a name and description for an<br>
 *  application in several different languages. This is done by<br>
 *  adding a language code as used by LC_MESSAGES in square<br>
 *  brackets behind the Key. This way one can specify different<br>
 *  values for the same Key depending on the currently selected<br>
 *  language.</p>
 *<br>
 * <p> The following keys are often used:</p>
 *<br>
 * <p> Type=Application<br>
 *  This is a mandatory field that indicates that the<br>
 *  *.desktop file describes an application launcher.</p>
 *<br>
 * <p> Name=Application Name<br>
 *  The name of the application. For example Mozilla</p>
 *<br>
 * <p> GenericName=Generic Name<br>
 *  A generic description of the application. For example<br>
 *  Web Browser</p>
 *<br>
 * <p> Comment=Comment<br>
 *  Optional field to specify a tooltip for the application.<br>
 *  For example Visit websites on the Internet</p>
 *<br>
 * <p> Icon=Icon File<br>
 *  The icon to use for the application. This can either be<br>
 *  an absolute path to an image file or an icon-name. If an<br>
 *  icon-name is provided an image lookup by name is done in<br>
 *  the user's current icon theme. The xdg-icon-resource<br>
 *  command can be used to install image files into icon<br>
 *  themes. The advantage of using an icon-name instead of<br>
 *  an absolute path is that with an icon-name the<br>
 *  application icon can be provided in several different<br>
 *  sizes as well as in several differently themed styles.</p>
 *<br>
 * <p> Exec=Command Line<br>
 *  The command line to start the application. If the<br>
 *  application can open files the %f placeholder should be<br>
 *  specified. When a file is dropped on the application<br>
 *  launcher the %f is replaced with the file path of the<br>
 *  dropped file. If multiple files can be specified on the<br>
 *  command line the %F placeholder should be used instead<br>
 *  of %f. If the application is able to open URLs in<br>
 *  addition to local files then %u or %U can be used<br>
 *  instead of %f or %F.</p>
 *<br>
 * <p> For a complete overview of the *.desktop file format please<br>
 *  visit<br>
 *  <a href="http://www.freedesktop.org/wiki/Specifications/desktop-entry-spec">http://www.freedesktop.org/wiki/Specifications/desktop-entry-spec</a></p>
 *<br>
 * <p>Environment Variables</p>
 *<br>
 * <p> xdg-desktop-icon honours the following environment variables:</p>
 *<br>
 * <p> XDG_UTILS_DEBUG_LEVEL<br>
 *  Setting this environment variable to a non-zero<br>
 *  numerical value makes xdg-desktop-icon do more verbose<br>
 *  reporting on stderr. Setting a higher value increases<br>
 *  the verbosity.<br>
 * </p>
 *
 * @see XDGIconResource
 */
public final class XDGDesktopIcon {

    private XDGDesktopIcon(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-desktop-icon";

    /**
     * All available subcommands.
     */
    public static final class Subcommand {
        private Subcommand(){}
        /**
         * <p>Installs FILE to the desktop of the current user. FILE<br>
         *  can be a *.desktop file or any other type of file.</p>
         */
        public static final String INSTALL = "install";
        /**
         * Removes FILE from the desktop of the current user.
         */
        public static final String UNINSTALL = "uninstall";
    }

    /**
     * All available options.
     */
    public static final class Option {
        private Option(){}
        /**
         * <p>Normally, xdg-desktop-icon checks to ensure that a<br>
         *  *.desktop file to be installed has a vendor prefix. This<br>
         *  option can be used to disable that check.</p>
         *<br>
         * <p> A vendor prefix consists of alpha characters ([a-zA-Z])<br>
         *  and is terminated with a dash (&quot;-&quot;). Companies and<br>
         *  organizations are encouraged to use a word or phrase,<br>
         *  preferably the organizations name, for which they hold a<br>
         *  trademark as their vendor prefix. The purpose of the<br>
         *  vendor prefix is to prevent name conflicts.</p>
         */
        public static final String NOVENDOR = "--novendor";
    }

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * <p>Installs FILE to the desktop of the current user. FILE<br>
     *  can be a *.desktop file or any other type of file.</p>
     *
     * @see Option#NOVENDOR
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param novendor whether novendor
     * @param FILE the file path
     * @return the exit code
     */
    public static int install(StringBuilder output, Boolean novendor, String FILE) {
        return XDGUtils.process(output, getScriptPath(), INSTALL, notNullBoolean(novendor) ? NOVENDOR : "", isEmpty(FILE) ? "" : FILE);
    }

    /**
     * Removes FILE from the desktop of the current user.
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param FILE the file path
     * @return the exit code
     */
    public static int uninstall(StringBuilder output, String FILE) {
        return XDGUtils.process(output, getScriptPath(), UNINSTALL, isEmpty(FILE) ? "" : FILE);
    }

}
