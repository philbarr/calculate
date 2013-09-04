package com.simplyapped.libgdx.ext.noise;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Rand {
	/**
	 * State for random number generation
	 */
	private static volatile long state=xorShift64(System.nanoTime()|0xCAFEBABE);

	/**
	 * Gets a long random value
	 * @return Random long value based on static state
	 */
	public static final long nextLong() {
		long a=state;
		state = xorShift64(a);
		return a;
	}
	
	/**
	 * XORShift algorithm - credit to George Marsaglia!
	 * @param a Initial state
	 * @return new state
	 */
	public static final long xorShift64(long a) {
		a ^= (a << 21);
		a ^= (a >>> 35);
		a ^= (a << 4);
		return a;
	}
	
	public static final int xorShift32(int a) {
		a ^= (a << 13);
		a ^= (a >>> 17);
		a ^= (a << 5);
		return a;
	}
	
	/**
	 * Creates a random number generator
	 * @return
	 */
	public static java.util.Random getGenerator() {
		return new MikeraRandom();
	}
		
	/**
	 * Implementation of Random class using XORShift RNG
	 * 
	 * @author Mike Anderson
	 *
	 */
	static final class MikeraRandom extends java.util.Random {
		private static final long serialVersionUID = 6868944865706425166L;

		private volatile long state=System.nanoTime()|1;
		
		protected int next(int bits) {
			return (int)(nextLong()>>>(64-bits));
		}
		
		public long nextLong() {
			long a=state;
			state=Rand.xorShift64(a);
			return a;
		}
		
		public void setSeed(long seed) {
			if (seed==0) seed=54384849948L;
			state=seed;
		}
		
		public long getSeed() {
			return state;
		}
		
		public boolean equals(Object o) {
			if (o instanceof MikeraRandom) {
				return equals((MikeraRandom)o);
			}
			return super.equals(o);
		}
		
		public boolean equals(MikeraRandom o) {
			return state==o.state;
		}
		
		public int hashCode() {
			return ((int)state)^((int)(state>>>32));
		}
	}
	
	/**
	 * Returns true with a given probability
	 * @param d
	 * @return
	 */
	public static boolean chance(double d) {
		return Rand.nextDouble()<d;
	}
	
	/**
	 * Returns true with a given probability
	 * @param d
	 * @return
	 */
	public static boolean chance(float d) {
		return Rand.nextFloat()<d;
	}

	
	/**
	 *  Poisson distribution
	 * @param x
	 * @return
	 */
	public static int po(double x) {
		if (x<=0) {
			if (x<0) throw new IllegalArgumentException();
			return 0;
		}
		if (x>400) {
			return poLarge(x);
		}
		return poMedium(x);
	}
	
	// sigmoid probability
	public static int sig(float x) {
		return (Rand.chance(Maths.sigmoid(x))?1:0);
	}

	private static int poMedium(double x) {
		int r = 0;
		double a = nextDouble();
		double p = Math.exp(-x);

		while (a > p) {
			r++;
			a = a - p;
			p = p * x / r;
		}
		return r;
	}
	
	private static int poLarge(double x) {
		// normal approximation to poisson
		// strictly needed for x>=746 (=> Math.exp(-x)==0)
		return (int)(0.5+n(x,Math.sqrt(x)));
	}

	public static int po(int numerator, int denominator) {
		return po(((double) numerator) / denominator);
	}

	/*
	 *  Exponential distribution
	 *  Continuous distribution
	 */
	
	public static double exp(double mean) {
		return -Math.log(Rand.nextDouble())*mean;
	}
	
	/*
	 *  Geometric distribution
	 *  Discrete distribution with decay rate p
	 *  Mean = (1-p) / p
	 *  Probability mass function for each integer output k = p.(1-p)^k
	 */	
	public static int geom(double p) {
		return (int)Math.floor(Math.log(Rand.nextDouble()) / Math.log(1-p));
	}
	
	
	/*
	 * Generators for standard Java types
	 */
	
	public static final int nextInt() {
		return (int)(nextLong()>>32);
	}
	
	public static final short nextShort() {
		return (short)(nextLong()>>32);
	}
	
	
	public static final char nextChar() {
		return (char)(nextLong()>>32);
	}
	
	public static final String nextLetterString(int length) {
		char[] cs=new char[length];
		for (int i=0; i<length; i++) {
			cs[i]=nextLetter();
		}
		return new String(cs);
	}
	
	public static final byte nextByte() {
		return (byte)(nextLong()>>32);
	}
	
	public static boolean nextBoolean() {
		return (nextLong()&65536)!=0;
	}
	
	public static final char nextLetter() {
		return (char)Rand.range('a','z');
	}
	
	/**
	 * Random number from zero to s-1
	 * 
	 * @param s Upper bound (excluded)
	 * @return
	 */
	public static final int r(int s) {
		if (s<0) throw new IllegalArgumentException();
		long result=((nextLong()>>>32)*s)>>32;
		return (int) result;
	}
	
	/**
	 * Return a random index between 0 and max (exclusive) not equal to i
	 */
	public static final int otherIndex(int i, int max) {
		return (Rand.r(max-1)+i)%max;
	}

	
	private static final double DOUBLE_SCALE_FACTOR=1.0/Math.pow(2,63);
	private static final float FLOAT_SCALE_FACTOR=(float)(1.0/Math.pow(2,63));

	/**
	 * Returns standard double in range 0..1
	 * 
	 * @return
	 */
	public static final double nextDouble() {
		return ( nextLong()>>>1 ) * DOUBLE_SCALE_FACTOR;
	}
	
	public static final float nextFloat() {
		return ( nextLong()>>>1 ) * FLOAT_SCALE_FACTOR;
	}

	
	public static final double u() {
		return nextDouble();
	}
	
	public static final double u(double a) {
		return u(0,a);
	}
	
	public static final double u(double a,double b) {
		return a+nextDouble()*(b-a);
	}

	
	/**
	 * Randomly rounds to the nearest integer
	 */
	public static final int round(double d) {
		int i=(int)Math.floor(d);
		int rem=(nextDouble()<(d-i))?1:0;
		return i+rem;
	}
	
    /**
     *  Returns random number uniformly distributed in inclusive [n1, n2] range.
     *  It is allowed to have to n1 > n2, or n1 < n2, or n1 == n2.
     */
     public static final int range(int n1, int n2) {
        if (n1>n2) {
        	int t=n1; n1=n2; n2=t;
        }
        return n1+r(n2-n1+1);
     }
	
	/**
	 *  simulates a dice roll with the given number of sides
	 * @param sides
	 * @return
	 */
	public static final int d(int sides) {
		return r(sides) + 1;
	}
	
	public static final int d3() {
		return d(3);
	}

	public static final int d4() {
		return d(4);
	}

	public static final int d6() {
		return d(6);
	}

	public static final int d8() {
		return d(8);
	}

	public static final int d10() {
		return d(10);
	}

	public static final int d12() {
		return d(12);
	}

	public static final int d20() {
		return d(20);
	}

	public static final int d100() {
		return d(100);
	}
	
	public static int d(int number, int sides) {
		int total = 0;
		for (int i = 0; i < number; i++) {
			total += d(sides);
		}
		return total;
	}
	
	/**
	 * Generates a normal distributed number with the given mean and standard deviation
	 * 
	 * @param u Mean
	 * @param sd Standard deviation
	 * @return
	 */
	public static double n(double u, double sd) {
		return nextGaussian()*sd+u;
	}
	
	public static double nextGaussian() {
		// create a guassian random variable based on
		// Box-Muller transform
		double x, y, d2;
		do { 
			// sample a point in the unit disc
			x = 2*nextDouble()-1;  
			y = 2*nextDouble()-1;  
			d2 = x*x + y*y;
		} while ((d2 > 1) || (d2==0));
		// create the radius factor
		double radiusFactor = Math.sqrt(-2 * Math.log(d2) / d2);
		return x * radiusFactor;
		// could save and use the other value?
		// double anotherGaussian = y * radiusFactor;
	}

	public static String nextString() {
		char[] cs=new char[Rand.po(4)];
		for (int i=0; i<cs.length; i++) cs[i]=Rand.nextLetter();
		return String.valueOf(cs);
	}
	
	/**
	 * Shuffles all elements in a given array
	 */
	public static <T> void shuffle(T[] ts) {
		for (int i=0; i<(ts.length-1); i++) {
			int j=Rand.r(ts.length-i);
			if (i!=j) {
				Arrays.swap(ts,i,j);
			}
		}
	}
	
	/**
	 * Chooses a set of distinct integers from a range 0 to maxValue-1
	 * 
	 * Resulting integers are sorted
	 */
	public static void chooseIntegers(int[] dest, int destOffset, int n, int maxValue) {
		if (n>maxValue) throw new Error("Cannot choose "+n+" items from a set of "+maxValue);
	
		if (maxValue>(4*n)) {
			chooseIntegersBySampling(dest,destOffset,n,maxValue);
			return;
		}
		
		chooseIntegersByExclusion(dest,destOffset,n,maxValue);

	}
	
	private static void chooseIntegersByExclusion(int[] dest, int destOffset, int n, int maxValue) {	
		while (n>0) {
			if ((n==maxValue)||Rand.r(maxValue)<n) {
				dest[destOffset+n-1]=maxValue-1;				
				n--;
			} 
			maxValue--;
		}
	}
		
	@SuppressWarnings("unused")
	private static void chooseIntegersByReservoirSampling(int[] dest, int destOffset, int n, int maxValue) {	
		int found=0;
		for (int i=0; i<maxValue; i++) {
			if (found<n) {
				// fill up array
				dest[destOffset+found]=i;
				found++;
			} else {
				// replace with appropriate probability to ensure fair distribution
				int ni=Rand.r(i+1);
				if (ni<n) {
					dest[destOffset+ni]=i;
				}
			}
		}
	}
	
	private static void chooseIntegersBySampling(int[] dest, int destOffset, int n, int maxValue) {
		SortedSet<Integer> s=new TreeSet<Integer>();
		
		while (s.size()<n) {
			int v=Rand.r(maxValue);
			s.add(v);
		}
		
		for (Integer i: s) {
			dest[destOffset++]=i;
		}
	}
	
	/**
	 * Picks a random item from a given array
	 */
	public static <T> T pick(T[] ts) {
		return ts[Rand.r(ts.length)];
	}
	
	/**
	 * Picks a random item from a given list
	 */
	public static <T> T pick(List<T> ts) {
		return ts.get(Rand.r(ts.size()));
	}
	
	/**
	 * Picks a random item from a given collection
	 */
	public static <T> T pick(Collection<T> ts) {
		int n=ts.size();
		if (n==0) throw new Error("Empty collection!");
		int p=Rand.r(n);
		for (T t: ts) {
			if (p--==0) return t;
		}
		throw new Error("Shouldn't get here!");
	}

	public static void fillUniform(float[] d, int start, int length) {
		for (int i=0; i<length; i++) {
			d[start+i]=Rand.nextFloat();
		}
	}
	
	public static void fillBinary(float[] d, int start, int length) {
		for (int i=0; i<length; i++) {
			d[start+i]=Rand.r(2);
		}
	}

	public static void fillGaussian(float[] d, int start, int length, float u, float sd) {
		for (int i=0; i<length; i++) {
			d[start+i]=(float) Rand.n(u,sd);
		}
	}

	public static void binarySample(float[] temp, int offset, int length) {
		for (int i=offset; i<(offset+length); i++) {
			temp[i]=(Rand.nextFloat()<temp[i]) ? 1f : 0f;
		}
	}


}
