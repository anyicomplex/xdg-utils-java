package com.anyicomplex.xdg.utils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.anyicomplex.xdg.utils.XDGEmail.Option.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

public class XDGEmail {

    private XDGEmail(){}

    public static final String FILE_NAME = "xdg-email";

    public static final class Option {
        private Option(){}
        public static final String UTF8 = "--utf8";
        public static final String CC = "--cc";
        public static final String BCC = "--bcc";
        public static final String SUBJECT = "--subject";
        public static final String BODY = "--body";
        public static final String ATTACH = "--attach";
    }

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

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

}
