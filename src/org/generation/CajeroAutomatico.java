package org.generation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CajeroAutomatico  {
	//---------------------------------Atributos----------------------------------------
	private double saldo = 10_000.0; 												//Se  crea el atributo saldo y le asignamos  10_000 como saldo inicial
	private double MAX_AMOUNT_WITHDRAW = 6_000.0  ; 								//M�xima cantidad a retirar
	private ArrayList<String> ultimoMovimientosArray = new ArrayList<String>(); 	//Arreglo para guardar los ultimos movimientos
	private String MOVIMIENTO_RETIRO = " Retiro de $"; 								//Cadena de texto para imprimir cuando se haga un retiro
	private String DONACION = " + $200 de donaci�n para Ch30"; 						//Cadena de texto para imprimir cuando se haga una donacion
	private String DEPOSITO_TDC = " Dep�sito a TC de $"; 							//Cadena de texto para imprimir cuando se haga un deposito a la tarjeta de cr�dito
	private String DEPOSITO_CHEQUES = " Dep�sito a cuenta de cheques de $"; 		//Cadena de texto para imprimir cuando se haga un retiro
	private double DONACION_CH30 = 200.0; 											//Constante donacion para CH30
	private int NUMERO_ULTIMOS_MOVIMIENTOS = 5; 									//Constante para cuantos ultimos movimientos
	
	//------------------------------------M�todos---------------------------------------------
	
	private double getSaldo() {  													//Funcion getter para obtener saldo
		return saldo;
	}
	
	
	private void setSaldo( double nuevoSaldo ) {  									//Funcion set para actualizar saldo
		this.saldo = nuevoSaldo;
	}
	
	
	private boolean esMultiplo50( double cantidad ) { 								//Verificar si una cantidad es m�ltiplo de 50
		if( cantidad == (((int)( cantidad / 50 )) * 50.0 ) )  { 					//Si es m�ltiplo de 50
				return true;
			}
		
		return false;
	}
	
	
	private String formateDouble ( double number ) { 								//Establecer una precisi�n de 2 digitos para float
		return String.format("%.2f", number );
	}
	
	private void limpiarConsola() {												
		System.out.print("\033[H\033[2J");  										//Limpiar consola
	    System.out.flush(); 
	}
	
	private void continuar( Scanner myScan )  { 									//Funcion para pausar despu�s de un proceso
		System.out.println( "Presione cualquier ENTER para continuar..." );
		myScan.nextLine();															//Consumimos el salto de linea

		this.limpiarConsola();
	}

	
	public void retirarDinero(  Scanner myScan2 ) {									//1) Funcion retirar de un cajero
		double saldoDisponibleRetirar;												//Variable para calcular la cantidad m�xima a retirar
		boolean wasWithdrew = false;  												//Variable si el usuario ya retir� su dinero
		double saldoRetirar;														//Variable para almacenar el saldo a retirar
		String donar = "n";															//Variable para saber si el usuario quiere donar
		
		this.limpiarConsola();
		
		if( this.getSaldo() >= MAX_AMOUNT_WITHDRAW ) {  							//Si el saldo es igual o mayor a la m�xima cantidad que se  puede retirar
			saldoDisponibleRetirar = MAX_AMOUNT_WITHDRAW; 							//La m�xima cantidad que se puede retirar (6_000)
			
		}else {
			saldoDisponibleRetirar = (int)(this.getSaldo()/50)*50.0; 				//El m�ximo saldo a retirar es m�ltiplo de 50 m�s grande del saldo actual
			
		}
		
		System.out.println("M�xima cantidad a retirar " + saldoDisponibleRetirar); 	//Mostramos la m�xima cantidad a retirar

		do {
			
			System.out.println("�Cu�nto dinero quieres retirar?"); 					//Preguntamos al usuario cuanto quiere retirar

			if( myScan2.hasNextDouble() ) {											//Si el dato a ingresar  es de tipo Double
				
				saldoRetirar = myScan2.nextDouble(); 								//Guardamos la cantidad a retirar
				myScan2.nextLine();													//Consumimos el salto de linea
				
				if( saldoRetirar > saldoDisponibleRetirar || saldoRetirar < 0.0 ) { //Si el saldo a retirar es mayor al posible o es negativo
					System.out.println("No se puede retirar esa cantidad!");  
					wasWithdrew = false;											//Para quedarnos en el bucle
					continue; 														//Se le va a volver a preguntar la cantidad al usuario
					
				}else {																//Si la cantidad es v�lida
					if( esMultiplo50( saldoRetirar )) { 							//Validamos si la cantidad es m�ltiplo de 50
						wasWithdrew = true; 										//Para ya salir del bucle
						this.setSaldo( this.getSaldo() - saldoRetirar ); 			//Le restamos la cantidad retirada a nuestro saldo
						
						System.out.println("�Quieres donar $" + DONACION_CH30 +
								" para la graduaci�n de ch30? (Y/N)"); 				//Preguntamos al usuario si quiere donar

						donar = myScan2.nextLine(); 								//Guardamos la respuesta del usuario
						
						
						if( ( donar.charAt(0) == 'y' || donar.charAt(0) == 'Y' ) ){ //Si el usuario quiere donar
							if( this.getSaldo() < DONACION_CH30 ) { 				//Si el saldo es menor a la donaci�n
								//No se puede donar
								System.out.println("No puedes donar, no tienes saldo suficiente"); 
								continue; 											//Le volvemos a preguntar al usuaio
							}else { 												//Si el usuario tiene el saldo suficiente para donar
								this.setSaldo( this.getSaldo() - DONACION_CH30 ); 	//Le restamos la donacion al saldo
								Date fechaMovimiento = new Date();					//Guardamos la fecha actual
								
								//Guardamos el movimiento en el arreglo de movimientos
								this.setUltimoMoviemiento( fechaMovimiento + MOVIMIENTO_RETIRO + this.formateDouble( saldoRetirar ) + DONACION );
								wasWithdrew = true;									//Para salir del bucle
							}
						}else { 													//Si el usuario no quiere donar
							Date fechaMovimiento = new Date(); 						//Guardamos la fecha actual
							
							//Guardamos el movimiento en el arreglo de movimientos
							this.setUltimoMoviemiento( fechaMovimiento + MOVIMIENTO_RETIRO + this.formateDouble( saldoRetirar ) ); 
							wasWithdrew = true;										//Para salir del bucle
						}
						
						System.out.println( "Retiro exitoso");
						
					}else { 														//Si la cantidad a retirar no es m�ltiplo de 50
						System.out.println("La cantidad a retirar debe ser m�ltiplo de $50");
						wasWithdrew = false;
					
					}
				}
				
			}else { 																//Si la cantidad no es de tipo double
				System.out.println("Cantidad Inv�lida, Ingrese otra cantidad");
				myScan2.nextLine();													//Consumimos el salto de linea
			}
			
		}while( wasWithdrew == false ); 											//Mientras el usuario no haya retirado dinero
		this.continuar( myScan2 );  												//Pausa para luego continuar
	}
	
	
	public void hacerDepositos( Scanner myScan ) { 									//2) Funcion para hacer depositos 

		String opcion = "3"; 														//Variable para que el usuario ingrese como opcion en el menu
		
		this.limpiarConsola();
		
		do { 																		//Bucle
			
			
			System.out.println( " 1) Cuenta de cheques\n"							//Men�
							  + " 2) Dep�sito a Tarjeta de Cr�dito\n"
							  + " 3) Salir");
			
			opcion = myScan.nextLine();												//Leemos la opci�n del usuario
			
			if( opcion.charAt(0) == '1' ) { 										//Si el usuario oprime 1
				this.cuentaDeCheques( myScan );										//Se ejecuta la funci�n de depositar en la cuenta de cheques
				
			}else if( opcion.charAt(0) == '2' ) { 									//Si el usuario oprime el 2
				this.dep�sitoTDC( myScan );											//Se ejecuta la funcion de depositar en tarjeta de cr�dito
				
			}else if ( opcion.charAt(0) != '3' ) { 									//Si el usuario oprime una opci�n no valida
				System.out.println("Opcion no v�lida"); 
	
			}
			
		}while( opcion.charAt(0) != '3' ); 											//Mientras el usuario no se salga del men�
		
		this.continuar( myScan ); 													//Pausa para luego continuar
	}
	
	
	private void cuentaDeCheques( Scanner myScan ) { 								//Funcion para depositar en la cuenta de cheques

		Boolean cantidadValida = false; 											//Variable para verificar si la cantidad es v�lida
		double cantidadDepositar = 0.0; 											//Variable para guardar la cantidad a depositar  en la cuenta de cheques
		
		this.limpiarConsola();
		
		do { //Bucle
			System.out.println("Ingrese la cantidad a depositar"); 					//Preguntar al usuario cuanto quiere depositar
			
			if( myScan.hasNextDouble()) { 											//Si se ingres� una cantidad double
				cantidadDepositar = myScan.nextDouble(); 							//Guardamos el valor
				myScan.nextLine();													//Consumimos el salto de linea
				
				if( esMultiplo50( cantidadDepositar ) ) { 							//Si es m�ltiplo de 50
					this.setSaldo( cantidadDepositar + this.getSaldo() ); 			//Guardamos la cantidad depositada al saldo
					Date fechaMovimiento = new Date(); 								//Obtenemos la fecha actual
					//Guardamos el movimiento en el arreglo de movimientos
					this.setUltimoMoviemiento( fechaMovimiento + DEPOSITO_CHEQUES + cantidadDepositar ); 
					cantidadValida = true; 											//Para salir del bucle
					 												
					
					System.out.println("Dep�sito exitoso"); 
				}else { //Si no es m�ltiplo de 50
					//La cantidad no es v�lida
					System.out.println( "Cantidad no v�lida, debe ser m�ltiplo de 50" ); 
					cantidadValida = false; 										//Para quedarnos en el bucle
															
				}
				
			}else { 																//Si el dato que se ingres� no es de tipo double
				System.out.println("Cantidad no v�lida, debe ser un n�mero"); 		//El dato no es v�lido
				cantidadValida = false; 											//Para quedarnos en el bucle
				myScan.nextLine(); 													//Consumimos el dato erroneo
				
			}
			
		}while( cantidadValida == false ); 											//Mientras no se deposite una cantidad
		
		
		this.continuar( myScan ); 													//Pausa para luego continuar
	}
	
	
	private void dep�sitoTDC( Scanner myScan  ) { 									//Funci�n para realizar dep�sitos a la tarjeta de cr�dito

		Boolean cantidadValida = false; 											//Variable para verificar que el usuario ingres� una cantidad v�lida
		double cantidadDepositar = 0.0; 											//Variable para almacenar la cantidad a depositar
		
		this.limpiarConsola();
		
		do { 																		//Bucle
			//Le preguntamos al usuario cuanto quiere depositar
			System.out.println("Ingrese la cantidad a depositar a su tarjeta de credito"); 
			
			if( myScan.hasNextDouble() ) { 											//Si el dato que se ingres� es de tipo double
				cantidadDepositar = myScan.nextDouble(); 							//Guardamos el dato
				myScan.nextLine();													//Consumimos el salto de linea
				
				if( cantidadDepositar > this.getSaldo() ) { 						//Si la cantidad que se quiere retirar es mayor al saldo actual
					//No es posible depositar esa cantidad
					System.out.println( "No tienes saldo suficiente paradepositar esa cantidad" );		
					cantidadValida = false; 										//Para quedarnos en el  bucle
					continue; 														//Pasamos a la siguiente iteracion del bucle
				}
				
				//Si la cantidad es v�lida
				cantidadValida = true;  											//Para salir del bucle
				this.setSaldo( this.getSaldo() - cantidadDepositar ); 				//Restamos la cantidad depositada al saldo
				Date fechaMovimiento = new Date(); 									//Guardamos la fecha
				//Guardamos el movimiento en el arreglo de movimientos
				this.setUltimoMoviemiento( fechaMovimiento + DEPOSITO_TDC + cantidadDepositar ); 
			
				
			}else { 																//Si el datos no es double
				System.out.println("Se tiene que ingresar un n�mero"); 				//El dato no es v�lido
				cantidadValida = false; 											//Para quedarnos en el  bucle
				myScan.nextLine(); 													//Consumimos el dato erroneo 
				
			}
			
		}while( cantidadValida == false ); 											//Mientras el usuario no deposite 
		
		System.out.println("Dep�sito exitoso");
		this.continuar( myScan ); 													//Pausa para luego continuar
	}
	
	
	public void consultarSaldo( Scanner myScan ) { 									//3) Funci�n para consultar el saldo actual
		
		this.limpiarConsola();
		System.out.println( "Saldo disponible: $" + this.getSaldo() ); 				//Mostramos el saldo
		this.continuar( myScan ); 													//Pausa para luego continuar
	}
	
	
	public void quejas( Scanner myScan ) { 											// 4)Funci�n para quejas 
		
		this.limpiarConsola();
		System.out.println( "No disponible por el momento, intente m�s tarde" );
		this.continuar( myScan ); 													//Pausa para luego continuar
	}
	
	
	private void setUltimoMoviemiento( String movimiento ) { 						//Funcion para agregar el �ltimo movimiento al arreglo de movimientos
		
		if( ultimoMovimientosArray.size() == NUMERO_ULTIMOS_MOVIMIENTOS ) {			//Si el arreglo ya est� lleno
			ultimoMovimientosArray.remove( 0 ); 									//Eliminamos el primer movimiento hecho
		}
		
		ultimoMovimientosArray.add( movimiento );  									//Agregamos el movimmiento al arreglos
	}
	
	
	public void ultimosMovimientos( Scanner myScan ) { 								// 5) Mostrar el arreglo de movimientos

		this.limpiarConsola();
		System.out.println( "Ultimos movimientos"  ); 
		
		for( String mov : ultimoMovimientosArray ) { 								//Bucle para iterar el arreglo de movimientos
			System.out.println( mov ); 												//Imprimimos cada uno de los movimientos
		}
		this.continuar( myScan ); 													//Pausa para luego continuar
	}
	
}
