package org.generation;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner myScan = new Scanner(System.in);;
		
		String option = "0";
		CajeroAutomatico usuario = new CajeroAutomatico();
		
		do {//Menú
			System.out.println(  " 1) Retirar dinero \n"+
					             " 2) Hacer depósitos \n"
					           + " 3) Consultar saldo \n"
					           + " 4) Quejas \n"
					           + " 5) Ver últimos rendimientos\n"
					           + " 9) Salir del cajero");
			 
			
			option = myScan.nextLine();
			
			switch( option ) {
				case "1": //Opcion 1 Retirar dinero
					
					usuario.retirarDinero();
					break;
					
				case "2": //Opcion 2: hacer depósitos
					usuario.hacerDepositos();
					break;
					
				case "3": //Opcion 3: consultar saldo
					usuario.consultarSaldo();
					break;
			
				case "4": //Quejas
					usuario.quejas();
					break;
					
				case "5": //Ver últimos movimientos
					usuario.ultimosMovimientos();
					break;
					
				case "9":  //Salir del cajero
					break;
					
				default:
					System.out.println("Ingrese una opción válida");
					
			}
			//myScan.close();
		}while( option.charAt(0) !=  '9' );
		
		
		
	}
}
