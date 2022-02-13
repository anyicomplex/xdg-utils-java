package com.anyicomplex.xdg.utils;

import static com.anyicomplex.xdg.utils.XDGScreenSaver.Subcommand.*;

public class XDGScreenSaver {

    private XDGScreenSaver(){}

    public static final String FILE_NAME = "xdg-open";

    public static final class Subcommand {
        private Subcommand(){}
        public static final String SUSPEND = "suspend";
        public static final String RESUME = "resume";
        public static final String ACTIVATE = "activate";
        public static final String LOCK = "lock";
        public static final String RESET = "reset";
        public static final String STATUS = "status";
    }

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static String suspend(int[] exitCode, long WindowID) {
        return XDGUtils.process(exitCode, getScriptPath(), SUSPEND, "0x" + Long.toHexString(WindowID));
    }

    public static String resume(int[] exitCode, long WindowID) {
        return XDGUtils.process(exitCode, getScriptPath(), RESUME, "0x" + Long.toHexString(WindowID));
    }

    public static String activate(int[] exitCode) {
        return XDGUtils.process(exitCode, getScriptPath(), ACTIVATE);
    }

    public static String lock(int[] exitCode) {
        return XDGUtils.process(exitCode, getScriptPath(), LOCK);
    }

    public static String reset(int[] exitCode) {
        return XDGUtils.process(exitCode, getScriptPath(), RESET);
    }

    public static String status(int[] exitCode) {
        return XDGUtils.process(exitCode, getScriptPath(), STATUS);
    }

}
