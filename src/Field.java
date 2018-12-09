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
    	if(Roster[y][x].Open()){
    		EndGame(false);
		}
    }

    
    public void FlagCell(int x, int y) {
    	Roster[y][x].Flag();
    }


    public int GetCellValue(int x, int y) {
    	return Roster[y][x].GetBombValue();
    }


    public cellState GetCellState(int x, int y) {
    	return Roster[y][x].GetCellState();
    }

    
    public Cell[][] GetNeighbours(int x, int y) {
    	Cell[][] neighbours = new Cell[3][3];
    	
    	int counterX = 0;
    	int counterY = 0;
    	
    	for(int i = (y-1);i <= (y+1); i++) {
    		for(int j = (x-1);j <= (x+1); j++) {
    			
    			if((i >= 0 && i < height) && (j >= 0 && j < width)) {
    				neighbours[counterY][counterX] = Roster[i][j];
    			}else{
					neighbours[counterY][counterX] = null;
				}
    			
    			counterX++;
        	}
    		
    		counterX = 0;
    		counterY++;
    	}
    	
    	return neighbours;
    }
    
    public Cell[][] GetNeighbours(Cell cell){
    	for(int i = 0;i < height; i++) {
    		for(int j = 0;j < width; j++) {
    			if(Roster[i][j].equals(cell) && Roster[i][j] == cell) {
    				return GetNeighbours(j, i);
    			}
        	}
    	}
    	
    	return new Cell[3][3];
    }

    
    public void PrintField() {
    	for(int i = 0;i < height; i++) {
    		for(int j = 0;j < width; j++) {
    			System.out.printf("%3s", Roster[i][j].GetShowValue());
        	}
			System.out.println();
    	}
    }

    
    public void EndGame(boolean won) {
    	this.GameOver = true;
		this.Won = won;
    }

    public void CheckIfWon(){
		boolean allNotBombsOpened = true;
		boolean allBombsFlagged = true; //Für zwei Tests verwendet, da beide der Tests zusammen gehören und notwenig sind für einen Sieg

		for(int i = 0;i < height; i++) {
			for(int j = 0;j < width; j++) {
				if(Roster[i][j].GetCellState() == cellState.Unopened){
					if(!Roster[i][j].isBomb()){ //Ungeöffnete Nicht-Bombe ist nicht gewonnen
						if(allNotBombsOpened){ allNotBombsOpened = false; }
					}
				}else if(Roster[i][j].GetCellState() == cellState.Flagged){ //Geflaggte Nicht-Bombe ist nicht gewonnen (Ansonsten könnte man einfach alles Flaggen)
					if(!Roster[i][j].isBomb()){
						if(allBombsFlagged){ allBombsFlagged = false; }
					}
				}

				if(Roster[i][j].isBomb()){
					if(Roster[i][j].GetCellState() != cellState.Flagged){ //Ungeflaggte Bombe ist nicht gewonnen
						if(allBombsFlagged){ allBombsFlagged = false; }
					}
				}
			}
		}

		if(allBombsFlagged || allNotBombsOpened){
			EndGame(true);
		}
	}

    
    public void SetBombs(int amount) {
    	for(int i = 0; i < amount; i++) {
    		int x = rnd.nextInt(this.width);
    		int y = rnd.nextInt(this.height);

			//int x = 0;
			//int y = 0;

    		if(this.Roster[y][x].isBomb()) {
    			i--;
    			continue;
    		}else {
				this.Roster[y][x].setBomb(true);
    		}
    	}
    }

    
    private void CreateField() {
		this.Roster = new Cell[height][width];
		for(int i = 0;i < height; i++) {
			for(int j = 0;j < width; j++) {
				Roster[i][j] = new Cell();
				Roster[i][j].SetField(this);

				//DEBUG
				//Roster[i][j].id = j+1 + (width * i);
			}
		}
    }

    private void CalculateValues(){
		for(int i = 0;i < height; i++) {
			for(int j = 0;j < width; j++) {
				Roster[i][j].CalculateBombValue();
			}
		}
	}

    
    public Field(int width, int height, int bombAmount) {
		this.width = width;
		this.height = height;

		CreateField();
		SetBombs(bombAmount);
		CalculateValues();
    }
}
