package jkind.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import jkind.JKindException;

/**
 * An arbitrary sized fractional value
 * 
 * Stored as <code>numerator</code> / <code>denominator</code> where the
 * fraction is in reduced form and <code>denominator</code> is always positive
 */
public class BigFraction implements Comparable<BigFraction> {
	public static final BigFraction ZERO = new BigFraction(BigInteger.ZERO);
	public static final BigFraction ONE = new BigFraction(BigInteger.ONE);

	// The numerator and denominator are always stored in reduced form with the
	// denominator always positive
	final private BigInteger num;
	final private BigInteger denom;

	public BigFraction(BigInteger num, BigInteger denom) {
		if (num == null || denom == null) {
			throw new NullPointerException();
		}
		if (denom.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Divide by zero");
		}

		BigInteger gcd = num.gcd(denom);
		if (denom.compareTo(BigInteger.ZERO) > 0) {
			this.num = num.divide(gcd);
			this.denom = denom.divide(gcd);
		} else {
			this.num = num.negate().divide(gcd);
			this.denom = denom.negate().divide(gcd);
		}
	}

	static final long SIGN_MASK = 0x8000000000000000L; 
	static final long EXPONENT_MASK = 0x7ff0000000000000L; 
	static final long MANTISSA_MASK = 0x000fffffffffffffL;
	
	public static int getDoubleSign(double d) {
		long bits = Double.doubleToRawLongBits(d);
		return ((bits & SIGN_MASK) != 0) ? 1 : 0;
	}
	
	public static int getDoubleExponent(double d) {
		long bits = Double.doubleToRawLongBits(d);
		int rawExponentVal = (int)((bits & EXPONENT_MASK) >>> 52); 
		/* exponent value biased by 1023 */
		int exponentVal = rawExponentVal - 0x3ff; 
		return exponentVal; 
	}

	 /**
	   * Returns whether or not this double is a subnormal value.
	   * @param d a double value
	   * @return whether or not this double is a subnormal value
	   */
	  public static boolean isDoubleSubnormal(double d)
	  {
	    long bits = Double.doubleToRawLongBits(d);
	    return ((bits & EXPONENT_MASK) == 0) && ((bits & MANTISSA_MASK) != 0);
	  }
	  
	public static long getDoubleMantissa(double d)
	{
	    return Double.doubleToRawLongBits(d) & MANTISSA_MASK;
	}
	  /**
	   * Constructs a BigFraction from a floating-point number.
	 * Arbitrary-precision fraction, utilizing BigIntegers for numerator and
	 * denominator. Fraction is always kept in lowest terms. Fraction is
	 * immutable, and guaranteed not to have a null numerator or denominator.
	 * Denominator will always be positive (so sign is carried by numerator,
	 * and a zero-denominator is impossible).
	 * 
	 * @author Kip Robinson, <a href="https://github.com/kiprobinson">https://github.com/kiprobinson</a>
	 */
	
	public static BigFraction fromValue(double d)
	  {
	    if(Double.isInfinite(d))
	      throw new IllegalArgumentException("double val is infinite");
	    if(Double.isNaN(d))
	      throw new IllegalArgumentException("double val is NaN");
	    
	    //special case - math below won't work right for 0.0 or -0.0
	    if(d == 0.0)
	      return BigFraction.ZERO;
	    
	    //Per IEEE spec...
	    final int sign = getDoubleSign(d);
	    final int exponent = getDoubleExponent(d);
	    final long mantissa = getDoubleMantissa(d);
	    final boolean isSubnormal = isDoubleSubnormal(d);
	    
	    //Number is: (-1)^sign * 2^(exponent) * 1.mantissa
	    //Neglecting sign bit, this gives:
	    //           2^(exponent) * 1.mantissa
	    //         = 2^(exponent) * (1 + mantissa/2^52)
	    //         = 2^(exponent) * (2^52 + mantissa)/2^52
	    // Letting tmpNumerator=(2^52 + mantissa):
	    //         = 2^(exponent) * tmpNumerator/2^52
	    //  
	    //  For exponent > 52:
	    //         = tmpNumerator * 2^(exponent - 52)
	    //  For exponent = 52:
	    //         = tmpNumerator
	    //  For exponent < 52:
	    //         = tmpNumerator / 2^(52 - exponent)
	    //
	    //SPECIAL CASE: Subnormals - if all exponent bits are 0 (in my code, this
	    //would mean exponent is -0x3ff, or -1023), then the formula is:
	    //    (-1)^sign * 2^(exponent+1) * 0.mantissa
	    //    
	    //Again neglecting sign bit, this gives:
	    //           2^(exponent + 1) * 0.mantissa
	    //         = 2^(-1022) * (mantissa/2^52)
	    //         = mantissa / 2^1074
	    // Letting tmpNumerator = mantissa:
	    //         = tmpNumerator / 2^1074
	    
	    BigInteger tmpNumerator = BigInteger.valueOf((isSubnormal ? 0L : 0x10000000000000L) + mantissa);
	    BigInteger tmpDenominator = BigInteger.ONE;
	    
	    if(exponent > 52)
	    {
	      //numerator * 2^(exponent - 52) === numerator << (exponent - 52)
	      tmpNumerator = tmpNumerator.shiftLeft(exponent - 52);
	    }
	    else if (exponent < 52)
	    {
	      if(!isSubnormal)
	      {
	        //The gcd of (2^52 + mantissa) / 2^(52 - exponent)  must be of the form 2^y,
	        //since the only prime factors of the denominator are 2. In base-2, it is
	        //easy to determine how many factors of 2 a number has--it is the number of
	        //trailing "0" bits at the end of the number. (This is the same as the number
	        //of trailing 0's of a base-10 number indicating the number of factors of 10
	        //the number has).
	        int y = Math.min(tmpNumerator.getLowestSetBit(), 52 - exponent);
	        
	        //Now 2^y = gcd( 2^52 + mantissa, 2^(52 - exponent) ), giving:
	        // (2^52 + mantissa) / 2^(52 - exponent)
	        //      = ((2^52 + mantissa) / 2^y) / (2^(52 - exponent) / 2^y)
	        //      = ((2^52 + mantissa) / 2^y) / (2^(52 - exponent - y))
	        //      = ((2^52 + mantissa) >> y) / (1 << (52 - exponent - y))
	        tmpNumerator = tmpNumerator.shiftRight(y);
	        tmpDenominator = tmpDenominator.shiftLeft(52 - exponent - y);
	      }
	      else
	      {
	        //using the same logic as above, except now we are finding gcd of tmpNumerator/2^1074
	        int y = Math.min(tmpNumerator.getLowestSetBit(), 1074);
	        
	        tmpNumerator = tmpNumerator.shiftRight(y);
	        tmpDenominator = tmpDenominator.shiftLeft(1074 - y);
	      }
	    }
	    //else: exponent == 52: do nothing
	    //Set sign bit if needed
	    if(sign != 0)
	      tmpNumerator = tmpNumerator.negate();
	    
	    return new BigFraction(tmpNumerator, tmpDenominator);
	}
	
	public BigFraction(BigInteger num) {
		this(num, BigInteger.ONE);
	}

	public static BigFraction valueOf(BigDecimal value) {
		if (value.scale() >= 0) {
			return new BigFraction(value.unscaledValue(), BigInteger.valueOf(10).pow(value.scale()));
		} else {
			return new BigFraction(value.unscaledValue().multiply(
					BigInteger.valueOf(10).pow(-value.scale())));
		}
	}

	public BigInteger getNumerator() {
		return num;
	}

	public BigInteger getDenominator() {
		return denom;
	}

	public BigFraction add(BigFraction val) {
		return new BigFraction(num.multiply(val.denom).add(val.num.multiply(denom)),
				denom.multiply(val.denom));
	}

	public BigFraction add(BigInteger val) {
		return add(new BigFraction(val));
	}

	public BigFraction subtract(BigFraction val) {
		return new BigFraction(num.multiply(val.denom).subtract(val.num.multiply(denom)),
				denom.multiply(val.denom));
	}

	public BigFraction subtract(BigInteger val) {
		return subtract(new BigFraction(val));
	}

	public BigFraction multiply(BigFraction val) {
		return new BigFraction(num.multiply(val.num), denom.multiply(val.denom));
	}

	public BigFraction multiply(BigInteger val) {
		return multiply(new BigFraction(val));
	}

	public BigFraction divide(BigFraction val) {
		return new BigFraction(num.multiply(val.denom), denom.multiply(val.num));
	}

	public BigFraction divide(BigInteger val) {
		return divide(new BigFraction(val));
	}

	public BigFraction negate() {
		return new BigFraction(num.negate(), denom);
	}

	public int signum() {
		return num.signum();
	}

	public double doubleValue() {
		double result = num.doubleValue() / denom.doubleValue();
		if (Double.isFinite(result)) {
			return result;
		} else {
			BigDecimal numDec = new BigDecimal(num);
			BigDecimal denomDec = new BigDecimal(denom);
			return numDec.divide(denomDec, MathContext.DECIMAL64).doubleValue();
		}
	}

	public BigInteger floor() {
		BigInteger divAndRem[] = num.divideAndRemainder(denom);
		if (num.signum() >= 0 || divAndRem[1].equals(BigInteger.ZERO)) {
			return divAndRem[0];
		} else {
			return divAndRem[0].subtract(BigInteger.ONE);
		}
	}

	public BigDecimal toBigDecimal(int scale) {
		BigDecimal decNum = new BigDecimal(num).setScale(scale);
		BigDecimal decDenom = new BigDecimal(denom);
		return decNum.divide(decDenom, BigDecimal.ROUND_DOWN);
	}

	public String toTruncatedDecimal(int scale, String suffix) {
		if (scale <= 0) {
			throw new JKindException("Scale must be positive");
		}

		BigDecimal dec = toBigDecimal(scale);
		if (this.equals(BigFraction.valueOf(dec))) {
			return Util.removeTrailingZeros(dec.toPlainString());
		} else {
			return dec.toPlainString() + suffix;
		}
	}

	@Override
	public int compareTo(BigFraction other) {
		return num.multiply(other.denom).compareTo(other.num.multiply(denom));
	}

	@Override
	public String toString() {
		if (denom.equals(BigInteger.ONE)) {
			return num.toString();
		} else {
			return num + "/" + denom;
		}
	}

	@Override
	public int hashCode() {
		return num.hashCode() + denom.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BigFraction)) {
			return false;
		}
		BigFraction other = (BigFraction) obj;
		return num.equals(other.num) && denom.equals(other.denom);
	}
}
