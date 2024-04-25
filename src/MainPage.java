import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainPage {
    private JFrame frame;
    private JTextField messageField;
    //added next two lines  but it didn't help
    private JButton encryptButton;
    private JButton decryptButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                    MainPage window = new MainPage();
                    window.frame.setVisible(true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public MainPage(){
        initialize();
        addListeners();
    }

    private void initialize(){
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonsPanel = new JPanel();
        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setLayout(new GridLayout(0, 2, 0, 0));

        encryptButton = new JButton("Encrypt");
        buttonsPanel.add(encryptButton);

        decryptButton = new JButton("Decrypt");
        buttonsPanel.add(decryptButton);

        JPanel messagePanel = new JPanel();
        frame.getContentPane().add(messagePanel, BorderLayout.CENTER);
        messagePanel.setLayout(null);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        messageLabel.setBounds(40, 58, 72, 26);
        messagePanel.add(messageLabel);

        messageField = new JTextField();
        messageField.setBounds(115, 63, 301, 20);
        messagePanel.add(messageField);
        messageField.setColumns(10);
    }

    ActionListener encryptListener = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            File imageFile = FileChooser.MakeFileChooser();
            if(imageFile != null){
                EncryptLSB.Encrypt(imageFile, messageField.getText());
            }
        }
    };


    ActionListener decryptListener = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            DecryptLSB.Decrypt();
        }
    };
    //or instead of the whole block we da do this:
    //ActionListener decryptListener = e -> DecryptLSB.Decrypt();


    private void addListeners(){
        encryptButton.addActionListener(encryptListener);
        decryptButton.addActionListener(decryptListener);
    }

}
