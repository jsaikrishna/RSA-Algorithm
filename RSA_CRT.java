import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class RSA_CRT {
	private static BigInteger p;
	private static BigInteger q;
	private BigInteger r;
	private static BigInteger N;
	private static BigInteger e= new BigInteger("65537");
	private BigInteger phi;
	private static BigInteger d =new BigInteger("65537");
	private static BigInteger d1 =new BigInteger("65537");
	private static BigInteger d2 =new BigInteger("65537");
	private BigInteger ONE= new BigInteger("1");
	private int length=1024;
	static long avgm=0;
	static int count=0;
	
	public RSA_CRT()
	{
		Random rand = new Random();
		p=BigInteger.probablePrime(length,rand);
		q=BigInteger.probablePrime(length, rand);
		N=(p.multiply(q));
		phi=((p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)));
		e=BigInteger.probablePrime(length, rand); 
		
		for(BigInteger i=BigInteger.ZERO;i.compareTo(N)<0;i.add(BigInteger.ONE))
		{
			
		if((e.gcd(phi).equals(BigInteger.ONE)) && (e.compareTo(phi)<0) && ((BigInteger.ONE).compareTo(e)<0))
		{
			System.out.println("generated e is valid");
			break;
		}
		else
		{
			e=BigInteger.probablePrime(length, rand);
		}
		}
		
		d=e.modInverse(phi);
		d1=(d.mod(p.subtract(BigInteger.ONE)));
		d2=(d.mod(q.subtract(BigInteger.ONE)));
	}
	public RSA_CRT(BigInteger e, BigInteger d, BigInteger N)
	{
		this.e=e;
		this.d=d;
		this.N=N;
	}
	
	
	public static void main(String[] args) throws Exception
	{
		
		long startTime = System.currentTimeMillis();
		long beL=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		  System.out.println("The initial memeory: "+beL);
		  System.out.println("The initial time: "+startTime);
		  avgm=avgm+beL;
		  count=count+1;
		  System.out.println("The added memory: "+avgm);
		  System.out.println("The count: "+count);
		  
		RSA_CRT r= new RSA_CRT();
		System.out.println("Please input the string you wnat to encrypt");
		//Scanner x= new Scanner(System.in);
		String z=readFileAsString("/Users/saikrishna/Desktop/SampleTextFile_1000kb.txt");
		//System.out.println("The entered String is: " + z);
		int textLength=z.length();
		System.out.println("the text length: " + textLength);
		int chunkSize=textLength/100;
		String encrptedMessage=" ";
		String decryptedMessage="";
		int k=0;
		for(int i=0;i<chunkSize;i++)
		{
			String input =z.substring(i, i+100);
			
			BigInteger in= toBigInteger(input);
			//System.out.println("The input string BigInteger form is: "+ in);
			
			
			
			BigInteger enmsg=r.encrypt(in);
			//System.out.println("The Encypted version of input string is: "+enmsg);
			
			
			BigInteger der=r.decrypt(enmsg);
			
			//System.out.println("The decypted version of input string is: "+der);
			
			String out=fromBigInteger(der);
			//System.out.println("The output string is: "+ out);
			decryptedMessage+=out;
		k=i;
		}
		
		System.out.println("subtract length: "+ (textLength-chunkSize*100));
		System.out.println("chunk size:"+ chunkSize*100);
		String input =z.substring(chunkSize*100, textLength);
		
		BigInteger in= toBigInteger(input);
		//System.out.println("The input string BigInteger form is: "+ in);
		
		
		BigInteger enmsg=r.encrypt(in);
		
		
		
		  
		//System.out.println("The Encypted version of input string is: "+enmsg);
		
		BigInteger der=r.decrypt(enmsg);
		
		//System.out.println("The decypted version of input string is: "+der);
		
		String out=fromBigInteger(der);
		//System.out.println("The output string is: "+ out);
		decryptedMessage+=out;
		
		//System.out.println("The output string is: "+ decryptedMessage);
		
		long aeL=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		  long dfL=aeL-beL;
		  
		  System.out.println("The program end memory: "+aeL);
		  System.out.println("The memery difference is: "+dfL);

		  long endTime   = System.currentTimeMillis();
		  long totalTime = endTime - startTime;
		  System.out.println("The program end time: "+endTime);
		  System.out.println("The time difference is: "+totalTime);
		  
		  avgm=avgm/count;
		  
		  System.out.println("The average memory is: "+avgm);
		
	}

	
	public static String readFileAsString(String fileName) throws Exception
	  {
	    String data = "";
	    data = new String(Files.readAllBytes(Paths.get(fileName)));
	    return data;
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
		BigInteger m1=msg.modPow(d1, p);
		BigInteger m2=msg.modPow(d2, q);
		BigInteger d1=m1.multiply(q).multiply(q.modInverse(p));
		BigInteger d2=m2.multiply(p).multiply(p.modInverse(q));
		BigInteger d3=d1.add(d2);
		BigInteger w=d3.mod(p.multiply(q));
		return w;
	}
	
	} 
