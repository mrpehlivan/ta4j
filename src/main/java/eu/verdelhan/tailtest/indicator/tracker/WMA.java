package eu.verdelhan.tailtest.indicator.tracker;

import eu.verdelhan.tailtest.Indicator;

public class WMA implements Indicator<Double> {

	private int timeFrame;

	private Indicator<? extends Number> indicator;

	public WMA(Indicator<? extends Number> indicator, int timeFrame) {
		this.indicator = indicator;
		this.timeFrame = timeFrame;
	}

	@Override
	public Double getValue(int index) {
		if(index == 0) return indicator.getValue(0).doubleValue();
		double value = 0;
		if(index - timeFrame < 0) {
			
			for(int i = index + 1; i > 0; i--) {
				value += i * indicator.getValue(i-1).doubleValue();
				
			}
			 return value / (((index + 1) * (index + 2)) / 2);
		}
		
		int actualIndex = index;
		for(int i = timeFrame; i > 0; i--) {
			value += i * indicator.getValue(actualIndex).doubleValue();
			actualIndex--;
		}
		return value / ((timeFrame * (timeFrame + 1)) / 2);
	}

	@Override
	public String getName() {
		return String.format(getClass().getSimpleName() + " timeFrame: %s", timeFrame);
	}
}