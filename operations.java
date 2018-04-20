import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedReader;
import java.util.*;
import java.io.*;
import java.io.File;




public class operations {
	static Map<Character,String> registers=new HashMap<Character,String>();
	static Map<Integer , String> memory= new HashMap<Integer , String>();
	static Set<Character> general_purpose_reg= new HashSet<Character>();
	static BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
	static String code[]="HLT RST MOV MVI LXI LDA STA LHLD SHLD LDAX STAX XCHG ADD ADI DAD SUB SUI INR DCR INX DCX CMA CMP JMP JZ JNZ JC JNC RZ RNZ RC RNC RP RM RPE RPO PCHL XTHL SPHL EOF SET".split(" ");
    static int location;//LAST location
	static boolean AC,CF,Z,P,S;//STATUS FLAGS
	static int PC=0,C=1,h=0,d=0,v=0;
	static boolean flag;
     static private Scanner  x;
     static size_and_conversion ob = new size_and_conversion();
     
	     
static void input() throws IOException
  
  {
  	 Scanner s=new Scanner(System.in);
  	 

		
        System.out.println("\n\n------------------------------------------------------------------------");
        System.out.println("|\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\t\t EMULATOR FOR 8085 MICROPROCESSOR\t\t\t|");
		System.out.println("|\t\t\t\t\t\t\t\t\t|");
		System.out.println("|\t\t\t\t\t\t\t\t\t|");
		System.out.println("-------------------------------------------------------------------------\n\n");
        System.out.println("\t*  YOU SHOUD SET MEMOEY VALUES AT FIRST USING 'SET' COMMAND\n");
		System.out.println("\t1.ENTER THE CODE\n\t2.LOAD THE FILE");
		 int t=s.nextInt();
		 if(t==1)
		 {
		take_the_input();
		 }
	 else if(t==2)
		{
		System.out.println("ENTER FILE NAME WITH PATH :");
		  String F_NAME=in.readLine();
		  File z=new File(F_NAME);
		  if(z.exists())
		  {
		 x = new Scanner(new File(F_NAME));
		 take_input();
			}
			else
			{
				System.out.println("FILE NOT FOUND");
				input();
				
			}
		}

}
	
	static void take_the_input() throws IOException{
		System.out.print("\tENTER THE ADDRESS TO WRITE THE CODE: ");

		location = ob.hexa_to_deci(in.readLine());
		PC =location;
		System.out.println();

		while(true){
			ob.print_the_location(location);
			String instruction = in.readLine();
			memory.put(location, instruction);
			if(stop_instruction(instruction))
				break;
			location+=ob.size_of_code(instruction);
		}
		System.out.println();
		System.out.print("\tDO YOU WANT TO ENTER ANY FURTHUR CODE (Y/N) : ");
		String choice = in.readLine();
		System.out.println();
		if(choice.equals("Y"))
			take_the_input();
		else

              print();
      }
            
   static void print() throws IOException
     {
			 System.out.println("\t ENTER EXECUTION TYPE");
			System.out.println("\t\n1.DEBUUGER MODE\n2.SIMPLE EXECUTION\n3.EXIT");
		 String s=in.readLine();
		     if(s.equals("1"))
		     {
			
			       debug();
		}
		else if(s.equals("2"))
		{
			execute();
		}
		else
		{
			System.out.println("EXITING..");
			System.exit(0);
		}
	}
	static void debug ()throws IOException
	 {
	 	
			Scanner b=new Scanner(System.in);
			if(d==0)System.out.println("\tDEBUGGER STATUS->ON");
     	            System.out.println("\t--------------------");
     	    System.out.println("\t \n DEBUGGING COMMANDS:\n");
            if(h==0)System.out.println("PRESS B OR BREAK : TO SET A BREAK POINT");
		    System.out.println("PRESS S OR STEP : TO STEP BY STEP EXECUTION");
		   if(h==0) System.out.println("PRESS R OR RUN : TO RUN A PROGRAM UNTIL A BREAKPOINT OR HLT IS ENCOUNTERED");
		            System.out.println("PRESS P OR PRINT  : TO CHECK MEMORY OR REGISTER VALUES(EX:-P A OR P X2500)");
		            System.out.println("PRESS Q OR QUIT :TO QUIT FROM DEBUGGER MODE");
		            d++;
		    
			String s=in.readLine();
		  if  (s.charAt(0)=='P' || s.equals("PRINT") ) 
		    {
		    	if(general_purpose_reg.contains(s.charAt(2)))
                 System.out.println("\tVALUE OF REGISTER "+ s.charAt(2) +":"+ registers.get(s.charAt(2)));
                else if (ob.hexa_to_deci(s.substring(3,6))<=30000) 
                {
                 System.out.println("\tVALUE OF MEMORY LOCATION "+s.substring(3,7)+":"+ memory.get(ob.hexa_to_deci(s.substring(3,7))));	
                }
                else
                	System.out.println("\tINVALID MEMORY LOCATION ");
                debug();

		    }
		 else if (s.charAt(0)=='R' || s.equals("RUN")) 
		 {
		 	          if(v>0){
                    if(execute2(v)==1)
                    {
                       h++;
                      debug();
                  }
              }
                      else
                      {
                     	execute();
                     }

		 }

		    	else if (s.charAt(0)=='S' || s.equals("STEP"))
		    		{
		    			
		    			h++;	execute1(); ;
		    			 
		    		}
		    	else if(s.charAt(0)=='B' || s.equals("BREAK")) 
		    		{
		    			System.out.println("\tENTER LINE NUMBER TO SET BREAKPOINT ");
		    			 v=b.nextInt();
		    			System.out.println("\tBREAKPOINT SET AT LINE NUMBER :"+v);
		    			debug();
		    		}
		    		
		    				else if(s.equals("QUIT")||s.charAt(0)=='Q')
		    				{
                              print();
                            }
               
            

	}
	static void take_input() throws IOException{
		   while(x.hasNext())
		   {
			   System.out.println("\tENTER STARTING ADDRESS OF CODE :");
			   String addr=in.readLine();
			   location =ob.hexa_to_deci(addr);
			   while(true){
			ob.print_the_location(location);
			String instruction = x.nextLine();
			System.out.println(instruction);
			memory.put(location, instruction);
			if(stop_instruction(instruction))
				break;
			location+=ob.size_of_code(instruction);
			   }
		   }
		   print();	
		   
	}		   
		   
	//Execute the instructions
	static void execute1() throws IOException{
           System.out.println("ENTER ADDRESS OF LINE YOU WANT TO EXECUTE :");
         PC=ob.hexa_to_deci(in.readLine());
			String instruct = memory.get(PC);
			if(stop_instruction_for_exec(instruct))
		              	 print();
			perform_the_instruction(instruct);
	         System.out.println(instruct);
	        	PC+=ob.size_of_code(instruct);
		              
			    debug();
			

		}
	


	//Execute the instructions
	static void execute() throws IOException{

        System.out.println(memory);
		 System.out.println(registers);
		System.out.println("\tENTER STARTING ADDRESS OF PROGRAM TO BEGIN EXECUTION :");
	       PC=ob.hexa_to_deci(in.readLine());
	     System.out.println("\t\t\nNOW EXECUTION WILL BEGIN...");
  		while(true){
			flag = false;
			String instruct = memory.get(PC);
			if(stop_instruction_for_exec(instruct))
				break;
		   perform_the_instruction(instruct);
		   System.out.println(registers);
			
			if(!flag)
				PC=PC+ob.size_of_code(instruct);
		}
		 
	}

static int execute2(int s) throws IOException{
	 System.out.println("ENTER STARTING ADDRESS OF CODE :");
	 PC=ob.hexa_to_deci(in.readLine());
         
         int c=0;
       while(c<s)
       {
			c++;
			String instruct = memory.get(PC);
		    perform_the_instruction(instruct);
			if(stop_instruction_for_exec(instruct))
		             {
		             	break;
		              	
		              }
		    System.out.println(instruct);
				PC+=ob.size_of_code(instruct);
		             
		       }

		       if(c==s) {return 1;}
            else

			return 0;
		}	
	
	

	static void output_process() throws IOException{
		System.out.println("TO QUIT THE PROGRAM TYPE N ");
		System.out.println("VALUES OF FLAG");
		 System.out.println("AC :"+AC);
		 System.out.println("CF :"+CF);
		 System.out.println("Z:"+Z);
		 System.out.println("P:"+P);
		 System.out.println("S:"+S);

		while(true){
			String check = in.readLine();
			if(check.equals("N"))
				break;
			try{
				if(check.length()==1)
					System.out.println(check+" - "+registers.get(check.charAt(0)));
				else{
					int addre = ob.hexa_to_deci(check);
					System.out.println(check+" - "+memory.get(addre));
				}
			}
			catch(Exception e){
				System.out.println(check+" - 00");
			}
		}
		 
	}
	
	static boolean stop_instruction(String instruction){
		String code = instruction.split(" ")[0];
		switch(code){
		case "HLT":
		case "EOF":
			return true;
		default:
			return false;
		}
	}
	
	static boolean stop_instruction_for_exec(String instruction){
		String code = instruction.split(" ")[0];
		switch(code){
		case "HLT":
		case "EOF":
			return true;
		default:
			return false;
		}
	}


	static void add_the_general_purpose_reg(){
		general_purpose_reg.add('A');
		general_purpose_reg.add('B');
		general_purpose_reg.add('C');
		general_purpose_reg.add('D');
		general_purpose_reg.add('E');
		general_purpose_reg.add('H');
		general_purpose_reg.add('L');
	}

	//Perform the instruction 
		static void perform_the_instruction(String instruction) throws IOException
		{
			String code = instruction.trim().split(" ")[0];
			String s=instruction.trim().split(" ")[1];
			++C;
			switch(code){
			case "MOV":
			    if(s.length()==3 && s.charAt(0)!='M')
				perform_mov(instruction);
			  else
			  	error();
				break;
			case "MVI":
			    if(s.length()==4 && general_purpose_reg.contains(s.charAt(0)))
				perform_mvi(instruction);
			    else
			    	error();
				break;
			case "LXI":
			    if(s.length()==6 && general_purpose_reg.contains(s.charAt(0)) && ob.hexa_to_deci(s.substring(3))<30000)
				perform_lxi(instruction);
			   else
			   	  error();
				break;
			case "LDA":
                if(s.length()==4 && ob.hexa_to_deci(s)<30000)
				perform_lda(instruction);
			   else
			   	   error();
				break;
			case "STA":
			     if(s.length()==4 && ob.hexa_to_deci(s)<30000)
				perform_sta(instruction);
			       else
			       	  error();
				break;
			case "LHLD":
			     if(s.length()==4 && ob.hexa_to_deci(s)<30000)
				perform_lhld(instruction);
			       else
			       	  error();
				break;
			case "SHLD":
			     if(s.length()==4 && ob.hexa_to_deci(s)<30000)
				perform_shld(instruction);
			       else
			       	   error();
				break;
			case "LDAX":
			     if(s.length()==1 && general_purpose_reg.contains(s.charAt(0)))
				perform_ldax(instruction);
			      else
			      	  error();
				break;
			case "STAX": 
			     if(s.length()==1 && general_purpose_reg.contains(s.charAt(0)))
				perform_stax(instruction);
			       else
			       	    error();
				break;
			case "XCHG":
			     if(s.length()==0)
				perform_xchg(instruction);
			     else
			     	error();
				break;
			case "ADD":
			      if(general_purpose_reg.contains(s.charAt(0)))
				perform_add(instruction);
			       else
			       	  error();
				break;
			case "ADI":
			      if(general_purpose_reg.contains(s.charAt(0)))
				perform_adi(instruction);
			     else
			       	  error();
				break;
			case "SUB":
			    if(general_purpose_reg.contains(s.charAt(0)))
				perform_sub(instruction);
			       else
			       	  error();
				break;
			case "SUI":
			     if(general_purpose_reg.contains(s.charAt(0)))
				perform_sui(instruction);
			     else
			       	  error();
				break;
			case "INR":
			    if(general_purpose_reg.contains(s.charAt(0)))
				perform_inr(instruction);
				  else
			       	  error();
			       	break;
			case "DCR":
			    if(general_purpose_reg.contains(s.charAt(0)))
				perform_dcr(instruction);
			       else
			       	  error();
				break;
			case "INX":
			     char x=s.charAt(0);
			    if(x=='B'||x=='D'||x=='H')
				perform_inx(instruction);
			        else
			       	  error();
				break;
			case "DCX":
			    char y=s.charAt(0);
			    if(y=='B'||y=='D'||y=='H')
				perform_dcx(instruction);
			       else
			       	  error();
				break;
			case "CMA":
				perform_cma(instruction);
				break;
			case "CMP":
				perform_cmp(instruction);
				break;
			case "JMP":
				perform_jmp(instruction);
				break;
			case "JZ":
				perform_jz(instruction);
				break;
			case "JNZ":
				perform_jnz(instruction);
				break;
			case "JC":
				perform_jc(instruction);
				break;
			case "JNC":
				perform_jnc(instruction);
				break;
			case "PCHL":
				perform_pchl(instruction);
				break;
			case "SET":
			   if(s.length()==7)
			    perform_set(instruction);
			   else
			       	  error();
			    break;
			case "HLT":
			      break;
			case "EOF":
			      break;
			default :
			 System.out.println("\t\tERROR AT LINE NO. "+(C-1)+": INVALID INSTRUCTION");
			System.out.print("\t"+code+" ");
			System.out.println("\t\t ENTER YOUR CODE AGAIN..");
		        input();
			 break;
			}

		}
	static void error() throws IOException
	        {
	        	System.out.println("\t\tINVALID OPERANDS AT LINE NO :" +(C-1));
	        	System.out.println("\t\t ENTER YOUR CODE AGAIN..");
	        	input();

	        }
	
	//Get memory location from HL pair
	static int memory_location_hl(){
		int hl_location = ob.hexa_to_deci(registers.get('H')+registers.get('L'));
		return hl_location;
	}

	 static void perform_set(String passed)
	 {
		int location=ob.hexa_to_deci(passed.substring(4,8));
			memory.put(location,passed.substring(9,passed.length()));
	}

	 
	static void perform_mov(String passed){
		int type = type_of_mov(passed);
		if(type==1)
			mov_memory_to_reg(passed);
		else if(type==2)
			mov_reg_to_memory(passed);
		else if(type==3)
			mov_reg_to_reg(passed);
	}
	
	static int type_of_mov(String passed){
		if(passed.charAt(4)=='M' && general_purpose_reg.contains(passed.charAt(6)))
			return 2;
		else if(passed.charAt(6)=='M' && general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else if(general_purpose_reg.contains(passed.charAt(6)) && general_purpose_reg.contains(passed.charAt(4)))
			return 3;
		else
			return 0;
	}

	static void mov_memory_to_reg(String passed){
		int memory_location = memory_location_hl();
		registers.put(passed.charAt(4),memory.get(memory_location));
	}
	
	static void mov_reg_to_memory(String passed){
		int memory_location = memory_location_hl();
		memory.put(memory_location, registers.get(passed.charAt(6)));
	}
	static void mov_reg_to_reg(String passed){
		registers.put(passed.charAt(4),registers.get(passed.charAt(6)));
	}


	
	
	static void perform_mvi(String passed){
		int type = type_of_mvi(passed);
		if(type==1)
			mvi_to_reg(passed);
		else if(type==2)
			mvi_to_mem(passed);
	}
	//Find type of mvi
	static int type_of_mvi(String passed){
		if(passed.charAt(4)=='M')
			return 2;
		else if(general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else
			return 0;
	}
	//Type 1 of MVI
	static void mvi_to_reg(String passed){
		registers.put(passed.charAt(4), passed.substring(6));
	}
	//Type 2 of MVi
	static void mvi_to_mem(String passed){
		int memory_location = memory_location_hl();
		memory.put(memory_location, passed.substring(6));
	}


	//LXI Command
	/*
	 Type 1 Load immediately the data into the register pair
	 */

	
	//Call appropriate method acc to type
	static void perform_lxi(String passed){
		int type = type_of_lxi(passed);
		if(type==1)
			lxi_to_reg_pair(passed);
	}
	//Find type of LXI
	static int type_of_lxi(String passed){
		char fourth = passed.charAt(4);
		if(fourth=='B' || fourth=='D' || fourth=='H')
			return 1;
		else
			return 0;
	}
	//Type 1 of LXI
	static void lxi_to_reg_pair(String passed){
		char fourth = passed.charAt(4);
		if(fourth=='B'){
			registers.put('B', passed.substring(6,8));
			registers.put('C', passed.substring(8));
		}
		else if(fourth=='D'){
			registers.put('D', passed.substring(6,8));
			registers.put('E', passed.substring(8));
		}
		else if(fourth=='H'){
			registers.put('H', passed.substring(6,8));
			registers.put('L', passed.substring(8));
		}
	}



	//LDA Command
	/*
	 Type 1 Load the data from mem to A
	 */

	
	//Call appropriate method acc to type
	static void perform_lda(String passed){
		int type = type_of_lda(passed);
		if(type==1)
			lda_from_mem(passed);
	}
	//Find type of LDA
	static int type_of_lda(String passed){
		return 1;
	}
	//Type 1 of LDA
	static void lda_from_mem(String passed){
		registers.put('A', memory.get(ob.hexa_to_deci(passed.substring(4))));
	}



	//STA Command
	/*
	 Type 1 Store the data from A to mem
	 */

	
	//Call appropriate method acc to type
	static void perform_sta(String passed){
		int type = type_of_sta(passed);
		if(type==1)
			sta_to_mem(passed);
	}
	//Find type of LDA
	static int type_of_sta(String passed){
		return 1;
	}
	//Type 1 of LDA
	static void sta_to_mem(String passed){
		memory.put(ob.hexa_to_deci(passed.substring(4)),registers.get('A'));
		//System.out.println(memory.get(ob.hexa_to_deci(passed.substring(4))));
	}


	//LHLD Command
	/*
	 Type 1 Load the data from consecutive memory to HL pair direct
	 */

	//Call appropriate method acc to type
	static void perform_lhld(String passed){
		int type = type_of_lhld(passed);
		if(type==1)
			lhld_from_mem(passed);
	}
	//Find type of LHLD
	static int type_of_lhld(String passed){
		return 1;
	}
	//Type 1 of LHLD
	static void lhld_from_mem(String passed){
		registers.put('L',memory.get(ob.hexa_to_deci(passed.substring(5))));
		int add_for_H = ob.hexa_to_deci(passed.substring(5))+1;
		registers.put('H', memory.get((add_for_H)));
	}



	//SHLD Command
	/*
	 Type 1 Store the data from HL pair to memory
	 */

	
	//Call appropriate method acc to type
	static void perform_shld(String passed){
		int type = type_of_shld(passed);
		if(type==1)
			shld_to_mem(passed);
	}
	//Find type of SHLD
	static int type_of_shld(String passed){
		return 1;
	}
	//Type 1 of SHLD
	static void shld_to_mem(String passed){
		int memo_location = ob.hexa_to_deci(passed.substring(5));
		memory.put(memo_location, registers.get('L'));
		memo_location++;
		memory.put(memo_location, registers.get('H'));
	}


	//LDAX Command
	/*
	 * Type 1 Load the A with the data from the memory having location as register pair content
	 */

	//Call appropriate method acc to type
	static void perform_ldax(String passed){
		int type = type_of_ldax(passed);
		if(type==1)
			ldax_from_mem(passed);
	}
	//Find type of LDAX
	static int type_of_ldax(String passed){
		char fifth=passed.charAt(5);
		if( fifth=='D' || fifth=='B')
		return 1;
	      else
	      	return 0;
	}
	//Type 1 of LDAX
	static void ldax_from_mem(String passed){
		char fifth=passed.charAt(5);
	     if(fifth=='B')
	    	registers.put('A',memory.get(ob.hexa_to_deci(registers.get('B')+registers.get('C'))));
           else if(fifth=='D')
	    		registers.put('A',memory.get(ob.hexa_to_deci(registers.get('D')+registers.get('E'))));
	}


	//STAX Command
	/*
	 * Type 1 Store the content of A to the memory location specified by the content of the  HL pair
	 */

	//Call appropriate  method acc to type
	static void perform_stax(String passed){
		int type = type_of_stax(passed);
		if(type==1)
			stax_to_mem(passed);
	}
	//Find type of STAX
	static int type_of_stax(String passed){
		return 1;
	}
	//Type 1 of STAX
	static void stax_to_mem(String passed){
		memory.put(ob.hexa_to_deci(registers.get('H')+registers.get('L')),registers.get('A'));
	}


	//XCHG Command
	/*
	 * Type 1 Exchange the contents of DE and HL reg pair
	 */

	//Call appropriate method according to type
	static void perform_xchg(String passed){
		int type = type_of_xchg(passed);
		if(type==1)
			xchg_de_with_hl(passed);
	}
	//Find type of XCHG
	static int type_of_xchg(String passed){
		return 1;
	}
	//Type 1 of XCHG
	static void xchg_de_with_hl(String passed){
		String temporary = registers.get('D');
		registers.put('D', registers.get('H'));
		registers.put('H',temporary);
		temporary = registers.get('E');
		registers.put('E',registers.get('L'));
		registers.put('L',temporary);
	}


	//Modify the status flags
	static void modify_status(String content){
		String binary_A = Integer.toBinaryString(ob.hexa_to_deci(content));
		int length = binary_A.length();
		if(length==32)
			binary_A = binary_A.substring(24);
		int ones = 0;
		for(int i=0;i<length;i++)
			if(binary_A.charAt(i)=='1')
				ones++;
		for(int i=length;i<8;i++)
			binary_A = "0"+binary_A;
		S = binary_A.charAt(0)=='1'?true:false;
		Z = ones==0?true:false;
		P = ones%2==0?true:false;
	}
	

	//ADD Command
	/*
	 * Type 1 Add the register to A
	 * Type 2 Add the memory content to A
	 */

	
	//Call appropriate method acc to type
	static void perform_add(String passed){
		int type = type_of_add(passed);
		if(type==1)
			add_with_reg(passed);
		else if(type==2)
			add_with_mem(passed);
	}
	//Find type of ADD
	static int type_of_add(String passed){
		if(general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else if(passed.charAt(4)=='M')
			return 2;
		else
			return 0;
	}
	//Type 1 of ADD
	static void add_with_reg(String passed){
		int sum = ob.hexa_to_deci(registers.get('A'));
		/*
		 * This will be valid only when sign arithematic is being performed
		 * if(S)
			sum*=-1;
		 */
		sum+=(ob.hexa_to_deci(registers.get(passed.charAt(4))));
		CF = sum>255?true:false;
		registers.put('A', ob.decimel_to_hexa_8bit(sum));
		modify_status(registers.get('A'));
	}
	//Type 2 of ADD
	static void add_with_mem(String passed){
		int sum = ob.hexa_to_deci(registers.get('A'));
		/*
		 * This will be valid only when sign arithematic is being performed
		 * if(S)
			sum*=-1;
		 */
		sum+=ob.hexa_to_deci(memory.get(memory_location_hl()));
		CF = sum>255?true:false;
		registers.put('A', ob.decimel_to_hexa_8bit(sum));
		modify_status(registers.get('A'));
	}



	
	//ADI Command
	/*
	 * Type 1 Add the immediate data to the A
	 */

	//Call appropriate method acc to type
	static void perform_adi(String passed){
		int type = type_of_adi(passed);
		switch(type){
		case 1:
			adi_data_with_acc(passed);
			break;
		}
	}

	//Find type of ADI
	static int type_of_adi(String passed){
		return 1;
	}

	//Type 1 of ADI
	static void adi_data_with_acc(String passed){
		int sum = ob.hexa_to_deci(registers.get('A'));
		sum+=ob.hexa_to_deci(passed.substring(4));
		CF = sum>255?true:false;
		registers.put('A',ob.decimel_to_hexa_8bit(sum));
		modify_status(registers.get('A'));
	}

	//SUB Command
	/*
	 * Type 1 Subtract register from A
	 * Type 2 Subtract the memory data from A
	 */

	//Call the app method acc to type
	static void perform_sub(String passed){
		int type = type_of_sub(passed);
		switch(type){
		case 1:
			sub_with_reg(passed);
			break;
		case 2:
			sub_with_mem(passed);
			break;
		}
	}

	//Find the type of SUB
	static int type_of_sub(String passed){
		if(general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else if(passed.charAt(4)=='M')
			return 2;
		else
			return 0;
	}

	//Type 1 of SUB
	static void sub_with_reg(String passed){
		int subt = ob.hexa_to_deci(registers.get('A'));
		int minu = ob.hexa_to_deci(registers.get(passed.charAt(4)));
		minu = 256-minu;
		minu%=256;
		subt+=minu;
		CF = subt>255?true:false;
		registers.put('A',ob.decimel_to_hexa_8bit(subt));
		modify_status(registers.get('A'));
	}

	//Type 2 of SUB
	static void sub_with_mem(String passed){
		int subt = ob.hexa_to_deci(registers.get('A'));
		int minu = ob.hexa_to_deci(memory.get(memory_location_hl()));
		minu = 256-minu;
		minu%=256;
		subt+=minu;
		CF = subt>255?true:false;
		registers.put('A',ob.decimel_to_hexa_8bit(subt));
		modify_status(registers.get('A'));
	}



	//SUI Command
	/*
	 * Type 1 Subtract immediate data from A
	 */


	//Call the appropriate method according to type
	static void perform_sui(String passed){
		int type = type_of_sui(passed);
		switch(type){
		case 1:
			sui_with_acc(passed);
			break;
		}
	}

	//Find the type of SUI
	static int type_of_sui(String passed){
		return 1;
	}

	//Type 1 of SUI
	static void sui_with_acc(String passed){
		int subt = ob.hexa_to_deci(registers.get('A'));
		int minu = ob.hexa_to_deci(passed.substring(4));
		minu = 256-minu;
		minu%=256;
		subt+=minu;
		CF = subt>255?true:false;
		registers.put('A', ob.decimel_to_hexa_8bit(subt));
		modify_status(registers.get('A'));
	}



	//INR Command
	/*
	 * Type 1 Increment the value of registers
	 * Type 2 Increment the value of the memory location
	 */

	

	//Call the appropriate method according to type
	static void perform_inr(String passed){
		int type = type_of_inr(passed);
		switch(type){
		case 1:
			inr_reg(passed);
			break;
		case 2:
			inr_mem(passed);
			break;
		}
	}

	//Find the type of INR
	static int type_of_inr(String passed){
		if(general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else if(passed.charAt(4)=='M')
			return 2;
		else
			return 0;
	}

	//Type 1 of INR
	static void inr_reg(String passed){
		int val = ob.hexa_to_deci(registers.get(passed.charAt(4)));
		val++;
		registers.put(passed.charAt(4), ob.decimel_to_hexa_8bit(val));
		modify_status(registers.get(passed.charAt(4)));
	}

	//Type 2 of INR
	static void inr_mem(String passed){
		int val = ob.hexa_to_deci(memory.get(memory_location_hl()));
		val++;
		memory.put(memory_location_hl(), ob.decimel_to_hexa_8bit(val));
		modify_status(memory.get(memory_location_hl()));
	}



	//DCR Command
	/*
	 * Type 1 Decrement the value of registers
	 * Type 2 Decrement the value of the memory location
	 */


	//Call the appropriate method according to type
	static void perform_dcr(String passed)
	{
		int type = type_of_dcr(passed);
		switch(type){
		case 1:
			dcr_reg(passed);
			break;
		case 2:
			dcr_mem(passed);
			break;
		}
	}

	//Find the type of INR
	static int type_of_dcr(String passed){
		if(general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else if(passed.charAt(4)=='M')
			return 2;
		else
			return 0;
	}

	//Type 1 of DCR
	static void dcr_reg(String passed){
		int val = ob.hexa_to_deci(registers.get(passed.charAt(4)));
		val--;
		registers.put(passed.charAt(4), ob.decimel_to_hexa_8bit(val));
		modify_status(registers.get(passed.charAt(4)));
	}

	//Type 2 of DCR
	static void dcr_mem(String passed){
		int val = ob.hexa_to_deci(memory.get(memory_location_hl()));
		val--;
		memory.put(memory_location_hl(), ob.decimel_to_hexa_8bit(val));
		modify_status(memory.get(memory_location_hl()));
	}



	//INX Command
	/*
	 * Type 1 Increment the data contained in the register pair
	 */



	//Call the appropriate method acc to type
	static void perform_inx(String passed){
		int type = type_of_inx(passed);
		switch(type){
		case 1:
			inx_rp(passed);
			break;
		}
	}

	//Find the type of INX
	static int type_of_inx(String passed){
		return 1;
	}

	//Type 1 of INX
	static void inx_rp(String passed){
		int h,l;
		switch(passed.charAt(4)){
		case 'B':
			h = ob.hexa_to_deci(registers.get('B'));
			l = ob.hexa_to_deci(registers.get('C'));
			l++;
			h+=(l>255?1:0);
			registers.put('C',ob.decimel_to_hexa_8bit(l));
			registers.put('B',ob.decimel_to_hexa_8bit(h));
			break;
		case 'D':
			h = ob.hexa_to_deci(registers.get('D'));
			l = ob.hexa_to_deci(registers.get('E'));
			l++;
			h+=(l>255?1:0);
			registers.put('E',ob.decimel_to_hexa_8bit(l));
			registers.put('D',ob.decimel_to_hexa_8bit(h));
			break;
		case 'H':
			h = ob.hexa_to_deci(registers.get('H'));
			l = ob.hexa_to_deci(registers.get('L'));
			l++;
			h+=(l>255?1:0);
			registers.put('L',ob.decimel_to_hexa_8bit(l));
			registers.put('H',ob.decimel_to_hexa_8bit(h));
			break;
		}
	}



	//DCX Command
	/*
	 * Type 1 Decrement the data contained in the register pair
	 */

	
	//Call the appropriate method acc to type
	static void perform_dcx(String passed){
		int type = type_of_inx(passed);
		switch(type){
		case 1:
			dcx_rp(passed);
			break;
		}
	}

	//Find the type of INX
	static int type_of_dcx(String passed){
		return 1;
	}

	//Type 1 of INX
	static void dcx_rp(String passed){
		int h,l;
		switch(passed.charAt(4)){
		case 'B':
			h = ob.hexa_to_deci(registers.get('B'));
			l = ob.hexa_to_deci(registers.get('C'));
			l--;
			if(l==-1)
				h--;
			if(l==-1)
				l=255;
			if(h==-1)
				h=255;
			registers.put('C',ob.decimel_to_hexa_8bit(l));
			registers.put('B',ob.decimel_to_hexa_8bit(h));
			break;
		case 'D':
			h = ob.hexa_to_deci(registers.get('D'));
			l = ob.hexa_to_deci(registers.get('E'));
			l--;
			if(l==-1)
				h--;
			if(l==-1)
				l=255;
			if(h==-1)
				h=255;
			h+=(l>255?1:0);
			registers.put('E',ob.decimel_to_hexa_8bit(l));
			registers.put('D',ob.decimel_to_hexa_8bit(h));
			break;
		case 'H':
			h = ob.hexa_to_deci(registers.get('H'));
			l = ob.hexa_to_deci(registers.get('L'));
			l--;
			if(l==-1)
				h--;
			if(l==-1)
				l=255;
			if(h==-1)
				h=255;
			h+=(l>255?1:0);
			registers.put('L',ob.decimel_to_hexa_8bit(l));
			registers.put('H',ob.decimel_to_hexa_8bit(h));
			break;
		}
	}



	
	//CMA Command
	/*
	 * Type 1 Complement the A
	 */

	
	//Call the appropriate method according to type
	static void perform_cma(String passed){
		int type = type_of_cma(passed);
		switch(type){
		case 1:
			cma_with_acc(passed);
			break;
		}
	}

	//Find the type of CMA
	static int type_of_cma(String passed){
		return 1;
	}

	//Type 1 of CMA
	static void cma_with_acc(String passed){
		int val = ob.hexa_to_deci(registers.get('A'));
		String bin = Integer.toBinaryString(val);
		for(int i=bin.length();i<8;i++)
			bin = "0"+bin;
		val=0;
		for(int i=7;i>=0;i--)
			if(bin.charAt(i)=='0')
				val+=(Math.pow(2,7-i));
		registers.put('A',ob.decimel_to_hexa_8bit(val));
	}



	//CMP Command
	/*
	 * Type 1 Compare the register with A
	 * Type 2 Compare the memory with A
	 */


	//Call the appropriate method according to type
	static void perform_cmp(String passed){
		int type = type_of_cmp(passed);
		switch(type){
		case 1:
			cmp_with_reg(passed);
			break;
		case 2:
			cmp_with_mem(passed);
			break;
		}
	}

	//Find the type of CMP
	static int type_of_cmp(String passed){
		if(general_purpose_reg.contains(passed.charAt(4)))
			return 1;
		else if(passed.charAt(4)=='M')
			return 2;
		else
			return 0;
	}

	//Type 1 of CMP
	static void cmp_with_reg(String passed){
		int val1 = ob.hexa_to_deci(registers.get('A'));
		int val2 = ob.hexa_to_deci(registers.get(passed.charAt(4)));
		S = val1>=val2?false:true;
		val2 = 256-val2;
		val2%=256;
		val1+=val2;
		CF = val1<255?true:false;
		String b = Integer.toBinaryString(val1);
		int ones=0;
		for(int i=0;i<b.length();i++)
			if(b.charAt(i)=='1')
				ones++;
		Z = ones==0?true:false;
		P = ones%2==0?true:false;
	}

	//Type 2 of CMP
	static void cmp_with_mem(String passed)
	{
		int val1 = ob.hexa_to_deci(registers.get('A'));
		int val2 = ob.hexa_to_deci(memory.get(memory_location_hl()));
		S = val1>=val2?false:true;
		val2 = 256-val2;
		val2%=256;
		val1+=val2;
		CF = val1<255?true:false;
		String b = Integer.toBinaryString(val1);
		int ones=0;
		for(int i=0;i<b.length();i++)
			if(b.charAt(i)=='1')
				ones++;
		Z = ones==0?true:false;
		P = ones%2==0?true:false;
	}




	//BRANCH GROUP

	//Get the Program Status Word
	static int psw(){
		int psw = 0;
		psw+=(CF?1:0);
		psw+=(P?4:0);
		psw+=(AC?16:0);
		psw+=(Z?64:0);
		psw+=(S?128:0);
		return psw;
	}




	//Complete the jump requirements
	static void complete_jump_req(String passed){
		//		String program_counter = ob.decimel_to_hexa(PC);
		//		fill_the_stack(program_counter.substring(0,2),program_counter.substring(2,4),psw());

			PC = ob.hexa_to_deci(passed);
		flag =true;
	}





	//JMP Command
	/*
	 * Type 1 Unconditional jump
	 */



	//Call the appropriate method according to type
	static void perform_jmp(String passed){
		int type = type_of_jmp(passed);
		switch(type){
		case 1:
			jump_without_condition(passed);
			break;
		}
	}

	//Find the type of JMP
	static int type_of_jmp(String passed)
	{
		return 1;
	}


	//Type 1 of JMP
	static void jump_without_condition(String passed){
		complete_jump_req(passed.substring(4));
	}




	//JZ Command
	/*
	 * Type 1 Jump if zero is set
	 */


	//Call the appropriate method according to type
	static void perform_jz(String passed){
		int type = type_of_jz(passed);
		switch(type){
		case 1:
			jump_when_zero(passed);
			break;
		}
	}

	//Find the type of JZ
	static int type_of_jz(String passed){
		return 1;
	}

	//Type 1 of JZ
	static void jump_when_zero(String passed){
		if(Z)
			complete_jump_req(passed.substring(3));
	}



	//JNZ Command
	/*
	 * Type 1 Jump if zero is not set
	 */

	//Call the appropriate method according to type
	static void perform_jnz(String passed){
		int type = type_of_jnz(passed);
		switch(type){
		case 1:
			jump_when_not_zero(passed);
			break;
		}
	}

	//Find the type of JNZ
	static int type_of_jnz(String passed){
		return 1;
	}

	//Type 1 of JNZ
	static void jump_when_not_zero(String passed){
		if(!Z)
			complete_jump_req(passed.substring(4));
	}



	//JC Command
	/*
	 * Type 1 Jump if carry is set
	 */


	//Call the appropriate method according to type
	static void perform_jc(String passed){
		int type = type_of_jc(passed);
		switch(type){
		case 1:
			jump_when_carry(passed);
			break;
		}
	}

	//Find the type of JC
	static int type_of_jc(String passed){
		return 1;
	}

	//Type 1 of JC
	static void jump_when_carry(String passed){
		if(CF)
			complete_jump_req(passed.substring(3));
	}



	//JNC Command
	/*
	 * Type 1 Jump if carry is not set
	 */


	//Call the appropriate method according to type
	static void perform_jnc(String passed){
		int type = type_of_jnc(passed);
		switch(type){
		case 1:
			jump_when_carry_not(passed);
			break;
		}
	}

	//Find the type of JNC
	static int type_of_jnc(String passed){
		return 1;
	}

	//Type 1 of JNC
	static void jump_when_carry_not(String passed){
		if(!CF)
			complete_jump_req(passed.substring(4));
	}



	//JP Command
	/*
	 * Type 1 Jump if sign is not set
	 */


	//Call the appropriate method according to type
	static void perform_jp(String passed){
		int type = type_of_jp(passed);
		switch(type){
		case 1:
			jump_when_sign_not(passed);
			break;
		}
	}

	//Find the type of JP
	static int type_of_jp(String passed){
		return 1;
	}

	//Type 1 of JZ
	static void jump_when_sign_not(String passed){
		if(!S)
			complete_jump_req(passed.substring(3));
	}



	
	//PCHL Command
	/*
	 * Type 1 Jump to location given by HL pair
	 */


	//Call the appropriate method according to type
	static void perform_pchl(String passed){
		int type = type_of_pchl(passed);
		switch(type){
		case 1:
			jump_to_pchl(passed);
			break;
		}
	}

	//Find the type of PCHL
	static int type_of_pchl(String passed)
	{
		return 1;
	}

	//Type 1 of PCHL
	static void jump_to_pchl(String passed){
		String ad = registers.get('H')+registers.get('L');
		PC = ob.hexa_to_deci(ad);
		flag = true;
	}
}




	










	
	
