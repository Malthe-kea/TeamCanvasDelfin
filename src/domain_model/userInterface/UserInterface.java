package domain_model.userInterface;

import database.DBController;
import domain_model.Controller;
import domain_model.UserInstance;
import domain_model.userInterface.MemberInterface.SuperUserInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    static ImageIcon icon = new ImageIcon("resources" + File.separator + "images" + File.separator + "swimclub.png");
    Controller controller;

    public UserInterface() {
        controller = new Controller();
    }


    public void startProgram() {
        boolean programRunning = true;
        smallWindow("Velkommen", TextStyle.format("(Velkommen) til Delfin svømmeklubben"), "Log ind");

        while (programRunning) {
            UserInstance userInstance = controller.getUserFromPassword(inputMenu("Log ind", "Indtast dit password"));

            switch (userInstance) {
                case MEMBER, COMPETITIVE -> {
                    //MEMBER INTERFACE
                }
                case TRAINER -> {
                    //TRAINER INTERFACE
                }
                case SUPER -> {
                    SuperUserInterface superUserInterface = new SuperUserInterface(controller);
                    superUserInterface.startMenu();
                }
                case TREASURER -> {
                    //TREASURER INTERFACE
                }
                case NOTFOUND -> {
                    JOptionPane.showMessageDialog(null, "Brugeren blev ikke fundet", "Fejl", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }



    public static void showList(String title, ArrayList<String> rows) {
        StringBuilder sb = new StringBuilder();
        for (String row : rows) {
            sb.append(row).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(800, 800));

        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.PLAIN_MESSAGE, icon);

    }


    public static int smallWindow(String title, String message, String buttonText) {
        return JOptionPane.showOptionDialog(null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                new String[]{buttonText},
                0);
    }


    public static String inputMenu(String title, String message) {
        return (String) JOptionPane.showInputDialog(null,
                TextStyle.format(message),
                title,
                JOptionPane.PLAIN_MESSAGE,
                icon, null, null);

    }

    public static boolean yesNoMenu(String title, String message) {
        int choice = JOptionPane.showConfirmDialog(null,
                TextStyle.format(message),
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon);

        return choice == 0;
    }


    public static int drawMenu(String title, String message, ArrayList<String> options) {

        //OPRETTELSE A SELVE VINDUET JDIALOG

        JDialog dialog = new JDialog(); //Opretter en JDialog. Dette er selve vinduet, hvor vores JLabel og JButtons placeres indeni.
        dialog.setTitle(title); //Sætter titlen af selve vinduet.
        dialog.setModal(true); //Om vinduet skal være Modal. Hvis det er modal kan der kun interageres med dette vindue. Sættes true for at undgå fejl med andre vinduer.
        dialog.setIconImage(icon.getImage()); //Sætter det lille ikon i hjørnet til vores delfin logo

        ///OPRETTELSE AF TITLE PANEL MED IKON OG OVERSKRIFT

        JLabel iconLabel = new JLabel(icon); //Laver et JLabel med Delfin logoet for at vise i menuen.

        JLabel titleLabel = new JLabel(message, SwingConstants.CENTER);  //Laver et JLabel med Menu overskriften for at vise i menuen. Sætter den til message og for at være i midten af selve JLabelen.
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15)); //Sætter fonten

        //Laver et JPanel som er vores title og ikon, så de er ved siden af hinanden. Gøres så vi kan gruppere og positionere dem sammen.
        JPanel northPanel = new JPanel();
        northPanel.add(iconLabel);
        northPanel.add(titleLabel);

        //OPRETTELSE AF KNAPPER OG TILFØJELSE TIL PANEL MED ALLE KNAPPER

        //Opretter JButton array med længden af options
        JButton[] buttons = new JButton[options.size()];
        final int[] chosenOption = {-1}; // Denne array bruges til at gemme den valgte menu mulighed. Skal være final for at kunne bruges i en ActionListener. Derfor en array da en array index kan ændres selvom den er final.

        //Loop som tilføjer knapper ud fra indsat options.
        for (int i = 0; i < options.size(); i++) {
            final int index = i; // Gemmer index i en final variabel for at kunne bruges i ActionListener.
            buttons[i] = new JButton(options.get(i)); //Laver en ny JButton med teksten fra options arrayet.
            buttons[i].addActionListener(e -> { //Tilføjer en actionslistener, som reagere når brugeren trykker på knappen.
                chosenOption[0] = index; //Sætter chosenOption til index, så vi kan gemme hvilken knap der er trykket på.
                dialog.dispose(); // Når en knap er trykket på lukkes vinduet.
            });
        }

        //Opretter et JPanel som indeholder vores knapper. Sætter layout til BoxLayout, så knapperne står under hinanden.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //Tilføjer knapperne til vores buttonPanel
        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Tilføjer et lille luftrum imellem knapperne.
        }


        //OPRETTELSE AF ET SAMLET PANEL DER INDEHOLDER ALLE VORES ELEMENTER

        //Opretter et JPanel som er vores hovedpanel. Indeholder vores JLabel title, vores JLabel icon og vores JButton panel.
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Sætter layout til BorderLayout. Blot en af flere typer layouts et vindue kan have.
        panel.setBorder(new EmptyBorder(10, 50, 10, 50)); // Tilføjer en tom kant rundt om vores panel, så der er luft imellem elementerne og siderne af vinduet.


        //Sætter vores northPanel og buttonPanel ind i vores hovedpanel. Northpanel øverst.
        panel.add(northPanel, BorderLayout.NORTH); // Add the icon and title to the north of the main panel
        if (buttons.length > 10) {
            JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
            buttonScrollPane.setPreferredSize(new Dimension(400, 400)); // Set the preferred size of the scroll pane
            panel.add(buttonScrollPane, BorderLayout.CENTER); // Add the button panel to the center of the main panel
        } else {
            panel.add(buttonPanel, BorderLayout.CENTER); // Add the button panel to the center of the main panel

        }

        JButton cancelButton = new JButton("Afslut");
        cancelButton.addActionListener(e -> {
            chosenOption[0] = -1;
            dialog.dispose();
        });
        panel.add(cancelButton, BorderLayout.SOUTH);

        //INDSÆTTELSE AF HOVEDPANEL I VINDUET OG VISNING AF VINDUET

        dialog.getContentPane().add(panel, BorderLayout.CENTER); //Sætter vores hovedpanel ind i vores dialog som er selve vinduet. Sætter indholdet i midten af vinduet.
        dialog.pack(); //Indbygget metode i JDialog der skalere vinduet så det passer til indholdet.
        dialog.setLocationRelativeTo(null); // Sætter vinduet til midten af skærmen.
        dialog.setVisible(true); //Viser vinduet.

        return chosenOption[0]; //Returnere den valgte menu mulighed.
    }

}
