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

import java.util.ArrayList;

import static com.anyicomplex.xdg.utils.XDGIconResource.Option.*;
import static com.anyicomplex.xdg.utils.XDGIconResource.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

/**
 * <p>The xdg-icon-resource program can be used to install icon<br>
 *  resources into the desktop icon system in order to illustrate<br>
 *  menu entries, to depict desktop icons or to graphically<br>
 *  represent file types.</p>
 *<br>
 * <p> The desktop icon system identifies icons by name. Depending on<br>
 *  the required size, the choice of icon theme and the context in<br>
 *  which the icon is used, the desktop icon system locates an<br>
 *  appropriate icon resource to depict an icon. Icon resources can<br>
 *  be XPM files or PNG files.</p>
 *<br>
 * <p> The desktop icon system works according to the XDG Icon Theme<br>
 *  Specification at<br>
 *  <a href="http://www.freedesktop.org/wiki/Specifications/icon-theme-spec">http://www.freedesktop.org/wiki/Specifications/icon-theme-spec</a><br>
 * </p>
 *<br>
 * <p>Environment Variables</p>
 *<br>
 * <p> xdg-icon-resource honours the following environment variables:</p>
 *<br>
 * <p> XDG_UTILS_DEBUG_LEVEL<br>
 *  Setting this environment variable to a non-zero<br>
 *  numerical value makes xdg-icon-resource do more verbose<br>
 *  reporting on stderr. Setting a higher value increases<br>
 *  the verbosity.</p>
 *<br>
 * <p> XDG_UTILS_INSTALL_MODE<br>
 *  This environment variable can be used by the user or<br>
 *  administrator to override the installation mode. Valid<br>
 *  values are user and system.</p>
 *
 * @see XDGDesktopIcon
 * @see XDGDesktopMenu
 * @see XDGMime
 */
public final class XDGIconResource {

    private XDGIconResource(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-icon-resource";

    /**
     * All available subcommands.
     */
    public static final class Subcommand {
        private Subcommand(){}
        /**
         * <p>Installs the icon file indicated by icon-file to the<br>
         *  desktop icon system under the name icon-name. Icon names<br>
         *  do not have an extension. If icon-name is not provided<br>
         *  the name is derived from icon-file. The icon file must<br>
         *  have .png or .xpm as extension. If a corresponding .icon<br>
         *  file exists in the same location as icon-file it will be<br>
         *  installed as well.</p>
         */
        public static final String INSTALL = "install";
        /**
         * <p>Removes the icon indicated by icon-name from the desktop<br>
         *  icon system. Note that icon names do not have an<br>
         *  extension.</p>
         */
        public static final String UNINSTALL = "uninstall";
        /**
         * <p>Force an update of the desktop icon system. This is only<br>
         *  useful if the last call to xdg-icon-resource included<br>
         *  the --noupdate option.</p>
         */
        public static final String FORCEUPDATE = "forceupdate";
    }

    /**
     * All available options.
     */
    public static final class Option extends XDGUtils.Option {
        private Option(){}
        /**
         * <p>Postpone updating the desktop icon system. If multiple<br>
         *  icons are added in sequence this flag can be used to<br>
         *  indicate that additional changes will follow and that it<br>
         *  is not necessary to update the desktop icon system right<br>
         *  away.</p>
         */
        public static final String NOUPDATE = "--noupdate";
        /**
         * <p>Normally, xdg-icon-resource checks to ensure that an<br>
         *  icon file to be installed in the apps context has a<br>
         *  proper vendor prefix. This option can be used to disable<br>
         *  that check.</p>
         *<br>
         * <p> A vendor prefix consists of alpha characters ([a-zA-Z])<br>
         *  and is terminated with a dash (&quot;-&quot;). Companies and<br>
         *  organizations are encouraged to use a word or phrase,<br>
         *  preferably the organizations name, for which they hold a<br>
         *  trademark as their vendor prefix. The purpose of the<br>
         *  vendor prefix is to prevent name conflicts.</p>
         */
        public static final String NOVENDOR = "--novendor";
        /**
         * <p>Installs or removes the icon file as part of theme. If<br>
         *  no theme is specified the icons will be installed as<br>
         *  part of the default hicolor theme. Applications may<br>
         *  install icons under multiple themes but should at least<br>
         *  install icons for the default hicolor theme.</p>
         */
        public static final String THEME = "--theme";
        /**
         * <p>Specifies the context for the icon. Icons to be used in<br>
         *  the application menu and as desktop icon should use apps<br>
         *  as context which is the default context. Icons to be<br>
         *  used as file icons should use mimetypes as context.<br>
         *  Other common contexts are actions, devices, emblems,<br>
         *  filesystems and stock.</p>
         */
        public static final String CONTEXT = "--context";
        /**
         * <p>mode can be user or system. In user mode the file is<br>
         *  (un)installed for the current user only. In system mode<br>
         *  the file is (un)installed for all users on the system.<br>
         *  Usually only root is allowed to install in system mode.</p>
         *<br>
         * <p> The default is to use system mode when called by root<br>
         *  and to use user mode when called by a non-root user.</p>
         */
        public static final String MODE = "--mode";
        /**
         * <p>Specifies the size of the icon. All icons must be<br>
         *  square. Common sizes for icons in the apps context are:<br>
         *  16, 22, 32, 48, 64 and 128. Common sizes for icons in<br>
         *  the mimetypes context are: 16, 22, 32, 48, 64 and 128</p>
         */
        public static final String SIZE = "--size";
    }

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * <p>Installs the icon file indicated by icon-file to the<br>
     *  desktop icon system under the name icon-name. Icon names<br>
     *  do not have an extension. If icon-name is not provided<br>
     *  the name is derived from icon-file. The icon file must<br>
     *  have .png or .xpm as extension. If a corresponding .icon<br>
     *  file exists in the same location as icon-file it will be<br>
     *  installed as well.</p>
     *
     * @see Option#NOUPDATE
     * @see Option#NOVENDOR
     * @see Option#THEME
     * @see Option#CONTEXT
     * @see Option#MODE
     * @see Option#SIZE
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param noupdate whether no update
     * @param novendor whether no vendor
     * @param theme the icon theme
     * @param context the icon context
     * @param mode the installation mode
     * @param size the icon size
     * @param iconFile the icon file
     * @param iconName the icon name
     * @return the exit code
     */
    public static int install(StringBuilder output, Boolean noupdate, Boolean novendor,
                                 String theme, String context, String mode, int size, String iconFile, String iconName) {
        ArrayList<String> args = new ArrayList<>(14);
        args.add(getScriptPath());
        args.add(INSTALL);
        args.add(notNullBoolean(noupdate) ? NOUPDATE : "");
        args.add(notNullBoolean(novendor) ? NOVENDOR : "");
        args.add(isEmpty(theme) ? "" : THEME);
        args.add(isEmpty(theme) ? "" : theme);
        args.add(isEmpty(context) ? "" : CONTEXT);
        args.add(isEmpty(context) ? "" : context);
        args.add(isEmpty(mode) ? "" : MODE);
        args.add(isEmpty(mode) ? "" : mode);
        args.add(SIZE);
        args.add(Integer.toString(size));
        args.add(isEmpty(iconFile) ? "" : iconFile);
        args.add(isEmpty(iconName) ? "" : iconName);
        return XDGUtils.process(output, args);
    }

    /**
     * <p>Removes the icon indicated by icon-name from the desktop<br>
     *  icon system. Note that icon names do not have an<br>
     *  extension.</p>
     *
     * @see Option#NOUPDATE
     * @see Option#THEME
     * @see Option#CONTEXT
     * @see Option#MODE
     * @see Option#SIZE
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param noupdate whether no update
     * @param theme the icon theme
     * @param context the icon context
     * @param mode the uninstallation mode
     * @param size the icon size
     * @param iconName the icon name
     * @return the exit code
     */
    public static int uninstall(StringBuilder output, Boolean noupdate, String theme, String context, String mode, int size, String iconName) {
        ArrayList<String> args = new ArrayList<>(12);
        args.add(getScriptPath());
        args.add(UNINSTALL);
        args.add(notNullBoolean(noupdate) ? NOUPDATE : "");
        args.add(isEmpty(theme) ? "" : THEME);
        args.add(isEmpty(theme) ? "" : theme);
        args.add(isEmpty(context) ? "" : CONTEXT);
        args.add(isEmpty(context) ? "" : context);
        args.add(isEmpty(mode) ? "" : MODE);
        args.add(isEmpty(mode) ? "" : mode);
        args.add(SIZE);
        args.add(Integer.toString(size));
        args.add(isEmpty(iconName) ? "" : iconName);
        return XDGUtils.process(output, args);
    }

    /**
     * <p>Force an update of the desktop icon system. This is only<br>
     *  useful if the last call to xdg-icon-resource included<br>
     *  the --noupdate option.</p>
     *
     * @see Option#THEME
     * @see Option#MODE
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param theme the icon theme
     * @param mode the update mode
     * @return the exit code
     */
    public static int forceupdate(StringBuilder output, String theme, String mode) {
        return XDGUtils.process(output, getScriptPath(), FORCEUPDATE, isEmpty(theme) ? "" : THEME, isEmpty(theme) ? "" : theme,
                isEmpty(mode) ? "" : MODE, isEmpty(mode) ? "" : mode);
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
