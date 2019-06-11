package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;


public class MainController {

	public static void main(String[] args) {
		MainController ejercicios = new MainController();
		String rutaAlmacenarFichero = "ficheros/ficheroMovs.txt";
		/**************LLAMADA EJERCICIO 1************************************/
		ejercicios.ejercicio1(rutaAlmacenarFichero, "#");
		
		/**************LLAMADA EJERCICIO 2************************************/
		
		
		/**************LLAMADA EJERCICIO 3************************************/
		
		
		
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
	
	
	
	/****************************INICIO EJERCICIO 3************************************/
}
