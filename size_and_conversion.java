public class size_and_conversion
     {
        static char hexa[]= "0123456789ABCDEF".toCharArray();
	//Convert from hexa to decimel
	static int hexa_to_deci(String hexa_location){
		int deci_val = 0;
		int multiply = 1;
		for(int i=hexa_location.length()-1;i>=0;i--){
			int j=0;
			for(;j<16;j++)
				if(hexa_location.charAt(i)==hexa[j])
					break;
			deci_val+=(multiply*j);
			multiply*=16;
		}
		return deci_val;
	}

	//Convert from decimal to hexa
	static String decimel_to_hexa(int deci_location){
		String disp = "";
		for(int i=0;i<4;i++){
			char hex = hexa[deci_location%16];
			disp = hex+disp;
			deci_location/=16;
		}
		return disp;
	}

	//COnvert from decimel to hexa for a 8 bit data
	static String decimel_to_hexa_8bit(int deci_location){
		String disp = "";
		for(int i=0;i<2;i++){
			char hex = hexa[deci_location%16];
			disp = hex+disp;
			deci_location/=16;
		}
		return disp;
	}

	//Print the location on the new line
	//to take as input the next instruction
	static void print_the_location(int print_location){
		System.out.print(decimel_to_hexa(print_location)+" : ");
	}

	//Find the size occupied by each location
	
	static int size_of_code(String instruction){
		int size = 0;
		String code = instruction.split(" ")[0];
		if( code.equals("MOV"))
			     return 1;
		
		else if(code.equals("MVI"))
		          return 2;
			
		 else if(code.equals("LXI"))
			     return 3;
	
		else if(code.equals("LDA"))
			     return 3;
		  
		else if(code.equals("STA"))
			    return 3;
			
		else if(code.equals("LHLD"))
			     return 3;
			
		else if(code.equals("SHLD"))
			     return 3;
			
		else if(code.equals("LDAX"))
			     return 1;
			
		else if(code.equals("STAX")) 
			     return 1;
		
		else if(code.equals("XCHG"))
			     return 1;
			
		else if(code.equals("ADD"))
			     return 1;
			
		else if(code.equals("ADI"))
			     return 2;
			
		else if(code.equals("SUB"))
			      return 1;
			
		else if(code.equals("SUI"))
			      return 2;
			
		else if(code.equals("INR"))
			      return 1;
			
		else if(code.equals("DCR"))
			      return 1;
			
		else if(code.equals("INX"))
			      return 1;
			
		else if(code.equals("DCX"))
			     return 1;
			
		else if(code.equals("CMA"))
			     return 1;
			
		else if(code.equals("CMP"))
			     return 1;
			
		else if(code.equals("JMP"))
			     return 3;
			
		else if(code.equals("JZ"))
			     return 3;
			
		else if(code.equals("JNZ"))
			     return 3;
		
		else if(code.equals("JC"))
			     return 3;
			
		else if(code.equals("JNC"))
			     return 3;
			
		else if(code.equals("PCHL"))
			    return 1;

		else if(code.equals("SPHL"))
			    return 1;
			
		else if(code.equals("SET"))
		         return 1; 
			
			else
			     return 1;
			     
		
		
	
	



	
   }
}