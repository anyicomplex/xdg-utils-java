/*
 * MIT License
 *
 * Copyright (c) 2022 Yi An
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.anyicomplex.xdg.utils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.anyicomplex.xdg.utils.XDGUtils.ExitCode.WRAPPER_ERROR;

/**
 * <p>xdg-utils is a set of tools that allows applications to easily integrate with the desktop environment of the user, regardless of the specific desktop environment that the user runs.</p>
 *<br>
 * <p>About half of the tools focus on tasks commonly required during the installation of a desktop application and the other half focuses on integration with the desktop environment while the application is running.</p>
 */
public final class XDGUtils {

    private XDGUtils(){}

    /**
     * The key for get specific path from property to load script.
     */
    public static final String SCRIPT_PATH_KEY = "com.anyicomplex.xdg.utils.scriptPath";
    /**
     * The specific tag for extract scripts.
     */
    public static final String SCRIPT_PATH_TAG = "xdg-utils-java";

    /**
     * The xdg-utils scripts version.
     */
    public static final String SCRIPT_VERSION = "1.1.3";

    /**
     * <p>An exit code of 0 indicates success while a non-zero exit code<br>
     *  indicates failure. The following failure codes can be returned:</p>
     */
    public static class ExitCode {
        private ExitCode(){}
        /**
         * The operation succeed.
         */
        public static final int SUCCESS = 0;
        /**
         * Error in command line syntax.
         */
        public static final int SYNTAX_ERROR = 1;
        /**
         * <p>One of the files passed on the command line did not<br>
         *  exist.</p>
         */
        public static final int FINE_NOT_EXIST = 2;
        /**
         * A required tool could not be found.
         */
        public static final int REQUIRED_TOOL_MISSING = 3;
        /**
         * The action failed.
         */
        public static final int ACTION_FAILED = 4;
        /**
         * <p>No permission to read one of the files passed on the<br>
         *  command line.</p>
         */
        public static final int PERMISSION_DENIED = 5;
        /**
         * An error occurred in the Java wrapper.
         */
        public static final int WRAPPER_ERROR = Integer.MIN_VALUE;
    }

    /**
     * <p>mode can be user or system. In user mode the file is<br>
     *  (un)installed for the current user only. In system mode<br>
     *  the file is (un)installed for all users on the system.<br>
     *  Usually only root is allowed to install in system mode.</p>
     *
     * <p> The default is to use system mode when called by root<br>
     *  and to use user mode when called by a non-root user.</p>
     */
    public static final class Mode {
        private Mode(){}
        /**
         * The non-root user mode.
         */
        public static final String USER = "user";
        /**
         * The system mode.
         */
        public static final String SYSTEM = "system";
    }

    static class Option {
        /**
         * Output command synopsis.
         */
        public static final String HELP = "--help";
        /**
         * Output manual page.
         */
        public static final String MANUAL = "--manual";
        /**
         * Output the version information.
         */
        public static final String VERSION = "--version";
    }

    public static final int BUFFER_SIZE = 4096;
    public static final String LINE_FEED = "\n";

    private static final Random random = new Random();

    /**
     * Load all script files.
     */
    public static void load() {
        getScriptFile(XDGDesktopIcon.FILE_NAME);
        getScriptFile(XDGDesktopMenu.FILE_NAME);
        getScriptFile(XDGEmail.FILE_NAME);
        getScriptFile(XDGMime.FILE_NAME);
        getScriptFile(XDGOpen.FILE_NAME);
        getScriptFile(XDGScreenSaver.FILE_NAME);
        getScriptFile(XDGSettings.FILE_NAME);
    }

    /**
     * Check whether string is empty.
     * @param string the string
     * @return whether string is empty
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() < 1;
    }

    /**
     * Check whether string array is empty
     * @param strings the string array
     * @return whether string array is empty
     */
    public static boolean isEmpty(String[] strings) {
        if (strings == null) return true;
        boolean result = true;
        for (String string : strings) {
            result = result && isEmpty(string);
        }
        return result;
    }

    /**
     * Ensure Boolean not null.
     * @param bool the Boolean object
     * @return if bool is null, false, otherwise bool's value
     */
    public static boolean notNullBoolean(Boolean bool) {
        return bool != null && bool;
    }

    /**
     * Execute the specific process.
     *
     * @see ExitCode
     *
     * @param output the output buffer
     * @param process the process to be executed
     * @return the exit code
     */
    public static int process(StringBuilder output, Process process) {
        try {
            if (output != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) output.append(line).append(LINE_FEED);
                reader.close();
                output.delete(output.length() - LINE_FEED.length(), output.length());
            }
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return WRAPPER_ERROR;
    }

    /**
     * Execute the specific process.
     *
     * @see ExitCode
     *
     * @param output the output buffer
     * @param args the process args
     * @return the exit code
     */
    public static int process(StringBuilder output, String... args) {
        try {
            return process(output, new ProcessBuilder().command(args).start());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WRAPPER_ERROR;
    }

    /**
     * Execute the specific process.
     *
     * @see ExitCode
     *
     * @param output the output buffer
     * @param args the process args
     * @return the exit code
     */
    public static int process(StringBuilder output, List<String> args) {
        try {
            return process(output, new ProcessBuilder().command(args).start());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WRAPPER_ERROR;
    }

    private static String randomUUID () {
        return new UUID(random.nextLong(), random.nextLong()).toString();
    }

    /**
     * Gets the specific path of loading scripts.
     * @return the script dir path
     */
    public static String getScriptDirPath() {
        return System.getProperty(SCRIPT_PATH_KEY);
    }

    /**
     * Sets the specific path to load scripts.
     * @param path the script dir path
     */
    public static void setScriptDirPath(String path) {
        System.setProperty(SCRIPT_PATH_KEY, path);
    }

    private static String sha512(InputStream input) {
        if (input == null) throw new IllegalArgumentException("input cannot be null.");
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) != -1) messageDigest.update(buffer, 0, length);
            input.close();
            return byteArray2HexString(messageDigest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String byteArray2HexString(byte[] input) {
        StringBuilder hex = new StringBuilder();
        for (byte b : input) {
            hex.append(String.format("%02X", b));
        }
        while (hex.length() < 32) hex.insert(0, '0');
        return hex.toString();
    }

    private static InputStream readResource(String name) {
        if (!name.startsWith("/")) name = "/" + name;
        InputStream input = XDGUtils.class.getResourceAsStream(name);
        if (input == null) throw new IllegalStateException("Unable to read resource file: " + name);
        return input;
    }

    private static InputStream readFile(String pathname) {
        try {
            File file = new File(pathname);
            if (!file.canRead()) return null;
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void writeFile(InputStream input, File file) {
        synchronized (XDGUtils.class) {
            try {
                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) != -1) {
                    output.write(buffer, 0, length);
                }
                input.close();
                output.close();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to extract script file: " + file.getName(), e);
            }
        }
    }

    public static File getScriptFile(String name) {
        File result;
        String path = getScriptDirPath();
        if (path == null) {
            try {
                String resourceSha512 = sha512(readResource(name));
                result = getScriptFileWithVersion(name);
                if (result == null) throw new IllegalStateException("Script extraction path is invalid!");
                if (!result.exists()) writeFile(readResource(name), result);
                String fileSha512 = sha512(readFile(result.getAbsolutePath()));
                if (!resourceSha512.equals(fileSha512)) {
                    writeFile(readResource(name), result);
                }
            }
            catch (RuntimeException e) {
                File file = getScriptFileWithLibraryPath(name);
                if (file != null) return file;
                throw e;
            }
        }
        else result = getScriptFileWithProperty(name);
        if (result == null) result = getScriptFileWithLibraryPath(name);
        return result;
    }

    private static File getScriptFileWithVersion(String name) {
        File result = new File(System.getProperty("java.io.tmpdir") + "/" + SCRIPT_PATH_TAG + "/"+ System.getProperty("user.name") + "/" + SCRIPT_VERSION, name);
        if (!valid(result)) result = new File(System.getProperty("user.home") + "/." + SCRIPT_PATH_TAG + "/" + SCRIPT_VERSION, name);
        if (!valid(result)) result = new File(".tmp/" + SCRIPT_PATH_TAG + "/" + SCRIPT_VERSION, name);
        return valid(result) ? result : null;
    }

    private static File getScriptFileWithProperty(String name) {
        File result = new File(getScriptDirPath(), name);
        return valid(result) ? result : null;
    }

    private static File getScriptFileWithLibraryPath(String name) {
        File result = new File(System.getProperty("java.library.path"), name);
        return valid(result) ? result : null;
    }

    private static boolean valid(File file) {
        if (file.exists()) {
            return canWrite(file) && canExecute(file);
        }
        else {
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) return false;
            if (!parent.isDirectory()) return false;
            File testFile = new File(parent, randomUUID());
            try {
                if (!testFile.createNewFile()) return false;
                return canWrite(testFile) && canExecute(testFile);
            } catch (IOException ignored) {
                return false;
            }
            finally {
                testFile.delete();
            }
        }
    }

    private static boolean canWrite(File file) {
        if (file == null) return false;
        try {
            Method canWrite = File.class.getMethod("canWrite");
            if ((boolean) canWrite.invoke(file)) return true;
            Method setWritable = File.class.getMethod("setWritable", boolean.class, boolean.class);
            setWritable.invoke(file, true, false);
            return (boolean) canWrite.invoke(file);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {}
        return false;
    }

    private static boolean canExecute(File file) {
        if (file == null) return false;
        try {
            Method canExecute = File.class.getMethod("canExecute");
            if ((boolean) canExecute.invoke(file)) return true;
            Method setExecutable = File.class.getMethod("setExecutable", boolean.class, boolean.class);
            setExecutable.invoke(file, true, false);
            return (boolean) canExecute.invoke(file);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {}
        return false;
    }

}
