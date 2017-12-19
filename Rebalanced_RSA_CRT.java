import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Rebalanced_RSA_CRT {
	private static final BigInteger ZERO = null;
	private static BigInteger p;
	private static BigInteger q;
	private static BigInteger dp;
	private static BigInteger dq;
	
	
	private static BigInteger N;
	private static BigInteger e= new BigInteger("65537");
	private BigInteger phi;
	private static BigInteger d =new BigInteger("65537");
	private BigInteger ONE= new BigInteger("1");
	
	
	private BigInteger TWO= new BigInteger("2");
	private int length=1024;
	private int k=160;
	
	public Rebalanced_RSA_CRT()
	{
		Random rand = new Random();
		p=BigInteger.probablePrime(length,rand);
		q=BigInteger.probablePrime(length,rand);
		N=(p.multiply(q));
	
		for(BigInteger qw=BigInteger.ZERO; qw.compareTo(N)<0; qw.add(BigInteger.ONE))
		{
		if((p.subtract(ONE).gcd(q.subtract(ONE)).equals(ONE.add(ONE))))
		{
			System.out.println("Generated p and q is valid");
			break;
		}
		else
		{
			p=BigInteger.probablePrime(length,rand);
			q=BigInteger.probablePrime(length,rand);
		}
		}
		
		
		phi=((p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)));
		
		dp=BigInteger.probablePrime(k,rand);
		dq=BigInteger.probablePrime(k,rand);
	
		for(BigInteger i=BigInteger.ZERO;i.compareTo(N)<0;i.add(BigInteger.ONE))
		{
		if((dp.gcd(p.subtract(ONE)).equals(ONE)))
		{
			System.out.println("Generated dp valid");
			break;
		}
		else
		{
			dp=BigInteger.probablePrime(k,rand);
		}
		}
		
		for(BigInteger i=BigInteger.ZERO;i.compareTo(N)<0;i.add(BigInteger.ONE))
		{
		if((dp.gcd(q.subtract(ONE)).equals(ONE)))
		{
			System.out.println("Generated dq valid");
			break;
		}
		else
		{
			dq=BigInteger.probablePrime(k,rand);
		}
		}
		
		BigInteger a=dp.mod(TWO);
		BigInteger da=((dp.subtract(a)).divide(TWO)).mod((p.subtract(ONE)).divide(TWO));
		
		for(BigInteger i=BigInteger.ZERO;i.compareTo(N)<0;i.add(BigInteger.ONE))
		{
		if(da.equals(((dp.subtract(a)).divide(TWO)).mod((p.subtract(ONE)).divide(TWO))))
		{
			System.out.print("Generated dp is valid");
			break;
		}
		else
		{
			da=((dp.subtract(a)).divide(TWO)).mod((p.subtract(ONE)).divide(TWO));
		}
		}
		
		d=(TWO.multiply(da)).add(a);
		
		//BigInteger a= dp.mod(TWO);
//		 BigInteger az= new BigInteger("1");
//		 BigInteger we= new BigInteger("0");
//		boolean flag=true;
//		
//		while(flag)
//		{
//			d=r1.add(az.multiply(p.subtract(ONE)));
//			BigInteger x=d.subtract(r2);
//			System.out.println("hello");
//			
//			if(x.mod(q.subtract(ONE)).equals(d.subtract(ONE)))
//			{
//				System.out.println("hi");
//				flag=false;
//			}
//			else {
//				az.add(ONE);
//			}
//		}
//
		e=d.modInverse(phi);
		
	}
	public Rebalanced_RSA_CRT(BigInteger e, BigInteger d, BigInteger N)
	{
		this.e=e;
		this.d=d;
		this.N=N;
	}
	
	
	public static void main(String[] args)
	{
		Rebalanced_RSA_CRT t= new Rebalanced_RSA_CRT();
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
		BigInteger m1=msg.modPow(dp, p);
		BigInteger m2=msg.modPow(dq, q);
		BigInteger d1=m1.multiply(q).multiply(q.modInverse(p));
		BigInteger d2=m2.multiply(p).multiply(p.modInverse(q));
		BigInteger d3=d1.add(d2);
		BigInteger w=d3.mod(p.multiply(q));
		return w;
	}
}
