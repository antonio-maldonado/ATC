package org.generation;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner myScan = new Scanner(System.in);;
		
		String option = "0";
		CajeroAutomatico usuario = new CajeroAutomatico();
		
		do {					//Men�
			System.out.println(  " 1) Retirar dinero \n"+
					             " 2) Hacer dep�sitos \n"
					           + " 3) Consultar saldo \n"
					           + " 4) Quejas \n"
					           + " 5) Ver �ltimos Movimientos\n"
					           + " 9) Salir del cajero");
			 
			
			option = myScan.nextLine();
			
			switch( option ) {
				case "1": 											//Opcion 1 Retirar dinero
					
					usuario.retirarDinero( myScan );
					break;
					
				case "2": 											//Opcion 2: hacer dep�sitos
					usuario.hacerDepositos( myScan );
					break;
					
				case "3": 											//Opcion 3: consultar saldo
					usuario.consultarSaldo( myScan );
					break;
			
				case "4": 											//Quejas
					usuario.quejas( myScan);
					break;
					
				case "5": 											//Ver �ltimos movimientos
					usuario.ultimosMovimientos( myScan );
					break;
					
				case "9":  											//Salir del cajero
					break;
					
				default:
					System.out.println("Ingrese una opci�n v�lida");
					
			}
			
		}while( option.charAt(0) !=  '9' );
		
		myScan.close();
	}
}
