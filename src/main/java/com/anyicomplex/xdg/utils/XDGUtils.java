package com.anyicomplex.xdg.utils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.anyicomplex.xdg.utils.XDGUtils.ExitCode.WRAPPER_ERROR;

public class XDGUtils {

    private XDGUtils(){}

    public static final String SCRIPT_PATH_KEY = "com.anyicomplex.xdg.utils.scriptPath";
    public static final String SCRIPT_PATH_TAG = "xdg-utils-java";

    public static final String SCRIPT_VERSION = "1.1.3";

    public static class ExitCode {
        private ExitCode(){}
        public static final int SUCCESS = 0;
        public static final int SYNTAX_ERROR = 1;
        public static final int FINE_NOT_EXIST = 2;
        public static final int REQUIRED_TOOL_MISSING = 3;
        public static final int ACTION_FAILED = 4;
        public static final int PERMISSION_DENIED = 5;
        public static final int WRAPPER_ERROR = Integer.MIN_VALUE;
    }

    public static final class Mode {
        private Mode(){}
        public static final String USER = "user";
        public static final String SYSTEM = "system";
    }

    public static final int BUFFER_SIZE = 4096;
    public static final String LINE_FEED = "\n";

    private static final Random random = new Random();

    public static void load() {
        getScriptFile(XDGDesktopIcon.FILE_NAME);
        getScriptFile(XDGDesktopMenu.FILE_NAME);
        getScriptFile(XDGEmail.FILE_NAME);
        getScriptFile(XDGMime.FILE_NAME);
        getScriptFile(XDGOpen.FILE_NAME);
        getScriptFile(XDGScreenSaver.FILE_NAME);
        getScriptFile(XDGSettings.FILE_NAME);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() < 1;
    }

    public static boolean isEmpty(String[] strings) {
        if (strings == null) return true;
        boolean result = true;
        for (String string : strings) {
            result = result && isEmpty(string);
        }
        return result;
    }

    public static boolean notNullBoolean(Boolean bool) {
        return bool != null && bool;
    }

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

    public static int process(StringBuilder output, String... args) {
        try {
            return process(output, new ProcessBuilder().command(args).start());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WRAPPER_ERROR;
    }

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

    public static String getScriptDirPath() {
        return System.getProperty(SCRIPT_PATH_KEY);
    }

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
