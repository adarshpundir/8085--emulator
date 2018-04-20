import java.io.IOException;
import java.util.*;
public class main
{
	static Scanner x;
        static operations ob = new operations();

	public static void main(String args[]) throws IOException
	{

        
		
		ob.add_the_general_purpose_reg();
		     
		
		ob.input();

		
		System.out.println("\t\tYOUR CODE EXECUTED SUCESFULLY.\n");
		System.out.println("\nENTER THE MEMORY LOCATIONS AND REGISTER VALUES YOU WANT TO CHECK : ");
		ob.output_process();
		x.close();
	}
	
     
 }  