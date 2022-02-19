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

import static com.anyicomplex.xdg.utils.XDGEmail.Option.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

/**
 * <p>xdg-email opens the user's preferred e-mail composer in order<br>
 *  to send a mail to address(es) or mailto-uri. RFC2368 defines<br>
 *  mailto: URIs. xdg-email limits support to, cc, subject and body<br>
 *  fields in mailto-uri, all other fields are silently ignored.<br>
 *  address(es) must follow the syntax of RFC822. Multiple<br>
 *  addresses may be provided as separate arguments.</p>
 *<br>
 * <p> All information provided on the command line is used to prefill<br>
 *  corresponding fields in the user's e-mail composer. The user<br>
 *  will have the opportunity to change any of this information<br>
 *  before actually sending the e-mail.</p>
 *<br>
 * <p> xdg-email is for use inside a desktop session only. It is not<br>
 *  recommended to use xdg-email as root.<br>
 * </p>
 *<br>
 * <p>Environment Variables</p>
 *<br>
 * <p> xdg-email honours the following environment variables:</p>
 *<br>
 * <p> XDG_UTILS_DEBUG_LEVEL<br>
 *  Setting this environment variable to a non-zero<br>
 *  numerical value makes xdg-email do more verbose<br>
 *  reporting on stderr. Setting a higher value increases<br>
 *  the verbosity.<br>
 * </p>
 *
 * @see XDGOpen
 * @see XDGMime
 */
public final class XDGEmail {

    private XDGEmail(){}

    /**
     * The script file name.
     */
    public static final String FILE_NAME = "xdg-email";

    /**
     * All available options.
     */
    public static final class Option extends XDGUtils.Option {
        private Option(){}
        /**
         * <p>Indicates that all command line options that follow are<br>
         *  in utf8. Without this option, command line options are<br>
         *  expected to be encoded according to locale. If the<br>
         *  locale already specifies utf8 this option has no effect.<br>
         *  This option does not affect mailto URIs that are passed<br>
         *  on the command line.</p>
         */
        public static final String UTF8 = "--utf8";
        /**
         * Specify a recipient to be copied on the e-mail.
         */
        public static final String CC = "--cc";
        /**
         * Specify a recipient to be blindly copied on the e-mail.
         */
        public static final String BCC = "--bcc";
        /**
         * Specify a subject for the e-mail.
         */
        public static final String SUBJECT = "--subject";
        /**
         * <p>Specify a body for the e-mail. Since the user will be<br>
         *  able to make changes before actually sending the e-mail,<br>
         *  this can be used to provide the user with a template for<br>
         *  the e-mail. text may contain linebreaks.</p>
         */
        public static final String BODY = "--body";
        /**
         * <p>Specify an attachment for the e-mail. file must point to<br>
         *  an existing file.</p>
         *<br>
         * <p> Some e-mail applications require the file to remain<br>
         *  present after xdg-email returns.</p>
         */
        public static final String ATTACH = "--attach";
    }

    /**
     * Gets the script path.
     * @return the script path
     */
    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    /**
     * Sending mail using the user's preferred e-mail composer.
     *
     * @see Option#UTF8
     * @see Option#CC
     * @see Option#BCC
     * @see Option#SUBJECT
     * @see Option#BODY
     * @see Option#ATTACH
     *
     * @see com.anyicomplex.xdg.utils.XDGUtils.ExitCode
     *
     * @param output the output buffer
     * @param utf8 whether encode string in utf8
     * @param ccAddress the cc address
     * @param bccAddress the bcc address
     * @param subjectText the subject text
     * @param bodyText the body text
     * @param attachFile the file will be attached
     * @param mailtoURIsOrAddresses the mailto URIs or addresses
     * @return the exit code
     */
    public static int process(StringBuilder output, Boolean utf8, String ccAddress, String bccAddress, String subjectText,
                                 String bodyText, String attachFile, String... mailtoURIsOrAddresses) {
        boolean isEmpty = isEmpty(mailtoURIsOrAddresses);
        ArrayList<String> args = new ArrayList<>(12 + (isEmpty ? 0 : mailtoURIsOrAddresses.length));
        args.add(getScriptPath());
        args.add(notNullBoolean(utf8) ? UTF8 : "");
        args.add(isEmpty(ccAddress) ? "" : CC);
        args.add(isEmpty(ccAddress) ? "" : ccAddress);
        args.add(isEmpty(bccAddress) ? "" : BCC);
        args.add(isEmpty(bccAddress) ? "" : bccAddress);
        args.add(isEmpty(subjectText) ? "" : SUBJECT);
        args.add(isEmpty(subjectText) ? "" : subjectText);
        args.add(isEmpty(bodyText) ? "" : BODY);
        args.add(isEmpty(bodyText) ? "" : bodyText);
        args.add(isEmpty(attachFile) ? "" : ATTACH);
        args.add(isEmpty(attachFile) ? "" : attachFile);
        if (!isEmpty) args.addAll(Arrays.asList(mailtoURIsOrAddresses));
        return XDGUtils.process(output, args);
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
