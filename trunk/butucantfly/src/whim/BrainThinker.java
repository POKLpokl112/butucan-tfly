package whim;

import java.math.BigDecimal;

public class BrainThinker {

	public static final BigDecimal NM = new BigDecimal(0.0000000001);

	public BigDecimal computeANum(BigDecimal b1, BigDecimal b2, final String key) {
		if (b1 == null) {
			b1 = new BigDecimal(0);
		}

		if (b2 == null) {
			b2 = new BigDecimal(0);
		}

		return computeNum(b1, b2, key);
	}

	public BigDecimal computeNum(final BigDecimal b1, final BigDecimal b2, final String key) {
		if (b1 == null || b2 == null) {
			return NM;
		}

		if (b1.equals(NM) || b2.equals(NM)) {
			return NM;
		}

		return compute(b1, b2, key);
	}

	private BigDecimal compute(final BigDecimal b1, final BigDecimal b2, final String key) {

		switch (key) {
		case "+":
			return b1.add(b2);

		case "-":
			return b1.subtract(b2);

		case "*":
			return b1.multiply(b2);

		case "/":
			return b1.divide(b2);

		default:
			throw new IllegalArgumentException();
		}
	}

	public static void main(final String[] args) {

	}

}
