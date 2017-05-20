package net.atos.awl.tes;

import java.util.Scanner; 

public class TimeAddition{ 

public static void main(String[] args){ 
int number1 = (int)(System.currentTimeMillis() ); 
int number2 = (int)(System.currentTimeMillis() +1); 

//create a scanner 
Scanner input = new Scanner(System.in); 

System.out.print( "What is " + number1 + " + " + number2 + "? "); 

int answer = input.nextInt(); 

System.out.println( number1 + " + " + number2 + " = " + answer+ " is " + 
(number1 + number2 == answer)); 
} 
} 
