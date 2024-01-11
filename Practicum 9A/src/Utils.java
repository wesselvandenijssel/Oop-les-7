import java.text.DecimalFormat;

public class Utils {
    private static int euroBedragAanroepen = 0;

    public static String euroBedrag(double bedrag) {
        euroBedragAanroepen++;
        return formatteerBedrag(bedrag, 2);
    }

    public static String euroBedrag(double bedrag, int precisie) {
        euroBedragAanroepen++;
        return formatteerBedrag(bedrag, precisie);
    }

    private static String formatteerBedrag(double bedrag, int precisie) {
        String patroon = "€ #,##0." + "0".repeat(precisie);
        DecimalFormat decimalFormat = new DecimalFormat(patroon);
        return decimalFormat.format(bedrag);
    }

    public static int getAantalEuroBedragAanroepen() {
        return euroBedragAanroepen;
    }

    public static void main(String[] args) {
        // Test de euroBedrag-methoden met verschillende waarden
        System.out.println(Utils.euroBedrag(3.11314));          // "€ 3,11"
        System.out.println(Utils.euroBedrag(3.11314, 1));       // "€ 3,1"
    }
}
