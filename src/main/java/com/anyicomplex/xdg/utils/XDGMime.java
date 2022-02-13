package com.anyicomplex.xdg.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.anyicomplex.xdg.utils.XDGMime.Option.*;
import static com.anyicomplex.xdg.utils.XDGMime.Subcommand.*;
import static com.anyicomplex.xdg.utils.XDGUtils.isEmpty;
import static com.anyicomplex.xdg.utils.XDGUtils.notNullBoolean;

public class XDGMime {

    private XDGMime(){}

    public static final String FILE_NAME = "xdg-mime";

    public static final class Subcommand {
        private Subcommand(){}
        public static final String QUERY = "query";
        public static final String DEFAULT = "default";
        public static final String INSTALL = "install";
        public static final String UNINSTALL = "uninstall";
    }

    public static final class Option {
        private Option(){}
        public static final String MODE = "--mode";
        public static final String NOVENDOR = "--novendor";
    }

    public static String getScriptPath() {
        return XDGUtils.getScriptFile(FILE_NAME).getAbsolutePath();
    }

    public static int query(StringBuilder output, String... filetypeOrDefault) {
        List<String> args = new ArrayList<>(2 + filetypeOrDefault.length);
        args.add(getScriptPath());
        args.add(QUERY);
        args.addAll(Arrays.asList(filetypeOrDefault));
        return XDGUtils.process(output, args);
    }

    public static int defaultApplication(StringBuilder output, String... mimetypes) {
        List<String> args = new ArrayList<>(2 + mimetypes.length);
        args.add(getScriptPath());
        args.add(DEFAULT);
        args.addAll(Arrays.asList(mimetypes));
        return XDGUtils.process(output, args);
    }

    public static int install(StringBuilder output, String mode, Boolean novendor, String mimetypesFile) {
        return XDGUtils.process(output, getScriptPath(), INSTALL, notNullBoolean(novendor) ? NOVENDOR : "", isEmpty(mode) ? "" : MODE,
                isEmpty(mode) ? "" : mode, mimetypesFile);
    }

    public static int uninstall(StringBuilder output, String mode, String mimetypesFile) {
        return XDGUtils.process(output, getScriptPath(), UNINSTALL, isEmpty(mode) ? "" : MODE,
                isEmpty(mode) ? "" : mode, mimetypesFile);
    }

}
