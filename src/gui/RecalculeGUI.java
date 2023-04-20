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

public class RecalculeGUI extends JFrame {

    private JPanel panel1;
    private JLabel oldNameLabel;
    private JPanel pp;
    private JButton selectFileButton;
    private JButton applyChangButton;
    private JLabel oL;
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
    File oldFile;
    String type;
    String company;
    String sequenceNumber;
    String comment;
    long sqNumber;
    String userPath = System.getProperty("user.home") + File.separator;
    File configFile;


    public RecalculeGUI() {
        sqNumber = 1;
        builtSequenceNumber(sqNumber);

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
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFiles();
            }
        });
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


    public void selectFiles(){
        choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retour = choix.showOpenDialog(RecalculeGUI.this);
        oldFile = choix.getSelectedFile();
        if (retour == JFileChooser.APPROVE_OPTION) {
            for (File f : oldFile.listFiles()) {
                if (f.isDirectory()) System.out.println("Dir :" + f.getName());
                else if (f.isFile()) System.out.println("File :" + f.getName());
            }
        }

//        if (retour == JFileChooser.APPROVE_OPTION) {
//            oldFileName = choix.getSelectedFile().getName();
//            extension = extractExtension(oldFileName);
//            parent = choix.getSelectedFile().getParent();
//            oldNameLabel.setText(" -" + oldFileName);
//
//        }
    }

    public void applyRenameFile() {

        File newF = new File(parent + "/" + newFileName + "." + extension);
        if (oldFile.renameTo(newF)) {
            JOptionPane.showMessageDialog(this, "The file was successfully renamed");

            sqNumber = sqNumber + 1;
            builtSequenceNumber(sqNumber);
            writeSequenceNumber(sqNumber);
            initApp();
        } else {
            JOptionPane.showMessageDialog(this, "Unable to rename file");
        }

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

    public void initApp() {
        oldFileName = "";
        oldNameLabel.setText("");
        newFileName = "";
        newFileNameLabel.setText("");
        applyChangButton.setEnabled(false);
    }

}
