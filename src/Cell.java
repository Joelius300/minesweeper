public class Cell {

	//DEBUG
	//public int id;

	public boolean isBomb() {
		return isBomb;
	}

	public void setBomb(boolean bomb) {
		isBomb = bomb;
	}

	private boolean isBomb;

    private cellState State;
    private Field field;
    
	private int bombValue;

    public Cell() {
    	isBomb = false;
		State = cellState.Unopened;

		//DEBUG
		//State = cellState.Open;
    }
    
    public void SetField(Field field) {
    	this.field = field;
    }

	public int GetBombValue() {
		return bombValue;
	}
    
    public void CalculateBombValue() {
    	Cell[][] neighbours = field.GetNeighbours(this);
    	
    	int bombs = 0;
    	
    	for(int i = 0; i < 3; i++) {
    		for(int j = 0; j < 3; j++) {
        		if(neighbours[i][j] != null && neighbours[i][j] != this && !neighbours[i][j].equals(this)) {
						bombs += neighbours[i][j].isBomb() ? 1 : 0;
				}
        	}
    	}
    	
    	bombValue = bombs;
    }

    
    public cellState GetCellState() {
    	return this.State;
    }

    /*
     @return 	was a bomb (game lost)
     */
    public boolean Open() {
    	if(isBomb) {
			this.State = cellState.Exploded;
			return true;
		}else{
			this.State = cellState.Open;

			if(GetBombValue() == 0){
				OpenAllZeroNeighbours();
			}

			field.CheckIfWon();
			return false;
		}
    }

    
    public void Flag() {
    	if(this.State == cellState.Flagged) {
    		this.State = cellState.Unopened;
    	}else if (this.State == cellState.Unopened){
    		this.State = cellState.Flagged;
    	}

		field.CheckIfWon();
    }
    
    public String GetShowValue() {
    	switch(State) {
    	case Unopened:
    		return "☐";
    	case Open:
    		return String.valueOf(GetBombValue());
					//DEBUG
					// + (isBomb ? "t" : "f")
					// + id;
    	case Flagged: 
    		return "F";
    	case Exploded: 
    		return "X";
    	default:
    		return "?";
    	}
    }

    
    private void OpenAllZeroNeighbours() {
		Cell[][] neighbours = field.GetNeighbours(this);
    	
    	for(int i = 0; i < 3; i++) {
    		for(int j = 0; j < 3; j++) {
        		if(neighbours[i][j] != null && neighbours[i][j] != this && !neighbours[i][j].equals(this)) {
					if(!neighbours[i][j].isBomb() && neighbours[i][j].GetCellState() == cellState.Unopened) {
        				neighbours[i][j].Open();
        			}
        		}
        	}
    	}
    }
}
