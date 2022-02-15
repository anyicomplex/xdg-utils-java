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
import java.util.List;

import static com.anyicomplex.xdg.utils.XDGMime.Option.*;
import static com.anyicomplex.xdg.utils.XDGMime.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

/**
 * <p>The xdg-mime program can be used to query information about<br>
 *  file types and to add descriptions for new file types.</p>
 *<br>
 *  <p>Environment Variables</p>
 *<br>
 * <p> xdg-mime honours the following environment variables:</p>
 *<br>
 * <p> XDG_UTILS_DEBUG_LEVEL<br>
 *  Setting this environment variable to a non-zero<br>
 *  numerical value makes xdg-mime do more verbose reporting<br>
 *  on stderr. Setting a higher value increases the<br>
 *  verbosity.</p>
 *<br>
 * <p> XDG_UTILS_INSTALL_MODE<br>
 *  This environment variable can be used by the user or<br>
 *  administrator to override the installation mode. Valid<br>
 *  values are user and system.</p>
 *
 * @see XDGIconResource
 * @see XDGDesktopMenu
 */
public final class XDGMime {

    private XDGMime(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-mime";

    /**
     * All available subcommands.
     */
    public static final class Subcommand {
        private Subcommand(){}

        /**
         * <p>Returns information related to file types.</p>
         *<br>
         * <p> The query option is for use inside a desktop session<br>
         *  only. It is not recommended to use xdg-mime query as<br>
         *  root.</p>
         *<br>
         * <p> The following queries are supported:</p>
         *<br>
         * <p> query filetype FILE: Returns the file type of FILE in<br>
         *  the form of a MIME type.</p>
         *<br>
         * <p> query default mimetype: Returns the default application<br>
         *  that the desktop environment uses for opening files of<br>
         *  type mimetype. The default application is identified by<br>
         *  its *.desktop file.</p>
         */
        public static final String QUERY = "query";
        /**
         * <p>Ask the desktop environment to make application the<br>
         *  default application for opening files of type mimetype.<br>
         *  An application can be made the default for several file<br>
         *  types by specifying multiple mimetypes.</p>
         *<br>
         * <p> application is the desktop file id of the application<br>
         *  and has the form vendor-name.desktop. application must<br>
         *  already be installed in the desktop menu before it can<br>
         *  be made the default handler. The application's desktop<br>
         *  file must list support for all the MIME types that it<br>
         *  wishes to be the default handler for.</p>
         *<br>
         * <p> Requests to make an application a default handler may be<br>
         *  subject to system policy or approval by the end-user.<br>
         *  xdg-mime query can be used to verify whether an<br>
         *  application is the actual default handler for a specific<br>
         *  file type.</p>
         *<br>
         * <p> The default option is for use inside a desktop session<br>
         *  only. It is not recommended to use xdg-mime default as<br>
         *  root.</p>
         */
        public static final String DEFAULT = "default";
        /**
         * <p>Adds the file type descriptions provided in<br>
         *  mimetypes-file to the desktop environment.<br>
         *  mimetypes-file must be a XML file that follows the<br>
         *  freedesktop.org Shared MIME-info Database specification<br>
         *  and that has a mime-info element as its document root.<br>
         *  For each new file type one or more icons with name<br>
         *  type-subtype must be installed with the<br>
         *  xdg-icon-resource command in the mimetypes context. For<br>
         *  example the filetype<br>
         *  application/vnd.oasis.opendocument.text requires an icon<br>
         *  named application-vnd.oasis.opendocument.text to be<br>
         *  installed (unless the file type recommends another icon<br>
         *  name).</p>
         */
        public static final String INSTALL = "install";
        /**
         * <p>Removes the file type descriptions provided in<br>
         *  mimetypes-file and previously added with xdg-mime<br>
         *  install from the desktop environment. mimetypes-file<br>
         *  must be a XML file that follows the freedesktop.org<br>
         *  Shared MIME-info Database specification and that has a<br>
         *  mime-info element as its document root.</p>
         */
        public static final String UNINSTALL = "uninstall";
    }

    /**
     * All available options.
     */
    public static final class Option {
        private Option(){}
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
         * <p>Normally, xdg-mime checks to ensure that the<br>
         *  mimetypes-file to be installed has a proper vendor<br>
         *  prefix. This option can be used to disable that check.</p>
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
     * <p>Returns information related to file types.</p>
     *<br>
     * <p> The query option is for use inside a desktop session<br>
     *  only. It is not recommended to use xdg-mime query as<br>
     *  root.</p>
     *<br>
     * <p> The following queries are supported:</p>
     *<br>
     * <p> query filetype FILE: Returns the file type of FILE in<br>
     *  the form of a MIME type.</p>
     *<br>
     * <p> query default mimetype: Returns the default application<br>
     *  that the desktop environment uses for opening files of<br>
     *  type mimetype. The default application is identified by<br>
     *  its *.desktop file.</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param filetypeOrDefault the file type or default to be queried
     * @return the exit code
     */
    public static int query(StringBuilder output, String... filetypeOrDefault) {
        boolean isEmpty = isEmpty(filetypeOrDefault);
        List<String> args = new ArrayList<>(2 + (isEmpty ? 0 : filetypeOrDefault.length));
        args.add(getScriptPath());
        args.add(QUERY);
        if (!isEmpty) args.addAll(Arrays.asList(filetypeOrDefault));
        return XDGUtils.process(output, args);
    }

    /**
     * <p>Ask the desktop environment to make application the<br>
     *  default application for opening files of type mimetype.<br>
     *  An application can be made the default for several file<br>
     *  types by specifying multiple mimetypes.</p>
     *<br>
     * <p> application is the desktop file id of the application<br>
     *  and has the form vendor-name.desktop. application must<br>
     *  already be installed in the desktop menu before it can<br>
     *  be made the default handler. The application's desktop<br>
     *  file must list support for all the MIME types that it<br>
     *  wishes to be the default handler for.</p>
     *<br>
     * <p> Requests to make an application a default handler may be<br>
     *  subject to system policy or approval by the end-user.<br>
     *  xdg-mime query can be used to verify whether an<br>
     *  application is the actual default handler for a specific<br>
     *  file type.</p>
     *<br>
     * <p> The default option is for use inside a desktop session<br>
     *  only. It is not recommended to use xdg-mime default as<br>
     *  root.</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param mimetypes the mimetypes file
     * @return the exit code
     */
    public static int defaultApplication(StringBuilder output, String... mimetypes) {
        boolean isEmpty = isEmpty(mimetypes);
        List<String> args = new ArrayList<>(2 + (isEmpty ? 0 : mimetypes.length));
        args.add(getScriptPath());
        args.add(DEFAULT);
        if (!isEmpty) args.addAll(Arrays.asList(mimetypes));
        return XDGUtils.process(output, args);
    }

    /**
     * <p>Adds the file type descriptions provided in<br>
     *  mimetypes-file to the desktop environment.<br>
     *  mimetypes-file must be a XML file that follows the<br>
     *  freedesktop.org Shared MIME-info Database specification<br>
     *  and that has a mime-info element as its document root.<br>
     *  For each new file type one or more icons with name<br>
     *  type-subtype must be installed with the<br>
     *  xdg-icon-resource command in the mimetypes context. For<br>
     *  example the filetype<br>
     *  application/vnd.oasis.opendocument.text requires an icon<br>
     *  named application-vnd.oasis.opendocument.text to be<br>
     *  installed (unless the file type recommends another icon<br>
     *  name).</p>
     *
     * @see Option#NOVENDOR
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param mode the uninstallation mode
     * @param novendor whether no vendor
     * @param mimetypesFile the mimetypes file
     * @return the exit code
     */
    public static int install(StringBuilder output, String mode, Boolean novendor, String mimetypesFile) {
        return XDGUtils.process(output, getScriptPath(), INSTALL, notNullBoolean(novendor) ? NOVENDOR : "", isEmpty(mode) ? "" : MODE,
                isEmpty(mode) ? "" : mode, isEmpty(mimetypesFile) ? "" : mimetypesFile);
    }

    /**
     * <p>Removes the file type descriptions provided in<br>
     *  mimetypes-file and previously added with xdg-mime<br>
     *  install from the desktop environment. mimetypes-file<br>
     *  must be a XML file that follows the freedesktop.org<br>
     *  Shared MIME-info Database specification and that has a<br>
     *  mime-info element as its document root.</p>
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.Mode
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param mode the uninstallation mode
     * @param mimetypesFile the mimetypes file
     * @return the exit code
     */
    public static int uninstall(StringBuilder output, String mode, String mimetypesFile) {
        return XDGUtils.process(output, getScriptPath(), UNINSTALL, isEmpty(mode) ? "" : MODE,
                isEmpty(mode) ? "" : mode, isEmpty(mimetypesFile) ? "" : mimetypesFile);
    }

}
