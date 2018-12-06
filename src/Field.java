import java.util.Random;

public class Field {

    private Cell[][] Roster;
    Random rnd = new Random();

	public boolean GameOver = false;
	public boolean Won;

    private int width;
    private int height;

	public int GetWidth(){
		return this.width;
	}

    public int GetHeight(){
    	return this.height;
	}

    public void OpenCell(int x, int y) {
    	if(Roster[x][y].Open()){
    		EndGame(false);
		}
    }

    
    public void FlagCell(int x, int y) {
    	Roster[x][y].Flag();
    }


    public int GetCellValue(int x, int y) {
    	return Roster[x][y].GetBombValue();
    }


    public cellState GetCellState(int x, int y) {
    	return Roster[x][y].GetCellState();
    }

    
    public Cell[][] GetNeighbours(int x, int y) {
    	Cell[][] neighbours = new Cell[3][3];
    	
    	int counterX = 0;
    	int counterY = 0;
    	
    	for(int i = (y-1);i <= (y+1); i++) {
    		for(int j = (x-1);j <= (x+1); j++) {
    			
    			if((i >= 0 && i < height) && (j >= 0 && j < width)) {
    				neighbours[counterX][counterY] = Roster[j][i];
    			}
    			
    			counterY++;
        	}
    		
    		counterY = 0;
    		counterX++;
    	}
    	
    	return neighbours;
    }
    
    public Cell[][] GetNeighbours(Cell cell){
    	for(int i = 0;i < height; i++) {
    		for(int j = 0;j < width; j++) {
    			if(Roster[j][i].equals(cell)) {
    				return GetNeighbours(i, j);
    			}
        	}
    	}
    	
    	return new Cell[3][3];
    }

    
    public void PrintField() {
    	for(int i = 0;i < height; i++) {
    		for(int j = 0;j < width; j++) {
    			System.out.println(Roster[j][i].GetShowValue() + " ");
        	}
    	}
    }

    
    public void EndGame(boolean won) {
    	GameOver = true;
		this.Won = won;
    }

    
    private void SetBombs(int amount) {
    	for(int i = 0; i < amount; i++) {
    		//int x = rnd.nextInt(this.width);
    		//int y = rnd.nextInt(this.height);

			int x = 0;
			int y = 0;

    		if(Roster[x][y].isBomb) {
    			i--;
    			continue;
    		}else {
    			Roster[x][y].isBomb = true;
    		}
    	}
    }

    
    private void CreateField() {
    	//this.Roster = new Cell[height][width]; //width/height... überau im code vil vrtuuscht u so muäsmä de aluägä

		//anscheinend muss mit for gearbeitet werden..
		// https://stackoverflow.com/questions/12231453/syntax-for-creating-a-two-dimensional-array

		this.Roster = new Cell[][]{
				{ new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell() },
				{ new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell() },
				{ new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell() },
				{ new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell() },
				{ new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell(), new Cell() }
		};
    }

    
    public Field(int width, int height, int bombAmount) {
		this.width = width;
		this.height = height;

		CreateField();
    	SetBombs(bombAmount);
    }

    public void Initialize(){
		for(int i = 0;i < height; i++) {
			for(int j = 0;j < width; j++) {
				Roster[j][i].SetField(this);
			}
		}
	}
}
