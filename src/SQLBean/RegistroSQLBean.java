package SQLBean;

import java.util.ArrayList;
import java.util.Date;

public class RegistroSQLBean {

	// Atributos de la clase
	private String nombre_bd;
	private String usuari_Conexio;
	private String tipus_Consulta;
	private String sentencia;
	private Date fecha;
	private int num_Registros;

	//Constructor vacio
	public RegistroSQLBean() {
		
	}
	
	// Getters y Setters
	public String getUsuari_Conexio() {
		return usuari_Conexio;
	}

	public void setUsuari_Conexio(String usuari_Conexio) {
		this.usuari_Conexio = usuari_Conexio;
	}

	public String getTipus_Consulta() {
		return tipus_Consulta;
	}

	public void setTipus_Consulta(String tipus_Consulta) {
		this.tipus_Consulta = tipus_Consulta;
	}

	public String getSentencia() {
		return sentencia;
	}

	public void setSentencia(String sentencia) {
		this.sentencia = sentencia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNum_Registros() {
		return num_Registros;
	}

	public void setNum_Registros(int num_Registros) {
		this.num_Registros = num_Registros;
	}

	public String getNombre_bd() {
		return nombre_bd;
	}

	public void setNombre_bd(String nombre_bd) {
		this.nombre_bd = nombre_bd;
	}

	//Metodo para printar los registros
	public void printarTodosRegistros() {
		ArrayList<RegistroSQLBean> list = new ArrayList<RegistroSQLBean>();
		Eventos_Registros e = new Eventos_Registros();
		list = e.getArraylist_registros();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Usuari Conexio = " + list.get(i).usuari_Conexio);
			System.out.println("Tipus Consulta = " + list.get(i).tipus_Consulta);
			System.out.println("Sentencia Executada = " + list.get(i).sentencia);
			System.out.println("Fecha = " + list.get(i).getFecha());
			System.out.println("Numero Registres = " + list.get(i).getNum_Registros());
			System.out.println("");
		}
	}

}
