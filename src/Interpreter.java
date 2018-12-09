import java.util.Scanner;

public class Interpreter {

    private Scanner scanner = new Scanner(System.in);
    private String lastError = "";

    private Field field;


    public Interpreter(Field field){
        this.field = field;
    }


    public void Read() {
        String[] parts;

        boolean firstTime = true;

        do {
            if(!firstTime){
                System.out.println(lastError);
            }

            System.out.println("Bitte geben Sie einen Befehl ein {f(lag)|o(pen) x y}.");
            String input = scanner.nextLine();
            parts = input.split(" ");

            if(firstTime){
               firstTime = false;
            }
        }while(!ValidateInput(parts));

        Execute(parts);
    }

    public void ReadOneEnter(){
        scanner.nextLine();
    }


    private boolean ValidateInput(String[] input) {
        if(!ValidateSyntax(input)){
            return false;
        }else{
            if(!ValidateState(input)) {
                return false;
            }else{
                return true;
            }
        }
    }

    private boolean ValidateState(String[] input) {
        int x = Integer.parseInt(input[1]) - 1;
        int y = Integer.parseInt(input[2]) - 1;

        if(x < 0 || x > field.GetWidth() - 1
        || y < 0 || y > field.GetHeight() - 1){
            this.lastError = "Out of bounds";
            return false;
        }


        switch(input[0]){
            case "o":
                if(field.GetCellState(x, y) != cellState.Unopened){
                    this.lastError = "Can only open unopened Cells";
                    return false;
                }
                break;
            case "f":
                cellState state = field.GetCellState(x, y);
                if(state != cellState.Unopened && state != cellState.Flagged){
                    this.lastError = "Can only flag unopened and flagged Cells";
                    return false;
                }
                break;
            default:
                this.lastError = "Bad command";
                return false;
        }

        return true;
    }


    private boolean ValidateSyntax(String[] input) {
        if(input.length != 3){
            this.lastError = "Syntax: command(o|f) x y";
            return false;
        }

        char command = input[0].toLowerCase().charAt(0);

        if(command != 'o' && command != 'f'){
            this.lastError = "Bad command (only o and f accepted)";
            return false;
        }

        try {
            Integer.parseInt(input[1]);
            Integer.parseInt(input[2]);
        }catch(Exception e){
            this.lastError = "Bad coordinates";
            return false;
        }

        return true;
    }


    private void Execute(String[] input) {
        int x = Integer.parseInt(input[1]) - 1;
        int y = Integer.parseInt(input[2]) - 1;

        switch(input[0]){
            case "o":
                field.OpenCell(x, y);
                break;
            case "f":
                field.FlagCell(x, y);
                break;
            default:
                this.lastError = "wtf happened";
        }
    }

}
