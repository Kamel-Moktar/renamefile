package gui;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
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


    private JLabel newFileNameLabel;
    private JFormattedTextField dateDocText;
    private JTextField numberText;
    private JLabel dateDocLabel;
    private JLabel structurePosteLbel;
    private JLabel numberLabel;
    private JLabel objetLabel;
    private JTextField objectText;
    private JTextField structurePostText;
    JFileChooser choix = new JFileChooser();
    String oldFileName;
    String newFileName;
    String parent;
    String extension;
    File [] oldFiles;
    String type;
    String company;
    String sequenceNumber;

    long sqNumber;
    String userPath = System.getProperty("user.home") + File.separator;
    File configFile;
    String[][] typeDocument=new String[27][2];


    public RecalculeGUI(JFrame jFrame,File[] files) {
        super(jFrame);
        setModal(true);
        initTypeDocument();
        oldFiles=files;
        sqNumber = 1;

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


        structurePostText.addKeyListener(new KeyAdapter() {
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
        dateDocText.addKeyListener(new KeyAdapter() {
        });
        dateDocText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                builtName();
            }
        });
        dateDocText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                builtName();
            }
        });
        structurePostText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                builtName();
            }
        });
        numberText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                builtName();
            }
        });
        objectText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                builtName();
            }
        });
    }


    public void initTypeDocument() {


        typeDocument[0][0] ="STAT-ENT";
        typeDocument[1][0] ="DCS";
        typeDocument[2][0] ="FP";
        typeDocument[3][0] ="LOI";
        typeDocument[4][0] ="DEC";
        typeDocument[5][0] ="AR";
        typeDocument[6][0] ="OR";
        typeDocument[7][0] ="IM";
        typeDocument[8][0] ="DIR";
        typeDocument[9][0] ="CIR";
        typeDocument[10][0] ="NS";
        typeDocument[11][0] ="SM";
        typeDocument[12][0] ="EST";
        typeDocument[13][0] ="PRO";
        typeDocument[14][0] ="MO";
        typeDocument[15][0] ="SE";
        typeDocument[16][0] ="MAN";
        typeDocument[17][0] ="PSM";
        typeDocument[18][0] ="SENS";
        typeDocument[19][0] ="FTF";
        typeDocument[20][0] ="ETUDE";
        typeDocument[21][0] ="RI";
        typeDocument[22][0] ="RE";
        typeDocument[23][0] ="OUVR";
        typeDocument[24][0] ="PHOTO";
        typeDocument[25][0] ="AUDIO";
        typeDocument[26][0] ="VIDEO";

        typeDocument[0][1] ="Statut de l’entreprise";
        typeDocument[1][1] = "Décisions de création de structure";
        typeDocument[2][1] ="Fiches de poste RH";
        typeDocument[3][1] ="Loi";
        typeDocument[4][1] ="Décret";
        typeDocument[5][1] ="Arrêté";
        typeDocument[6][1] ="Ordonnance";
        typeDocument[7][1] ="Instruction ministérielle";
        typeDocument[8][1] ="Directive";
        typeDocument[9][1] ="Circulaire";
        typeDocument[10][1] ="Note de service";
        typeDocument[11][1] ="Système de management";
        typeDocument[12][1] ="Exigences et spécification technique";
        typeDocument[13][1] ="Procédure";
        typeDocument[14][1] ="Mode opératoire";
        typeDocument[15][1] = "Support d’enregistrement";
        typeDocument[16][1] ="Manuels";
        typeDocument[17][1] ="Protocole de surveillance médicale";
        typeDocument[18][1] ="Sensibilisation";
        typeDocument[19][1] = "Fiches techniques des formations";
        typeDocument[20][1] ="Etudes";
        typeDocument[21][1] ="Revue Interne";
        typeDocument[22][1] ="Revue Externe";
        typeDocument[23][1] ="Ouvrage";
        typeDocument[24][1] ="Photos";
        typeDocument[25][1] ="Vidéo";
        typeDocument[26][1] ="Audio";






    }


    public String getABTypeFile(String tf){

        for(String[] t:typeDocument){
            if(t[1].equals(tf)) return t[0];
        }


        return "aa";
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

               writeSequenceNumber(sqNumber);
               builtName();
           } else {
               JOptionPane.showMessageDialog(this, "Unable to rename file");
           }
           }
       }
        this.setVisible(false);


    }

    public void builtName() {
        type =getABTypeFile( typeCompbobox.getSelectedItem().toString());
        if(type.equals("STAT-ENT")) {

            company = companyCombobox.getSelectedItem().toString();
            String dDoc = dateDocText.getText();
            newFileName = type + "-" + company + "-" + dDoc;
        }

        if(type.equals("DCS")) {

            company = Objects.requireNonNull(companyCombobox.getSelectedItem()).toString();
            String structure = structurePostText.getText();
            String numero = numberText.getText();
            String dDoc = dateDocText.getText();
            newFileName = type + "-" + company + "-"+structure + "-"+numero+"-" + dDoc;
        }
        if(type.equals("FP")) {

            company = Objects.requireNonNull(companyCombobox.getSelectedItem()).toString();
            String structure = structurePostText.getText();

            String dDoc = dateDocText.getText();
            newFileName = type + "-" + company + "-"+structure + "-" + dDoc;
        }


        if(type.equals("LOI")) {

            company = Objects.requireNonNull(companyCombobox.getSelectedItem()).toString();
            String objet = objectText.getText();
            String numero = numberText.getText();
            String dDoc = dateDocText.getText();
            newFileName = type + "-"+numero+  "-"+dDoc +"-"+objet;
        }
        if(type.equals("DEC")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("AR")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }


        if(type.equals("OR")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        if(type.equals("IM")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("DIR")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("CIR")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("NS")) {
            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("SM")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("EST")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("PRO")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("MO")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("SE")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("MAN")) {
            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("PSM")) {
            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("SENS")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("FTF")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("DIR")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("RI")) {
            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("RE")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("OUVR")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }



        if(type.equals("PHOTO")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("VIDEO")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(type.equals("AUDIO")) {

            JOptionPane.showMessageDialog(this, "En cour de construction ",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }






        newFileNameLabel.setText(newFileName);
        applyChangButton.setEnabled(true);

    }



    public void readSequenceNumber() {

        try {
            Scanner obj = new Scanner(configFile);
            String line = "0";
            if (obj.hasNextLine()) line = obj.nextLine();
            sqNumber = Long.parseLong(line);

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


    private void createUIComponents() {
        DateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter displayFormatter = new DateFormatter(displayFormat);
        DateFormat editFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormatter editFormatter = new DateFormatter(editFormat);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter,
                displayFormatter, editFormatter);

        dateDocText = new JFormattedTextField(factory, new Date());

    }
}
