package domain_model;
import domain_model.Processors.*;
import user_domain.Member;
import javax.swing.*;
import java.util.Scanner;

public class UserInterface {
    private boolean programIsRunning;
    Scanner userInput;
    Controller controller;
    boolean loginSucces;
    MemberProcessor memberProcessor;

    public UserInterface() {
        controller = new Controller();
        programIsRunning = true;
        loginSucces = true;
        userInput = new Scanner(System.in);
        Member member = new Member(true, true, 30, false); // Eksempel på medlem
        memberProcessor = new MemberProcessor(member);
    }

    public void startProgram() {
        while (programIsRunning) {
            showWelcomeMessage();
            String[] loginInfo = showLoginPopUp();
            if (loginInfo != null && loginInfo.length == 2) {
                String userID = loginInfo[0];
                String password = loginInfo[1];
                if (loginSucces) {
                    navigateToRole(userID);
                    programIsRunning = false;
                }
                else{
                    programIsRunning = false;
                }
            }
        }
    }

    private String[] showLoginPopUp() {
        String[] loginInfo = new String[2];
        String userID = JOptionPane.showInputDialog(null, "Indtast UserID");
        String password = JOptionPane.showInputDialog(null, "Indtast Password");

        if ((userID != null && !userID.isEmpty()) && (password != null && !password.isEmpty())) {
            loginInfo[0] = userID;
            loginInfo[1] = password;
            return loginInfo;
        }
        return null;
    }

    private void navigateToRole(String userID) {
        switch (userID.toLowerCase()) {
            case "superuser":
                JOptionPane.showMessageDialog(null, "Logget ind som 'SuperUser'", "Login Success!", JOptionPane.INFORMATION_MESSAGE);
                loginAsSuperUser();
                break;

            case "trainer":
                JOptionPane.showMessageDialog(null, "Logget ind som 'Trainer'", "Login Success!", JOptionPane.INFORMATION_MESSAGE);
                loginAsTrainer();
                break;

            case "treasurer":
                JOptionPane.showMessageDialog(null, "Logget ind som 'Treasurer'", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                loginAsTreasurer();
                break;

            case "member":
                JOptionPane.showMessageDialog(null, "Logget ind som 'Member'", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                loginAsMember();
                break;

            default:
                print("Forkert input, prøv igen.");
                showWelcomeMessage();
                break;
        }
    }

    private void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Velkommen til Delfinen Sv\u00F8mmehal. \n Indtast login.", "Velkommen", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loginAsSuperUser() {
        print("Logget ind som Super User.");
    }

    private void loginAsTrainer() {
        print("Logget ind som Trainer.");
    }

    private void loginAsTreasurer() {
        print("Logget ind som Treasurer.");
    }

    private void loginAsMember() {
        print("Logget ind som Member.");
        memberProcessor.displayMemberOverview();
    }

    private void print(String s) {
        System.out.println(s);
    }

}

