package domain_model;

import database.DBController;
import domain_model.Processors.SuperUserProcessor;
import user_domain.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class UserInterfaceTest {

    ImageIcon icon = new ImageIcon("resources"+File.separator+"images"+File.separator+"swimclub.png");
    Controller controller;

    public UserInterfaceTest() {
        controller = new Controller();
    }



    public void startProgram() {
        System.out.println(welcomeMessage());
        controller.getProcessorFromUserPassword(loginMenu());



        String[] options = {
                "Tilføj medlem",
                "Tilføj træner",
                "Tilføj konkurrence-medlem",
                "Tilføj kasserer",
                "Ændre medlemsoplysninger",
                "Slet medlem",
                "Se brugeroversigt",
        };

        int choice = showMenu("Menu", "Vælg en af følgende muligheder", options);
        System.out.println(choice);
        if(controller.getProcessor() instanceof SuperUserProcessor) {
            System.out.println("SuperUserProcessor");
        }

        ArrayList<String> userStringList = new ArrayList<>();

        DBController dbController = controller.getDBController();
        for(User user : dbController.getListOfAllUsers()) {
            userStringList.add(user.toString());
        }

        showList("Brugere", userStringList);

    }



    public void showList(String title, ArrayList<String> rows) {
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


    public int welcomeMessage() {
        return JOptionPane.showOptionDialog(null,
                TextStyle.format("(Velkommen) til Delfinen administrationssystem!"),
                "Svømmeklubben Delfinin",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                new String[]{"Log ind"},
                0);
    }



    public String loginMenu() {
        return (String)JOptionPane.showInputDialog(null,
                TextStyle.format("Indtast brugernavn:"),
                "Log ind",
                JOptionPane.PLAIN_MESSAGE,
                icon, null, null);

    }

    public int showMenu(String title, String message, String[] options) {
        //Opretter JButton array med længden af options
        JButton[] buttons = new JButton[options.length];
        final int[] chosenOption = {-1}; // Denne array bruges til at gemme den valgte menu mulighed. Skal være final for at kunne bruges i en ActionListener. Derfor en array da en array index kan ændres selvom den er final.


        //Laver et JLabel med Delfin logoet for at vise i menuen.
        JLabel iconLabel = new JLabel(icon);


        JLabel titleLabel = new JLabel(message, SwingConstants.CENTER);  //Laver et JLabel med Menu overskriften for at vise i menuen. Sætter den til message og for at være i midten af selve JLabelen.
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15)); //Sætter fonten

        //Opretter en JDialog. Dette er selve vinduet, hvor vores JLabel og JButtons placeres indeni.
        JDialog dialog = new JDialog();
        dialog.setTitle(title); //Sætter titlen af selve vinduet.
        dialog.setModal(true); //Om vinduet skal være Modal. Hvis det er modal kan der kun interageres med dette vindue. Sættes true for at undgå fejl med andre vinduer.
        dialog.setIconImage(icon.getImage()); //Sætter det lille ikon i hjørnet til vores delfin logo

        //Loop som tilføjer knapper ud fra indsat options.
        for (int i = 0; i < options.length; i++) {
            final int index = i; // Gemmer index i en final variabel for at kunne bruges i ActionListener.
            buttons[i] = new JButton(options[i]); //Laver en ny JButton med teksten fra options arrayet.
            buttons[i].addActionListener(e -> { //Tilføjer en actionslistener, som reagere når brugeren trykker på knappen.
                chosenOption[0] = index; //Sætter chosenOption til index, så vi kan gemme hvilken knap der er trykket på.
                dialog.dispose(); // Når en knap er trykket på lukkes vinduet.
            });
        }

        //Opretter et JPanel som er vores hovedpanel. Indeholder vores JLabel title, vores JLabel icon og vores JButton panel.
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Sætter layout til BorderLayout. Blot en af flere typer layouts et vindue kan have.
        panel.setBorder(new EmptyBorder(10, 50, 10, 50)); // Tilføjer en tom kant rundt om vores panel, så der er luft imellem elementerne og siderne af vinduet.

        //Opretter et JPanel som indeholder vores knapper. Sætter layout til BoxLayout, så knapperne står under hinanden.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //Tilføjer knapperne til vores buttonPanel
        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Tilføjer et lille luftrum imellem knapperne.
        }

        //Laver et JPanel som er vores title og ikon, så de er ved siden af hinanden. Gøres så vi kan gruppere og positionere dem sammen.
        JPanel northPanel = new JPanel();
        northPanel.add(iconLabel);
        northPanel.add(titleLabel);

        //Sætter vores northPanel og buttonPanel ind i vores hovedpanel. Northpanel øverst.
        panel.add(northPanel, BorderLayout.NORTH); // Add the icon and title to the north of the main panel
        panel.add(buttonPanel, BorderLayout.CENTER); // Add the button panel to the center of the main panel


        dialog.getContentPane().add(panel, BorderLayout.CENTER); //Sætter vores hovedpanel ind i vores dialog som er selve vinduet. Sætter indholdet i midten af vinduet.
        dialog.pack(); //Indbygget metode i dialog der skalere vinduet så det passer til indholdet.
        dialog.setLocationRelativeTo(null); // Sætter vinduet til midten af skærmen.
        dialog.setVisible(true); //Viser vinduet.

        return chosenOption[0]; //Returnere den valgte menu mulighed.
    }

}
