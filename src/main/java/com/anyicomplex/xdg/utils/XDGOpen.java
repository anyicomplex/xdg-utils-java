package com.anyicomplex.xdg.utils;

public class XDGOpen {

    private XDGOpen(){}

    public static final String FILE_NAME = "xdg-open";

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static String process(int[] exitCode, String fileOrURL) {
        return XDGUtils.process(exitCode, getScriptPath(), fileOrURL);
    }

}
