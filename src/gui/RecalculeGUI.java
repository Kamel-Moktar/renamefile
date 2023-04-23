package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RecalculeGUI extends JDialog {

    private JPanel panel1;
    private JLabel oldNameLabel;
    private JButton selectFileButton;
    private JButton applyChangButton;
    private JComboBox typeCompbobox;
    private JLabel typeDoc;
    private JComboBox companyCombobox;
    private JPanel panel2;
    private JLabel companyLabel;
    private JTextField commentTextField;
    private JTextField sequenseTextField;
    private JLabel newFileNameLabel;
    JFileChooser choix = new JFileChooser();
    String oldFileName;
    String newFileName;
    String parent;
    String extension;
    File [] oldFiles;
    String type;
    String company;
    String sequenceNumber;
    String comment;
    long sqNumber;
    String userPath = System.getProperty("user.home") + File.separator;
    File configFile;


    public RecalculeGUI(JFrame jFrame,File[] files) {
        super(jFrame);
        setModal(true);
         oldFiles=files;
        sqNumber = 1;
        builtSequenceNumber(sqNumber);
         setLocation(jFrame.getLocation().x+300,jFrame.getLocation().y);
         setSize(500,500);
        configFile = new File(userPath + "/rename-file-config");
        if (!configFile.isFile()) {
            try {
                if (configFile.createNewFile())
                    writeSequenceNumber(sqNumber);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else readSequenceNumber();


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(panel1);

        typeCompbobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                builtName();
            }
        });
        companyCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                builtName();
            }
        });


        commentTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                builtName();
            }
        });
        sequenseTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                builtName();
            }
        });
        applyChangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyRenameFile();
            }
        });
    }

    public String extractExtension(String name) {
        int pointPosition = name.indexOf('.');
        int longName = name.length();
        return name.substring(pointPosition + 1, longName);
    }






    public void applyRenameFile() {
       for (File f:oldFiles) {
           if(!f.isDirectory() ){
           extension = extractExtension(f.getName());
           parent = f.getParent();
//           System.out.println(f.getName());
           File newF = new File(parent + "/" + newFileName + "." + extension);
//           System.out.println(newF.getName());
           if (f.renameTo(newF)) {

               sqNumber = sqNumber + 1;
               builtSequenceNumber(sqNumber);
               writeSequenceNumber(sqNumber);
               builtName();
           } else {
               JOptionPane.showMessageDialog(this, "Unable to rename file");
           }
           }
       }
        this.setVisible(false);
//        JOptionPane.showMessageDialog(this, "The file was successfully renamed");

    }

    public void builtName() {

        type = typeCompbobox.getSelectedItem().toString();
        company = companyCombobox.getSelectedItem().toString();
        sequenceNumber = sequenseTextField.getText();
        comment = commentTextField.getText();
        newFileName = type + "-" + company + "-" + sequenceNumber;
        if (!comment.equals("")) newFileName += "(" + comment + ")";
        newFileNameLabel.setText(newFileName);
        applyChangButton.setEnabled(true);

    }

    public void builtSequenceNumber(long number) {

        if (number < 10) sequenceNumber = "000000" + number;
        else if (number < 100) sequenceNumber = "00000" + number;
        else if (number < 1000) sequenceNumber = "0000" + number;
        else if (number < 10000) sequenceNumber = "000" + number;
        else if (number < 100000) sequenceNumber = "00" + number;
        else if (number < 1000000) sequenceNumber = "0" + number;
        else sequenceNumber = "" + number;

        sequenseTextField.setText(sequenceNumber);
    }

    public void readSequenceNumber() {

        try {
            Scanner obj = new Scanner(configFile);
            String line = "0";
            if (obj.hasNextLine()) line = obj.nextLine();
            sqNumber = Long.parseLong(line);
            builtSequenceNumber(sqNumber);
            obj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeSequenceNumber(long sn) {

        try {
            configFile.delete();
            FileWriter fw = new FileWriter(configFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("" + sn);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
