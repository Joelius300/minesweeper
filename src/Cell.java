public class Cell {
	public boolean isBomb;

    private cellState State;
    private Field field;
    

    public Cell() {
    	isBomb = false;
    	State = cellState.Unopened;
    }
    
    public void SetField(Field field) {
    	this.field = field;
    }
    
    
    public int GetBombValue() {
    	Cell[][] neighbours = field.GetNeighbours(this);
    	
    	int bombs = 0;
    	
    	for(int i = 0; i < 3; i++) {
    		for(int j = 0; j < 3; j++) {
        		if(i != 1 && j != 1) {
        			bombs += neighbours[i][j].GetBombValue();
        		}
        	}
    	}
    	
    	return bombs;
    }

    
    public cellState GetCellState() {
    	return this.State;
    }

    /*
     @return 	was a bomb (game lost)?
     */
    public boolean Open() {
    	if(isBomb) {
			this.State = cellState.Exploded;
			return true;
		}else{
			this.State = cellState.Open;
			return false;
		}
    }

    
    public void Flag() {
    	if(this.State == cellState.Flagged) {
    		this.State = cellState.Unopened;
    	}else if (this.State == cellState.Unopened){
    		this.State = cellState.Flagged;
    	}
    }
    
    public String GetShowValue() {
    	switch(State) {
    	case Unopened:
    		return "☐";
    	case Open:
    		return String.valueOf(this.GetBombValue());
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
        		if(i != 1 && j != 1) {
        			if(neighbours[i][j].GetBombValue() == 0) {
        				neighbours[i][j].Open();
        			}
        		}
        	}
    	}
    }
}
