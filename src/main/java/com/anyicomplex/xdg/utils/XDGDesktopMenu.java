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
import java.util.Arrays;

import static com.anyicomplex.xdg.utils.XDGDesktopMenu.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGDesktopMenu.Option.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

/**
 * <p>The xdg-desktop-menu program can be used to install new menu<br>
 *  entries to the desktop's application menu.</p>
 *<br>
 * <p> The application menu works according to the XDG Desktop Menu<br>
 *  Specification at<br>
 *  <a href="http://www.freedesktop.org/wiki/Specifications/menu-spec">http://www.freedesktop.org/wiki/Specifications/menu-spec</a></p>
 *<br>
 *  <p>Directory Files</p>
 *<br>
 * <p> The appearance of submenu in the application menu is provided<br>
 *  by a *.directory file. In particular it provides the title of<br>
 *  the submenu and a possible icon. A *.directory file consists of<br>
 *  a [Desktop Entry] header followed by several Key=Value lines.</p>
 *<br>
 * <p> A *.directory file can provide a title (name) for the submenu<br>
 *  in several different languages. This is done by adding a<br>
 *  language code as used by LC_MESSAGES in square brackets behind<br>
 *  the Key. This way one can specify different values for the same<br>
 *  Key depending on the currently selected language.</p>
 *<br>
 * <p> The following keys are relevant for submenus:</p>
 *<br>
 * <p> Type=Directory<br>
 *  This is a mandatory field that indicates that the<br>
 *  *.directory file describes a submenu.</p>
 *<br>
 * <p> Name=Menu Name<br>
 *  The title of submenu. For example Mozilla</p>
 *<br>
 * <p> Comment=Comment<br>
 *  Optional field to specify a tooltip for the submenu.</p>
 *<br>
 * <p> Icon=Icon File<br>
 *  The icon to use for the submenu. This can either be an<br>
 *  absolute path to an image file or an icon-name. If an<br>
 *  icon-name is provided an image lookup by name is done in<br>
 *  the user's current icon theme. The xdg-icon-resource<br>
 *  command can be used to install image files into icon<br>
 *  themes. The advantage of using an icon-name instead of<br>
 *  an absolute path is that with an icon-name the submenu<br>
 *  icon can be provided in several different sizes as well<br>
 *  as in several differently themed styles.</p>
 *<br>
 * <p>Environment Variables</p>
 *<br>
 * <p> xdg-desktop-menu honours the following environment variables:</p>
 *<br>
 * <p> XDG_UTILS_DEBUG_LEVEL<br>
 *  Setting this environment variable to a non-zero<br>
 *  numerical value makes xdg-desktop-menu do more verbose<br>
 *  reporting on stderr. Setting a higher value increases<br>
 *  the verbosity.</p>
 *<br>
 * <p> XDG_UTILS_INSTALL_MODE<br>
 *  This environment variable can be used by the user or<br>
 *  administrator to override the installation mode. Valid<br>
 *  values are user and system.<br>
 * </p>
 *
 * @see XDGDesktopIcon
 * @see XDGIconResource
 * @see XDGMime
 */
public final class XDGDesktopMenu {

    private XDGDesktopMenu(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-desktop-menu";

    /**
     * All available subcommands.
     */
    public static final class Subcommand {
        private Subcommand(){}
        /**
         * <p>Install one or more applications in a submenu of the<br>
         *  desktop menu system.</p>
         *
         * <p> desktop-file: A desktop file represents a single menu<br>
         *  entry in the menu. Desktop files are defined by the<br>
         *  freedesktop.org Desktop Entry Specification. The most<br>
         *  important aspects of *.desktop files are summarized<br>
         *  below.</p>
         *
         * <p> Menu entries can be added to the menu system in two<br>
         *  different ways. They can either be added to a predefined<br>
         *  submenu in the menu system based on one or more category<br>
         *  keywords, or they can be added to a new submenu.</p>
         *
         * <p> To add a menu entry to a predefined submenu the desktop<br>
         *  file that represents the menu entry must have a<br>
         *  Categories= entry that lists one or more keywords. The<br>
         *  menu item will be included in an appropriate submenu<br>
         *  based on the included keywords.</p>
         *
         * <p> To add menu items to a new submenu the desktop-files<br>
         *  must be preceded by a directory-file that describes the<br>
         *  submenu. If multiple desktop-files are specified, all<br>
         *  entries will be added to the same menu. If entries are<br>
         *  installed to a menu that has been created with a<br>
         *  previous call to xdg-desktop-menu the entries will be<br>
         *  installed in addition to any already existing entries.</p>
         *
         * <p> directory-file: The *.directory file indicated by<br>
         *  directory-file represents a submenu. The directory file<br>
         *  provides the name and icon for a submenu. The name of<br>
         *  the directory file is used to identify the submenu.</p>
         *
         * <p> If multiple directory files are provided each file will<br>
         *  represent a submenu within the menu that precedes it,<br>
         *  creating a nested menu hierarchy (sub-sub-menus). The<br>
         *  menu entries themselves will be added to the last<br>
         *  submenu.</p>
         *
         * <p> Directory files follow the syntax defined by the<br>
         *  freedesktop.org Desktop Entry Specification.</p>
         */
        public static final String INSTALL = "install";
        /**
         * <p>Remove applications or submenus from the desktop menu<br>
         *  system previously installed with xdg-desktop-menu<br>
         *  install.</p>
         *
         * <p> A submenu and the associated directory file is only<br>
         *  removed when the submenu no longer contains any menu<br>
         *  entries.</p>
         */
        public static final String UNINSTALL = "uninstall";
        /**
         * <p>Force an update of the menu system.</p>
         *
         * <p> This command is only useful if the last call to<br>
         *  xdg-desktop-menu included the --noupdate option.</p>
         */
        public static final String FORCEUPDATE = "forceupdate";
    }

    /**
     * All available options.
     */
    public static final class Option extends XDGUtils.Option {
        private Option(){}
        /**
         * <p>Postpone updating the menu system. If multiple updates<br>
         *  to the menu system are made in sequence this flag can be<br>
         *  used to indicate that additional changes will follow and<br>
         *  that it is not necessary to update the menu system right<br>
         *  away.</p>
         */
        public static final String NOUPDATE = "--noupdate";
        /**
         * <p>Normally, xdg-desktop-menu checks to ensure that any<br>
         *  *.directory and *.desktop files to be installed has a<br>
         *  vendor prefix. This option can be used to disable that<br>
         *  check.</p>
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
         * <p>mode can be user or system. In user mode the file is<br>
         *  (un)installed for the current user only. In system mode<br>
         *  the file is (un)installed for all users on the system.<br>
         *  Usually only root is allowed to install in system mode.</p>
         *<br>
         * <p> The default is to use system mode when called by root<br>
         *  and to use user mode when called by a non-root user.</p>
         */
        public static final String MODE = "--mode";
    }

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * <p>Install one or more applications in a submenu of the<br>
     *  desktop menu system.</p>
     *<br>
     * <p> desktop-file: A desktop file represents a single menu<br>
     *  entry in the menu. Desktop files are defined by the<br>
     *  freedesktop.org Desktop Entry Specification. The most<br>
     *  important aspects of *.desktop files are summarized<br>
     *  below.</p>
     *<br>
     * <p> Menu entries can be added to the menu system in two<br>
     *  different ways. They can either be added to a predefined<br>
     *  submenu in the menu system based on one or more category<br>
     *  keywords, or they can be added to a new submenu.</p>
     *<br>
     * <p> To add a menu entry to a predefined submenu the desktop<br>
     *  file that represents the menu entry must have a<br>
     *  Categories= entry that lists one or more keywords. The<br>
     *  menu item will be included in an appropriate submenu<br>
     *  based on the included keywords.</p>
     *<br>
     * <p> To add menu items to a new submenu the desktop-files<br>
     *  must be preceded by a directory-file that describes the<br>
     *  submenu. If multiple desktop-files are specified, all<br>
     *  entries will be added to the same menu. If entries are<br>
     *  installed to a menu that has been created with a<br>
     *  previous call to xdg-desktop-menu the entries will be<br>
     *  installed in addition to any already existing entries.</p>
     *<br>
     * <p> directory-file: The *.directory file indicated by<br>
     *  directory-file represents a submenu. The directory file<br>
     *  provides the name and icon for a submenu. The name of<br>
     *  the directory file is used to identify the submenu.</p>
     *<br>
     * <p> If multiple directory files are provided each file will<br>
     *  represent a submenu within the menu that precedes it,<br>
     *  creating a nested menu hierarchy (sub-sub-menus). The<br>
     *  menu entries themselves will be added to the last<br>
     *  submenu.</p>
     *<br>
     * <p> Directory files follow the syntax defined by the<br>
     *  freedesktop.org Desktop Entry Specification.</p>
     *
     * @see Option#NOUPDATE
     * @see Option#NOVENDOR
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param noupdate whether no update
     * @param novendor whether no vendor
     * @param mode the mode
     * @param directoryFilesAndDesktopFiles the *.directory files and *.desktop files
     * @return the exit code
     */
    public static int install(StringBuilder output, Boolean noupdate, Boolean novendor, String mode, String... directoryFilesAndDesktopFiles) {
        boolean isEmpty = isEmpty(directoryFilesAndDesktopFiles);
        ArrayList<String> args = new ArrayList<>(6 + (isEmpty ? 0 : directoryFilesAndDesktopFiles.length));
        args.add(getScriptPath());
        args.add(INSTALL);
        args.add(notNullBoolean(noupdate) ? NOUPDATE : "");
        args.add(notNullBoolean(novendor) ? NOVENDOR : "");
        args.add(isEmpty(mode) ? "" : MODE);
        args.add(isEmpty(mode) ? "" : mode);
        if (!isEmpty) args.addAll(Arrays.asList(directoryFilesAndDesktopFiles));
        return XDGUtils.process(output, args);
    }

    /**
     * <p>Remove applications or submenus from the desktop menu<br>
     *  system previously installed with xdg-desktop-menu<br>
     *  install.</p>
     *<br>
     * <p> A submenu and the associated directory file is only<br>
     *  removed when the submenu no longer contains any menu<br>
     *  entries.</p>
     *
     * @see Option#NOUPDATE
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param noupdate whether no update
     * @param mode the mode
     * @param directoryFilesAndDesktopFiles the *.directory files and *.desktop files
     * @return the exit code
     */
    public static int uninstall(StringBuilder output, Boolean noupdate, String mode, String... directoryFilesAndDesktopFiles) {
        boolean isEmpty = isEmpty(directoryFilesAndDesktopFiles);
        ArrayList<String> args = new ArrayList<>(5 + (isEmpty ? 0 : directoryFilesAndDesktopFiles.length));
        args.add(getScriptPath());
        args.add(UNINSTALL);
        args.add(notNullBoolean(noupdate) ? NOUPDATE : "");
        args.add(isEmpty(mode) ? "" : MODE);
        args.add(isEmpty(mode) ? "" : mode);
        if (!isEmpty) args.addAll(Arrays.asList(directoryFilesAndDesktopFiles));
        return XDGUtils.process(output, args);
    }

    /**
     * <p>Force an update of the menu system.</p>
     *<br>
     * <p> This command is only useful if the last call to<br>
     *  xdg-desktop-menu included the --noupdate option.</p>
     *
     * @see Option#NOUPDATE
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param mode the mode
     * @return the exit code
     */
    public static int forceupdate(StringBuilder output, String mode) {
        return XDGUtils.process(output, getScriptPath(), FORCEUPDATE, isEmpty(mode) ? "" : MODE, isEmpty(mode) ? "" : mode);
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
