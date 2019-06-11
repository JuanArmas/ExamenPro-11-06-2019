package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import modelo.Cuenta;
import modelo.Movimiento;
import modelo.Propietario;
import modelo.Vehiculo;


public class MainController {

	public static void main(String[] args) {
		MainController ejercicios = new MainController();
		String rutaAlmacenarFichero = "ficheros/ficheroMovs.txt";
		String rutaFicheroMovimientos = "ficheros/movimientos.txt";
		String rutaFicheroCuentas = "ficheros/cuentas.txt";
		/**************LLAMADA EJERCICIO 1************************************/
		// ejercicios.ejercicio1(rutaAlmacenarFichero, "#"); // CODIGO FUNCIONAL; DESCOMENTAR, BORRAR FICHERO CREADO EN CARPETA FICHEROS Y EJECUTAR
		
		/**************LLAMADA EJERCICIO 2************************************/
		// ejercicios.mostrarMovimientosCuenta(rutaFicheroMovimientos, rutaFicheroCuentas); // CODIGO FUNCIONAL; DESCOMENTAR,
		
		/**************LLAMADA EJERCICIO 3************************************/
		HashMap<String, Propietario> mapaPropietarios = ejercicios.ejercicio3();		
		// System.out.println(propietarios);		
		ejercicios.mostrarPropietarios(mapaPropietarios);
		
		
		System.out.println("Fin del programa");
		System.exit(0);
	}

	/****************************INICIO EJERCICIO 1************************************/
	// Desarrollar un método que lea la tabla movimientos (se encuentra en el fichero
	// backupBanco.sql) y cree un fichero de texto con todos los movimientos, ordenados por identificador de cuenta, idCta y fecha.
	
	public void ejercicio1(String rutaAlmacenarFichero, String separador) {
	try {
		BaseDatos bd = new BaseDatos("localhost:3306",  "banco", "root", "");
		Connection conexion = bd.getConexion();
		Statement stmt = conexion.createStatement();
		ResultSet rst = stmt.executeQuery("SELECT * FROM movs order by id, idcta,fecha");
		ResultSetMetaData rstMeta = rst.getMetaData();
		rstMeta.getColumnCount(); 
		BufferedWriter fichero = new BufferedWriter(new FileWriter(rutaAlmacenarFichero));
		// id, int idcta, String fecha, String descripcion, float importe
		while(rst.next()) { 		
			int id = Integer.parseInt(rst.getString(1));
			int idcta = Integer.parseInt(rst.getString(2));
			String fecha = rst.getString(3);
			String descripcion = rst.getString(4);
			float importe = Float.parseFloat(rst.getString(5));			
			fichero.write(id + separador+ idcta + separador+ fecha + separador+ descripcion + separador
					+ importe +"\n");						
		}
		fichero.close();
		rst.close();
		stmt.close();
		conexion.close();
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}catch (NullPointerException e) {
		System.out.println(e.getMessage());
	} catch (IOException e) {
	}
	}
	
	/****************************INICIO EJERCICIO 2************************************/
	// EN EL EJERCICIO 2 HE OPTADO POR LLAMAR A LOS DISTINTOS METODOS POR LA INFORMACION QUE MANEJAN PARA FACILITAR 
	// EL USO DE LA MISMA EN EL METODO QUE USARE A MODO DE "VISTA"
	public HashMap <Integer,Cuenta> cuentas(String rutaFicheroCuentas){
		HashMap <Integer, Cuenta> mapaCuentas = new HashMap <Integer, Cuenta>();
		try {
			FileReader fr = new FileReader(rutaFicheroCuentas);
			BufferedReader br = new BufferedReader(fr);
			String fichero;
			while((fichero = br.readLine()) != null) {
				String[] lineaFichero = fichero.split("#&");
				//1#&cuenta1#&100
				// int id; String descripcion;	float saldo;
				int id = Integer.parseInt(lineaFichero[0]);
				String descripcion = lineaFichero[1];
				float saldo = Float.parseFloat(lineaFichero[2]);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(id);
				cuenta.setDescripcion(descripcion);
				cuenta.setSaldo(saldo);
				mapaCuentas.put(id, cuenta);
			}
			return mapaCuentas;
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public HashMap <Integer,ArrayList<Movimiento>> listadoMovimientos(String rutaFicheroMovimientos){
		HashMap <Integer, ArrayList<Movimiento>> mapaListadoMovimientos = new HashMap <Integer, ArrayList<Movimiento>>();		
		try {
			FileReader fr = new FileReader(rutaFicheroMovimientos);
			BufferedReader br = new BufferedReader(fr);
			String fichero;
			while((fichero = br.readLine()) != null) {
				String[] lineaFichero = fichero.split("#&");
				//1#&2#&2019-01-03#&compra de material#&-50
				// int id;	int idCta; String fecha; String descripcion; float importe;
				int id = Integer.parseInt(lineaFichero[0]);
				int idCta = Integer.parseInt(lineaFichero[1]);			
				String fecha = lineaFichero[2];
				String descripcion = lineaFichero[3];
				float importe = Float.parseFloat(lineaFichero[4]);
				Movimiento movimiento = new Movimiento();
				movimiento.setId(id);
				movimiento.setIdCta(idCta);
				movimiento.setFecha(fecha);
				movimiento.setDescripcion(descripcion);
				movimiento.setImporte(importe);					
				if(!mapaListadoMovimientos.containsKey(idCta)) {						
					ArrayList<Movimiento> listadoMovs = new ArrayList<Movimiento>();
					mapaListadoMovimientos.put(idCta, listadoMovs);
				}
				mapaListadoMovimientos.get(idCta).add(movimiento);			
			}
			return mapaListadoMovimientos;
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void mostrarMovimientosCuenta(String rutaFicheroMovimientos, String rutaFicheroCuentas) {
		HashMap<Integer, Cuenta> mapaCuentas = cuentas(rutaFicheroCuentas);
		HashMap<Integer, ArrayList<Movimiento>> movimientos = listadoMovimientos(rutaFicheroMovimientos);
		System.out.println("LISTADO DE MOVIMIENTOS A CUENTAS...");
		
		Set<Integer> clavesCuentas = mapaCuentas.keySet();
		Set<Integer> clavesMovimientos = movimientos.keySet();	
		for(Integer id : clavesCuentas) {
			Cuenta cuenta = mapaCuentas.get(id);
			for(Integer idCta: clavesMovimientos) {
				 ArrayList<Movimiento> listaMovimientos = movimientos.get(idCta);
				 float saldoCuenta = cuenta.getSaldo();
				 if(idCta==id) {
					System.out.println("CUENTA :" + "cuenta"+id);					
					System.out.println("Saldo Inicial :" + saldoCuenta);						
					float acumMov=0f;
					for(int i = 0; i < listaMovimientos.size(); i++) {					
						float unMovimiento = listaMovimientos.get(i).getImporte();
						String fecha = listaMovimientos.get(i).getFecha();
						acumMov += unMovimiento;						
						System.out.println(fecha + "\t\t\t" + unMovimiento + " €");
					}
					float saldoFinal = acumMov + saldoCuenta;
					
					System.out.println("SUBTOTAL CUENTA : "+ "Cuenta"+id + "\t" + saldoFinal + " €");
					System.out.println("-------------------------------------");
				 }
			}
		}
		
	}
	
	/****************************INICIO EJERCICIO 3************************************/
	
	public HashMap<String, Propietario> ejercicio3(){
		HashMap<String, Propietario> mapaPropietarios = new HashMap<String, Propietario>();
		try {
			BaseDatos bd = new BaseDatos("localhost:3306",  "vehiculos", "root", "");
			Connection conexion = bd.getConexion();
			Statement stmt = conexion.createStatement();
			ResultSet rS = stmt.executeQuery("select * from vehiculos;");
			ResultSetMetaData rSMeta = rS.getMetaData();
			rSMeta.getColumnCount(); 
			// codigo, Strig matricula, String fecha, char estado, Int precio, String nif
			while(rS.next()) { 	
				int codigo = rS.getInt("codigo");
				String matricula = rS.getString("matricula");
				String fecha = rS.getString("fecha");
				String estado = rS.getString("estado");
				float precio = rS.getFloat("precio");
				String nifPropietario = rS.getString("nif");
				Vehiculo unCoche = new Vehiculo(codigo, nifPropietario, matricula, estado, precio);				

				ArrayList<Vehiculo> unVehiculo = null;
				

				Propietario propietario = null;
				
				if(!mapaPropietarios.containsKey(nifPropietario)) {
					mapaPropietarios.put(nifPropietario, propietario = new Propietario());
					propietario.setNif(nifPropietario);
					propietario.setVehiculos(new ArrayList<Vehiculo>());

				}
					mapaPropietarios.get(nifPropietario).getVehiculos().add(unCoche);
				
				
				
			}
		} catch (SQLException e) {
		}
		return mapaPropietarios;
	}

	public void mostrarPropietarios(HashMap<String, Propietario> ejercicio3) {
		Set<String> clavesEjercicio3 = ejercicio3.keySet();
		for(String nifPropietarios : clavesEjercicio3) {
			Propietario propietario = ejercicio3.get(nifPropietarios);
			System.out.println("Propietario con nif: " + propietario.getNif() + "-->");
			ArrayList<Vehiculo> listadoVehiculos = propietario.getVehiculos();
			for(int i = 0; i < listadoVehiculos.size(); i++) {
				System.out.println();
				System.out.println("\tMATRICULA -->"+listadoVehiculos.get(i).getMatricula());
			}
			System.out.println();
		}
	}
	
	
	
	
	
	
	
	
}
