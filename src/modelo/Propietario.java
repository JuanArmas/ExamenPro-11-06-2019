package modelo;

import java.util.ArrayList;

public class Propietario {
	private String nif;
    private ArrayList<Vehiculo> vehiculos;
	public Propietario() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}
	public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}
}
