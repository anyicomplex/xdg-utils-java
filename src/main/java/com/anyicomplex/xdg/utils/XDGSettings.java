package com.anyicomplex.xdg.utils;

import static com.anyicomplex.xdg.utils.XDGSettings.Option.*;
import static com.anyicomplex.xdg.utils.XDGSettings.Subcommand.*;

public class XDGSettings {

    private XDGSettings(){}

    public static final String FILE_NAME = "xdg-settings";

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static final class Subcommand {
        private Subcommand(){}
        public static final String GET = "get";
        public static final String CHECK = "check";
        public static final String SET = "set";
    }

    public static final class Option {
        private Option(){}
        public static final String LIST = "--list";
    }

    public static String get(int[] exitCode, String property) {
        return XDGUtils.process(exitCode, getScriptPath(), GET, property);
    }

    public static String check(int[] exitCode, String property, String subproperty) {
        return XDGUtils.process(exitCode, getScriptPath(), CHECK, property, subproperty);
    }

    public static String set(int[] exitCode, String property, String subproperty, String value) {
        return XDGUtils.process(exitCode, getScriptPath(), SET, property, subproperty, value);
    }

    public static String list(int[] exitCode) {
        return XDGUtils.process(exitCode, getScriptPath(), LIST);
    }

}
