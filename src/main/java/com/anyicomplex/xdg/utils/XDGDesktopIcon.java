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

    public static String install(int[] exitCode, Boolean novendor, String FILE) {
        return XDGUtils.process(exitCode, getScriptPath(), INSTALL, notNullBoolean(novendor) ? NOVENDOR : "", FILE);
    }

    public static String uninstall(int[] exitCode, String FILE) {
        return XDGUtils.process(exitCode, getScriptPath(), UNINSTALL, FILE);
    }

}
