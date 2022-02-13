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

    public static int suspend(StringBuilder output, long WindowID) {
        return XDGUtils.process(output, getScriptPath(), SUSPEND, "0x" + Long.toHexString(WindowID));
    }

    public static int resume(StringBuilder output, long WindowID) {
        return XDGUtils.process(output, getScriptPath(), RESUME, "0x" + Long.toHexString(WindowID));
    }

    public static int activate(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), ACTIVATE);
    }

    public static int lock(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), LOCK);
    }

    public static int reset(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), RESET);
    }

    public static int status(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), STATUS);
    }

}
