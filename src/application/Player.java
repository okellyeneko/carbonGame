package application;

public class Player {
    private int location;
    private int gemsCollected;
    private int timeBudget;
    private int costBudget;
    private int carbonBudget;
    private int startLocation;

    public Player(int startLocation) {
        this.gemsCollected = 0;
        resetBudgets();
        this.location = startLocation;
        this.startLocation = startLocation;
        
    }
    public int getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(int startLocation) {
        this.startLocation = startLocation;
    }

    public void resetBudgets() {
        // Initial budget values, these could be adjusted based on the level or other criteria
        this.timeBudget = 100;
        this.costBudget = 50;
        this.carbonBudget = 200;
    }

    public void collectGem() {
        this.gemsCollected++;
    }

    public boolean updateBudgets(int timeCost, int monetaryCost, int carbonCost) {
    	this.timeBudget -= timeCost;
        this.costBudget -= monetaryCost;
        this.carbonBudget -= carbonCost;
        this.timeBudget = Math.max(this.timeBudget, 0);
        this.costBudget = Math.max(this.costBudget, 0);
        this.carbonBudget = Math.max(this.carbonBudget, 0);
    	if(this.timeBudget <= 0 || this.costBudget <= 0 || this.costBudget  <= 0 ) {
    		return false;
    	}
    	return true;
        
    }

    // New methods to specifically deduct time, cost, and carbon footprint
    public void deductTime(int time) {
        this.timeBudget -= time;
        this.timeBudget = Math.min(this.timeBudget, 100);
    }

    public void deductCost(int cost) {
        this.costBudget -= cost;
        this.costBudget = Math.min(this.costBudget, 50);
    }

    public void deductCarbonFootprint(int carbonFootprint) {
        this.carbonBudget -= carbonFootprint;
        this.carbonBudget = Math.min(this.carbonBudget, 200);
    }

    // Getters and setters
    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getGemsCollected() {
        return gemsCollected;
    }

    public int getTimeBudget() {
        return timeBudget;
    }

    public int getCostBudget() {
        return costBudget;
    }

    public int getCarbonBudget() {
        return carbonBudget;
    }

    public void setTimeBudget(int time) {
        this.timeBudget = time;
    }

    public void setCostBudget(int cost) {
        this.costBudget = cost;
    }

    public void setCarbonBudget(int carbon) {
        this.carbonBudget = carbon;
    }

    public boolean canContinue() {
        return timeBudget > 0 && costBudget > 0 && carbonBudget > 0;
    }
}
