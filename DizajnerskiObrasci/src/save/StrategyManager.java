package save;

public class StrategyManager implements SaveStrategy {

	private SaveStrategy strategy;

	public SaveStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(SaveStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public void save() {
		this.strategy.save();
		
	}
	
	
}
