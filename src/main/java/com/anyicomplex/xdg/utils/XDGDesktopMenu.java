package com.anyicomplex.xdg.utils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.anyicomplex.xdg.utils.XDGDesktopMenu.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGDesktopMenu.Option.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

public class XDGDesktopMenu {

    private XDGDesktopMenu(){}

    public static final String FILE_NAME = "xdg-desktop-menu";

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
        public static final String MODE = "--mode";
    }

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static String install(int[] exitCode, Boolean noupdate, Boolean novendor, String mode, String... directoryFilesAndDesktopFiles) {
        ArrayList<String> args = new ArrayList<>(6 + directoryFilesAndDesktopFiles.length);
        args.add(getScriptPath());
        args.add(INSTALL);
        args.add(notNullBoolean(noupdate) ? NOUPDATE : "");
        args.add(notNullBoolean(novendor) ? NOVENDOR : "");
        args.add(isEmpty(mode) ? "" : MODE);
        args.add(isEmpty(mode) ? "" : mode);
        args.addAll(Arrays.asList(directoryFilesAndDesktopFiles));
        return XDGUtils.process(exitCode, args);
    }

    public static String uninstall(int[] exitCode, Boolean noupdate, String mode, String... directoryFilesAndDesktopFiles) {
        ArrayList<String> args = new ArrayList<>(5 + directoryFilesAndDesktopFiles.length);
        args.add(getScriptPath());
        args.add(UNINSTALL);
        args.add(notNullBoolean(noupdate) ? NOUPDATE : "");
        args.add(isEmpty(mode) ? "" : MODE);
        args.add(isEmpty(mode) ? "" : mode);
        args.addAll(Arrays.asList(directoryFilesAndDesktopFiles));
        return XDGUtils.process(exitCode, args);
    }

    public static String forceupdate(int[] exitCode, String mode) {
        return XDGUtils.process(exitCode, getScriptPath(), FORCEUPDATE, isEmpty(mode) ? "" : MODE, isEmpty(mode) ? "" : mode);
    }

}
