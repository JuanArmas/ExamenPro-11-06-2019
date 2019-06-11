package modelo;

public class Movimiento {
	private int id;
	private int idCta;
	private String fecha;
	private String descripcion;
	private float importe;
	
	public Movimiento() {
		super();
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCta() {
		return idCta;
	}
	public void setIdCta(int idCta) {
		this.idCta = idCta;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
}
