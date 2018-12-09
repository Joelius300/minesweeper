
public class Minesweeper {
	public Field field;

    public Interpreter interpreter;

    public Minesweeper(int width, int height, int bombAmount){
        field = new Field(width, height, bombAmount);

        interpreter = new Interpreter(field);
    }

    public void Play() {
    	do{
    	    field.PrintField();

    	    interpreter.Read();
        }while(!field.GameOver);

    	if(field.Won){
    	    System.out.println("Gratulation! Sie haben gewonnen.");
        }else{
            System.out.println("Game Over! Sie haben eine Bombe getroffen.");
        }

        field.PrintField();
    }
}
