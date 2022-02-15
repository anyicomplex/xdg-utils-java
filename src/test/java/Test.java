import com.anyicomplex.xdg.utils.XDGOpen;
import com.anyicomplex.xdg.utils.XDGUtils;

public class Test {

    public static void main(String[] args) {
        // Set the specific path to load script.
        //XDGUtils.setScriptDirPath("/path/to/scripts"); // equal as System.setProperty("com.anyicomplex.xdg.utils.scriptPath", "/path/to/scripts");
        XDGUtils.load(); // Extract script files from jar
        System.out.println(XDGOpen.process(null, "https://github.com"));
    }

}
