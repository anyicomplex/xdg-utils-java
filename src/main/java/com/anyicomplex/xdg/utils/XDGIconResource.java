package com.anyicomplex.xdg.utils;

import java.util.ArrayList;

import static com.anyicomplex.xdg.utils.XDGIconResource.Option.*;
import static com.anyicomplex.xdg.utils.XDGIconResource.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

public class XDGIconResource {

    private XDGIconResource(){}

    public static final String FILE_NAME = "xdg-icon-resource";

    public static final class Subcommand {
        private Subcommand(){}
        public static final String INSTALL = "install";
        public static final String UNINSTALL = "uninstall";
        public static final String FORCEUPDATE = "forceupdate";
    }

    public static final class Option {
        private Option(){}
        public static final String NOUPDATE = "--noupdate";
        public static final String NOVENDOR = "--novendor";
        public static final String THEME = "--theme";
        public static final String CONTEXT = "--context";
        public static final String MODE = "--mode";
        public static final String SIZE = "--size";
    }

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

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
        args.add(iconFile);
        args.add(iconName);
        return XDGUtils.process(output, args);
    }

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
        args.add(iconName);
        return XDGUtils.process(output, args);
    }

    public static int forceupdate(StringBuilder output, String theme, String mode) {
        return XDGUtils.process(output, getScriptPath(), FORCEUPDATE, isEmpty(theme) ? "" : THEME, isEmpty(theme) ? "" : theme,
                isEmpty(mode) ? "" : MODE, isEmpty(mode) ? "" : mode);
    }

}
