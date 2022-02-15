package com.anyicomplex.xdg.utils;

import static com.anyicomplex.xdg.utils.XDGSettings.Option.*;
import static com.anyicomplex.xdg.utils.XDGSettings.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;

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

    public static int get(StringBuilder output, String property) {
        return XDGUtils.process(output, getScriptPath(), GET, isEmpty(property) ? "" : property);
    }

    public static int check(StringBuilder output, String property, String subproperty) {
        return XDGUtils.process(output, getScriptPath(), CHECK, isEmpty(property) ? "" : property, isEmpty(subproperty) ? "" : subproperty);
    }

    public static int set(StringBuilder output, String property, String subproperty, String value) {
        return XDGUtils.process(output, getScriptPath(), SET, isEmpty(property) ? "" : property,
                isEmpty(subproperty) ? "" : subproperty, isEmpty(value) ? "" : value);
    }

    public static int list(StringBuilder output) {
        return XDGUtils.process(output, getScriptPath(), LIST);
    }

}
