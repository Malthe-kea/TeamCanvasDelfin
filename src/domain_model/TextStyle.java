package domain_model;

public class TextStyle {

//HTML COLOR CODES

//    public static final String RESET = "\u001B[0m";
    public static final String RESET = "</font>";
    public static final String RESET_BOLD = "</b>";
    public static final String RESET_ITALIC = "</i>";
    public static final String YELLOW_FG = "<font color=\"yellow\">";
    public static final String GREEN_FG = "<font color=\"green\">";
    public static final String BRIGHT_RED_FG = "<font color=\"red\">";
    public static final String PURPLE_FG = "<font color=\"purple\">";
    public static final String ITALIC = "<i>";
    public static final String BOLD = "<b>";

    // Metoden tager en string ind og returnerer en string magen til men i farver.
    public static String format(String s) {

        s = s.replaceAll("\\[",YELLOW_FG);
        s = s.replaceAll("]",RESET);

        s = "<html>" + s + "</html>";


        s = s.replaceAll("\\[", GREEN_FG);
        s = s.replaceAll("]", RESET);




        s = s.replaceAll("\\{",BRIGHT_RED_FG);
        s = s.replaceAll("}",RESET);

        s = s.replaceAll("\\(",PURPLE_FG);
        s = s.replaceAll("\\)",RESET);


        String[] parts = s.split("\\*\\*");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (i % 2 == 1) {
                sb.append(BOLD);
            }
            sb.append(parts[i]);
            if (i != parts.length - 1) {
                sb.append(RESET_BOLD);
            }
        }

        s = sb.toString();

        parts = s.split("\\*");
        sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i % 2 == 1) {
                sb.append(ITALIC);
            }
            sb.append(parts[i]);
            if (i != parts.length - 1) {
                sb.append(RESET_ITALIC);
            }
        }

        s = sb.toString();

        return s;
    }

}
