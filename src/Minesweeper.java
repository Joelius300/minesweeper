
public class Minesweeper {
	public Field field;

    public Interpreter interpreter;

    public Minesweeper(int width, int height, int bombAmount){
        field = new Field(width, height, bombAmount);
        field.Initialize();

        interpreter = new Interpreter(field);
    }

    public void Play() {
    	do{
    	    field.PrintField();

    	    interpreter.Read();
        }while(!field.GameOver);

    	if(field.Won){
    	    System.out.println("Gratulation");
        }else{
            System.out.println("Leider hast du eine Bombe getroffen");
        }
        field.PrintField();

    	interpreter.ReadOneEnter();
    }
}
