package org.generation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CajeroAutomatico  {
	//Atributos
	private double saldo = 10_000.0; //Se  crea el atributo saldo y le asignamos  10_000 como saldo inicial
	private double MAX_AMOUNT_WITHDRAW = 6_000.0  ; //Máxima cantidad a retirar
	private ArrayList < String > ultimoMovimientosArray = new ArrayList < String >(); //Arreglo para guardar los ultimos movimientos
	private String MOVIMIENTO_RETIRO = " Retiro de $"; //Cadena de texto para imprimir cuando se haga un retiro
	private String DONACION = " + $200 de donación para Ch30"; //Cadena de texto para imprimir cuando se haga una donacion
	private String DEPOSITO_TDC = " Depósito a TC de $"; //Cadena de texto para imprimir cuando se haga un deposito a la tarjeta de crédito
	private String DEPOSITO_CHEQUES = " Depósito a cuenta de cheques de $"; //Cadena de texto para imprimir cuando se haga un retiro
	private double DONACION_CH30 = 200.0; //Constante donacion para CH30
	private int NUMERO_ULTIMOS_MOVIMIENTOS = 5; //Constante para cuantos ultimos movimientos
	
	private double getSaldo() {  //funcion para obtener saldo
		return saldo;
	}
	
	private void setSaldo( double nuevoSaldo ) {  //Funcion para actualizar saldo
		this.saldo = nuevoSaldo;
	}
	
	private boolean esMultiplo50( double cantidad ) { //Verificar si una cantidad es múltiplo de 50
		if( cantidad == (((int)( cantidad / 50 )) * 50.0 ) )  { //Si es múltiplo de 50
			return true;
		}
		return false;
	}
	
	private String formateDouble ( double number ) { //Establecer una precisión de 2 digitos para float
		return String.format("%.2f", number );
	}
	
	private void continuar()  { //Funcion para continuar después de un proceso
		Scanner myScan = new Scanner( System.in );
		
		System.out.println( "Presione cualquier tecla para continuar..." );
		myScan.nextLine();
		System.out.print("\033[H\033[2J");  
	    System.out.flush(); 
		
	}

	public void retirarDinero() {	//1) Funcion retirar de un cajero
		double saldoDisponibleRetirar;	//Variable para calcular la cantidad máxima a retirar
		boolean wasWithdrew = false;  //Variable si el usuario ya retiró su dinero
		Scanner myScan2 = new Scanner(System.in);
		double saldoRetirar;	//Variable para almacenar el saldo a retirar
		String donar = "n";		//Variable para saber si el usuario quiere donar
		
		if( this.getSaldo() >= MAX_AMOUNT_WITHDRAW ) {  //Si el saldo es igual o mayor a la máxima cantidad que se  puede retirar
			saldoDisponibleRetirar = MAX_AMOUNT_WITHDRAW; //La máxima cantidad que se puede retirar (6_000)
			
		}else {
			saldoDisponibleRetirar = (int)(this.getSaldo()/50)*50.0; //El máximo saldo a retirar es múltiplo de 50 más grande del saldo actual
			
		}
		
		System.out.println("Máxima cantidad a retirar " + saldoDisponibleRetirar); //Mostramos la máxima cantidad a retirar

		do {
			
			System.out.println("¿Cuánto dinero quieres retirar?"); //Preguntamos al usuario cuanto quiere retirar

			if( myScan2.hasNextDouble() ) {//Si el dato a ingresar  es de tipo Double
				
				saldoRetirar = myScan2.nextDouble(); //Guardamos la cantidad a retirar
				
				if( saldoRetirar > saldoDisponibleRetirar || saldoRetirar < 0.0 ) { //Si el saldo a retirar es mayor al posible o es negativo
					System.out.println("No se puede retirar esa cantidad!");  
					wasWithdrew = false;	//Para quedarnos en el bucle
					continue; //Se le va a volver a preguntar la cantidad al usuario
					
				}else {		//Si la cantidad es válida
					if( esMultiplo50( saldoRetirar )) { //Validamos si la cantidad es múltiplo de 50
						wasWithdrew = true; //Para ya salir del bucle
						this.setSaldo( this.getSaldo() - saldoRetirar ); //Le restamos la cantidad retirada a nuestro saldo
						
						System.out.println("¿Quieres donar $" + DONACION_CH30 + " para la graduación de ch30? (Y/N)"); //Preguntamos al usuario si quiere donar
						donar = myScan2.next(); //Guardamos la respuesta del usuario
						
						if( ( donar.charAt(0) == 'y' || donar.charAt(0) == 'Y' ) ) { //Si el usuario quiere donar
							if( this.getSaldo() < DONACION_CH30 ) { //Si el saldo es menor a la donación
								System.out.println("No puedes donar, no tienes saldo suficiente"); //No se puede donar
								Date fechaMovimiento = new Date(); //Guardamos la fecha actual
								//Guardamos el movimiento en el arreglo de movimientos
								this.setUltimoMoviemiento( fechaMovimiento + MOVIMIENTO_RETIRO + this.formateDouble( saldoRetirar ) + DONACION ); 
							
							}else { //Si el usuario tiene el saldo suficiente para donar
								this.setSaldo( this.getSaldo() - DONACION_CH30 ); //Le restamos la donacion al saldo
								Date fechaMovimiento = new Date();//Guardamos la fecha actual
								//Guardamos el movimiento en el arreglo de movimientos
								this.setUltimoMoviemiento( fechaMovimiento + MOVIMIENTO_RETIRO + this.formateDouble( saldoRetirar ) );
							
							}
						}
						System.out.println( "Retiro exitoso");
						
					}else { //Si la cantidad a retirar no es múltiplo de 50
						System.out.println("La cantidad a retirar debe ser múltiplo de $50");
						wasWithdrew = false;
					
					}
				}
				
			}else { //Si la cantidad no es de tipo double
				System.out.println("Cantidad Inválida, Ingrese otra cantidad");
				myScan2.nextLine();
			}
			
		}while( wasWithdrew == false ); //Mientras el usuario no haya retirado dinero
		this.continuar();  //Pausa para luego continuar
	}
	
	public void hacerDepositos() { //2) Funcion para hacer depositos 
		Scanner myScan = new Scanner( System.in );
		String opcion = "3"; //Variable para que el usuario ingrese como opcion en el menu
		
		do { //Bucle
			//Menú
			System.out.println( " 1) Cuenta de cheques\n"
							  + " 2) Depósito a Tarjeta de Crédito\n"
							  + " 3) Salir\n");
			
			opcion = myScan.nextLine();	//Leemos la opción del usuario
			
			if( opcion.charAt(0) == '1' ) { //Si el usuario oprime 1
				this.cuentaDeCheques();		//Se ejecuta la función de depositar en la cuenta de cheques
				
			}else if( opcion.charAt(0) == '2' ) { //Si el usuario oprime el 2
				this.depósitoTDC();				//Se ejecuta la funcion de depositar en tarjeta de crédito
				
			}else if ( opcion.charAt(0) != '3' ) { //Si el usuario oprime una opción no valida
				System.out.println("Opcion no válida"); 
	
			}
		}while( opcion.charAt(0) != '3' ); //Mientras el usuario no se salga del menú
		
		this.continuar(); //Pausa para luego continuar
	}
	
	private void cuentaDeCheques() { //Funcion para depositar en la cuenta de cheques
		Scanner myScan = new Scanner( System.in );
		Boolean cantidadValida = false; //Variable para verificar si la cantidad es válida
		double cantidadDepositar = 0.0; //Variable para guardar la cantidad a depositar  en la cuenta de cheques
		
		do { //Bucle
			System.out.println("Ingrese la cantidad a depositar"); //Preguntar al usuario cuanto quiere depositar
			
			if( myScan.hasNextDouble()) { //Si se ingresó una cantidad double
				cantidadDepositar = myScan.nextDouble(); //Guardamos el valor
				
				if( esMultiplo50( cantidadDepositar ) ) { //Si es múltiplo de 50
					this.setSaldo( cantidadDepositar + this.getSaldo() ); //Guardamos la cantidad depositada al saldo
					Date fechaMovimiento = new Date(); //Obtenemos la fecha actual
					this.setUltimoMoviemiento( fechaMovimiento + DEPOSITO_CHEQUES + cantidadDepositar ); //Guardamos el movimiento en el arreglo de movimientos
					cantidadValida = true; //Para salir del bucle
					
				}else { //Si no es múltiplo de 50
					System.out.println( "Cantidad no válida, debe ser múltiplo de 50" ); //La cantidad no es válida
					cantidadValida = false; //Para quedarnos en el bucle
					
				}
				
			}else { //Si el dato que se ingresó no es de tipo double
				System.out.println("Cantidad no válida, debe ser un número"); //El dato no es válido
				cantidadValida = false; //Para quedarnos en el bucle
				myScan.nextLine(); //Consumimos el dato erroneo
				
			}
			
		}while( cantidadValida == false ); //Mientras no se deposite una cantidad
		
		System.out.println("Depósito exitoso"); 
		this.continuar(); //Pausa para luego continuar
	}
	
	private void depósitoTDC() { //Función para realizar depósitos a la tarjeta de crédito
		Scanner myScan = new Scanner( System.in );
		Boolean cantidadValida = false; //Variable para verificar que el usuario ingresó una cantidad válida
		double cantidadDepositar = 0.0; //Variable para almacenar la cantidad a depositar
		
		do { //Bucle
			System.out.println("Ingrese la cantidad a depositar a su tarjeta de credito"); //Le preguntamos al usuario cuanto quiere depositar
			
			if( myScan.hasNextDouble() ) { //Si el dato que se ingresó es de tipo double
				cantidadDepositar = myScan.nextDouble(); //Guardamos el dato
				
				if( cantidadDepositar > this.getSaldo() ) { //Si la cantidad que se quiere retirar es mayor al saldo actual
					System.out.println( "No se puede depositar esa cantidad" ); //No es posible depositar esa cantidad
					cantidadValida = false; //Para quedarnos en el  bucle
					continue; //Pasamos a la siguiente iteracion del bucle
				}
				
				//Si la cantidad es válida
				cantidadValida = true;  //Para salir del bucle
				this.setSaldo( this.getSaldo() - cantidadDepositar ); //Restamos la cantidad depositada al saldo
				Date fechaMovimiento = new Date(); //Guardamos la fecha
				this.setUltimoMoviemiento( fechaMovimiento + DEPOSITO_TDC + cantidadDepositar ); //Guardamos el movimiento en el arreglo de movimientos
				
			}else { //Si el datos no es double
				System.out.println("Se tiene que ingresar un número"); //El dato no es válido
				cantidadValida = false; //Para quedarnos en el  bucle
				myScan.nextLine(); //Consumimos el dato erroneo 
				
			}
			
		}while( cantidadValida == false ); //Mientras el usuario no deposite 
		
		System.out.println("Depósito exitoso");
		this.continuar(); //Pausa para luego continuar
	}
	
	public void consultarSaldo() { //3) Función para consultar el saldo actual
		System.out.println( "Saldo disponible: $" + this.getSaldo() ); //Mostramos el saldo
		this.continuar(); //Pausa para luego continuar
	}
	
	public void quejas() { // 4) Función para quejas 
		System.out.println( "No disponible por el momento, intente más tarde" );
		this.continuar(); //Pausa para luego continuar
	}
	
	private void setUltimoMoviemiento( String movimiento ) { //Funcion para agregar el último movimiento al arreglo de movimientos
		
		if( ultimoMovimientosArray.size() == NUMERO_ULTIMOS_MOVIMIENTOS ) {	//Si el arreglo ya está lleno
			ultimoMovimientosArray.remove( 0 ); //Eliminamos el primer movimiento hecho
		}
		
		ultimoMovimientosArray.add( movimiento );  //Agregamos el movimmiento al arreglos
	}
	
	public void ultimosMovimientos(  ) { // 5) Mostrar el arreglo de movimientos
		System.out.println( "Ultimos movimientos"  ); 
		
		for( String mov : ultimoMovimientosArray ) { //Bucle para iterar el arreglo de movimientos
			System.out.println( mov ); //Imprimimos cada uno de los movimientos
		}
		this.continuar(); //Pausa para luego continuar
	}
}
