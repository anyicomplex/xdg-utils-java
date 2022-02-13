package com.anyicomplex.xdg.utils;

import static com.anyicomplex.xdg.utils.XDGDesktopIcon.Option.*;
import static com.anyicomplex.xdg.utils.XDGDesktopIcon.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

public class XDGDesktopIcon {

    private XDGDesktopIcon(){}

    public static final String FILE_NAME = "xdg-desktop-icon";

    public static final class Subcommand {
        private Subcommand(){}
        public static final String INSTALL = "install";
        public static final String UNINSTALL = "uninstall";
    }

    public static final class Option {
        private Option(){}
        public static final String NOVENDOR = "--novendor";
    }

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static int install(StringBuilder output, Boolean novendor, String FILE) {
        return XDGUtils.process(output, getScriptPath(), INSTALL, notNullBoolean(novendor) ? NOVENDOR : "", FILE);
    }

    public static int uninstall(StringBuilder output, String FILE) {
        return XDGUtils.process(output, getScriptPath(), UNINSTALL, FILE);
    }

}
