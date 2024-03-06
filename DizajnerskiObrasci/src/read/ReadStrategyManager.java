package read;

import java.util.List;

public class ReadStrategyManager implements ReadStrategy {

	private ReadStrategy strategy;
	@Override
	public List<Object> read() {
		return strategy.read();
	}
	public ReadStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(ReadStrategy strategy) {
		this.strategy = strategy;
	}
	
	

}
