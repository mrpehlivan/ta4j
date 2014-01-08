package eu.verdelhan.tailtest.strategy;

import eu.verdelhan.tailtest.Indicator;
import eu.verdelhan.tailtest.Operation;
import eu.verdelhan.tailtest.OperationType;
import eu.verdelhan.tailtest.Strategy;
import eu.verdelhan.tailtest.Trade;
import eu.verdelhan.tailtest.sample.SampleIndicator;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class IndicatorOverIndicatorStrategyTest {

	private Indicator<BigDecimal> first;

	private Indicator<BigDecimal> second;

	@Before
	public void setUp() throws Exception {

		first = new SampleIndicator<BigDecimal>(new BigDecimal[] {
			BigDecimal.valueOf(4),
			BigDecimal.valueOf(7),
			BigDecimal.valueOf(9),
			BigDecimal.valueOf(6),
			BigDecimal.valueOf(3),
			BigDecimal.valueOf(2)
		});
		second = new SampleIndicator<BigDecimal>(new BigDecimal[] {
			BigDecimal.valueOf(3),
			BigDecimal.valueOf(6),
			BigDecimal.valueOf(10),
			BigDecimal.valueOf(8),
			BigDecimal.valueOf(2),
			BigDecimal.valueOf(1)
		});

	}

	@Test
	public void testOverIndicators() {
		Trade trade = new Trade();

		Strategy s = new IndicatorOverIndicatorStrategy(first, second);
		assertFalse(s.shouldOperate(trade, 0));
		assertFalse(s.shouldOperate(trade, 1));
		assertEquals(null, trade.getEntry());
		Operation buy = new Operation(2, OperationType.BUY);
		assertTrue(s.shouldOperate(trade, 2));
		trade.operate(2);
		assertEquals(buy, trade.getEntry());
		trade = new Trade();
		buy = new Operation(3, OperationType.BUY);
		assertTrue(s.shouldOperate(trade, 3));
		trade.operate(3);
		assertEquals(buy, trade.getEntry());

		assertFalse(s.shouldOperate(trade, 3));

		Operation sell = new Operation(4, OperationType.SELL);
		assertTrue(s.shouldOperate(trade, 4));
		trade.operate(4);
		assertEquals(sell, trade.getExit());

	}
}