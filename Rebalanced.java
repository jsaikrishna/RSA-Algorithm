import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Rebalanced {
	private static final BigInteger ZERO = null;
	private static BigInteger p;
	private static BigInteger q;
	private static BigInteger r1;
	private static BigInteger r2;
	private static BigInteger d1;
	
	private static BigInteger N;
	private static BigInteger e= new BigInteger("65537");
	private BigInteger phi;
	private static BigInteger d =new BigInteger("65537");
	private BigInteger ONE= new BigInteger("1");
	private BigInteger a;
	
	
	private BigInteger TWO= new BigInteger("2");
	private int length=1024;
	
	public Rebalanced()
	{
		Random rand = new Random();
		p=BigInteger.probablePrime(length,rand);
		q=BigInteger.probablePrime(length,rand);
		
		
		N=(p.multiply(q));
	
		for(BigInteger qw=BigInteger.ZERO; qw.compareTo(N)<0; qw.add(BigInteger.ONE))
		{
			if(((p.subtract(ONE)).gcd(q.subtract(ONE)))==TWO)
			{
				
			System.out.println("Generated p and q is valid");
			break;
			}
			
		else
		{
			System.out.println("Hi");
			p=BigInteger.probablePrime(length,rand);
			q=BigInteger.probablePrime(length,rand);
		}
		}
		
		for(int k=1;k<length/2;k++)
		{
		r1=BigInteger.probablePrime(length,rand);
		r2=BigInteger.probablePrime(length,rand);
		}
		
		for(BigInteger qw=BigInteger.ZERO; qw.compareTo(N)<0; qw.add(BigInteger.ONE))
		{
			if(r1.gcd(p.subtract(ONE))==ONE && r1.gcd(p.subtract(ONE))==ONE)
			{
				if(r1.equals(r2.mod(TWO)))
				{
					System.out.println("Generated r1 and r2 are valid");
				}
			}
			else
			{
				System.out.println("Hello");
				r1=BigInteger.probablePrime(length,rand);
				r2=BigInteger.probablePrime(length,rand);
			}
		}
		a=r1.mod(TWO);
		d1=((r1.subtract(a)).divide(TWO)).mod((p.subtract(ONE).divide(TWO)));
		d=(TWO.multiply(d1)).add(a);
		e=d.modInverse(phi);
		
	}
	public Rebalanced(BigInteger e, BigInteger d, BigInteger N)
	{
		this.e=e;
		this.d=d;
		this.N=N;
	}
	
	
	public static void main(String[] args)
	{
		Rebalanced t= new Rebalanced();
		System.out.println("Please input the string you wnat to encrypt");
		Scanner x= new Scanner(System.in);
		String input;
		input=x.next();
		System.out.println("The entered String is: " + input);
		
		BigInteger in= toBigInteger(input);
		System.out.println("The input string BigInteger form is: "+ in);
		
		BigInteger enmsg=t.encrypt(in);
		System.out.println("The Encypted version of input string is: "+enmsg);
		
		BigInteger der=t.decrypt(enmsg);
		System.out.println("The decypted version of input string is: "+der);
		
		String out=fromBigInteger(der);
		System.out.println("The output string is: "+ out);
		
	}
	public static BigInteger toBigInteger(String input)
	{
	    return new BigInteger(input.getBytes());
	}

	public static String fromBigInteger(BigInteger bar)
	{
	    return new String(bar.toByteArray());
	}
	
	public static BigInteger encrypt(BigInteger msg)
	{
		BigInteger t=msg.modPow(e, N);
		return t;
	}
	
	public static BigInteger decrypt(BigInteger msg)
	{
		BigInteger m1=msg.modPow(d, p);
		BigInteger m2=msg.modPow(d, q);
		BigInteger d1=m1.multiply(q).multiply(q.modInverse(p));
		BigInteger d2=m2.multiply(p).multiply(p.modInverse(q));
		BigInteger d3=d1.add(d2);
		BigInteger w=d3.mod(p.multiply(q));
		return w;
	}

}
