package com.anyicomplex.xdg.utils;

import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;

public class XDGOpen {

    private XDGOpen(){}

    public static final String FILE_NAME = "xdg-open";

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static int process(StringBuilder output, String fileOrURL) {
        return XDGUtils.process(output, getScriptPath(), isEmpty(fileOrURL) ? "" : fileOrURL);
    }

}
