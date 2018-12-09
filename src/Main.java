import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Minesweeper minesweeper;
    	
    	do {
    	    minesweeper = new Minesweeper(4, 4, 1);

    	    minesweeper.Play();
            System.out.println("Erneut spielen? (j/n)");
        }while(scanner.next().toLowerCase().charAt(0) == 'j');

    }

}
