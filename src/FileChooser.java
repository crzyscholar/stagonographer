import javax.swing.*;
import java.io.File;

public class FileChooser {
    public static File MakeFileChooser(){
        JFileChooser chooser = new JFileChooser();

        int option = chooser.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            return file;
        }
        return null;
    }

}
