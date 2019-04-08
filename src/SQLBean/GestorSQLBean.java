package SQLBean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import SQLBean.*;

public class GestorSQLBean implements Serializable {

	// Atributos de la clase
	private String bd;
	private String ip;
	private String puerto;
	private String usuario;
	private String contrasena;
	private String[] tipo_consulta;
	private Connection conn;
	private PreparedStatement preparedStmt;
	Eventos_Registros evento;
	private PropertyChangeSupport changeSupp;
	private Statement stmt;
	private ResultSet rs;
	private CallableStatement callableStatement;
	Date date = new Date();

	// Constructor
	public GestorSQLBean() {
		// Creo el observable a mi clase
		changeSupp = new PropertyChangeSupport(this);
		evento = new Eventos_Registros();
		// Meto mi evento a mi clase
		addPropertyChangeListener(evento);
	}

	// Conexion a la base de datos
	public void conexionSQL(String bd, String ip, String puerto, String usuario, String contrasena) {
		conn = null;
		try {
			if (conn != null) {
				stmt.close();
				rs.close();
				conn.close();
			}
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:" + puerto + "/" + bd, usuario, contrasena);
			this.bd = bd;
			this.ip = ip;
			this.puerto = puerto;
			this.usuario = usuario;
			this.contrasena = contrasena;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	// METODO PARA COMPROBAR EL TIPO DE QUERY (SELECT/UPDATE/DELETE)
	public void sentenciaSQL(String query) {
		tipo_consulta = query.split(" ");
		if (tipo_consulta[0].equalsIgnoreCase("SELECT")) {
			hacerSelect(query);
		} else if (tipo_consulta[0].equalsIgnoreCase("INSERT")) {
			hacerInsert(query);
		} else if (tipo_consulta[0].equalsIgnoreCase("UPDATE")) {
			hacerUpdate(query);
		} else if (tipo_consulta[0].equalsIgnoreCase("DELETE")) {
			hacerDelete(query);
		} else if (tipo_consulta[0].equalsIgnoreCase("CALL")) {
			hacerProcedure(query);

		}
	}

	// QUERY INSERT
	private void hacerInsert(String query) {
		int contador = 0;
		try {
			stmt = conn.createStatement();
			contador = stmt.executeUpdate(query);

			RegistroSQLBean objeto = new RegistroSQLBean();
			objeto.setNombre_bd(bd);
			objeto.setUsuari_Conexio(usuario);
			objeto.setTipus_Consulta(tipo_consulta[0]);
			objeto.setSentencia(query);
			objeto.setFecha(date);
			objeto.setNum_Registros(contador);
			changeSupp.firePropertyChange("INSERT", null, objeto);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// QUERY SELECT
	public void hacerSelect(String query) {
		int contador = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				contador++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		RegistroSQLBean objeto = new RegistroSQLBean();
		objeto.setNombre_bd(bd);
		objeto.setUsuari_Conexio(usuario);
		objeto.setTipus_Consulta(tipo_consulta[0]);
		objeto.setSentencia(query);
		objeto.setFecha(date);
		objeto.setNum_Registros(contador);

		changeSupp.firePropertyChange("SELECT", null, objeto);

	}

	// QUERY UPDATE
	public void hacerUpdate(String query) {
		int contador = 0;
		try {
			stmt = conn.createStatement();
			contador = stmt.executeUpdate(query);

			RegistroSQLBean objeto = new RegistroSQLBean();
			objeto.setNombre_bd(bd);
			objeto.setUsuari_Conexio(usuario);
			objeto.setTipus_Consulta(tipo_consulta[0]);
			objeto.setSentencia(query);
			objeto.setFecha(date);
			objeto.setNum_Registros(contador);
			changeSupp.firePropertyChange("UPDATE", null, objeto);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// QUERY DELETE
	public void hacerDelete(String query) {
		int contador = 0;
		try {
			stmt = conn.createStatement();
			contador = stmt.executeUpdate(query);

			RegistroSQLBean objeto = new RegistroSQLBean();
			objeto.setNombre_bd(bd);
			objeto.setUsuari_Conexio(usuario);
			objeto.setTipus_Consulta(tipo_consulta[0]);
			objeto.setSentencia(query);
			objeto.setFecha(date);
			objeto.setNum_Registros(contador);
			changeSupp.firePropertyChange("DELETE", null, objeto);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// QUERY PROCEDURE
	public void hacerProcedure(String query) {
		int contador = 0;
		try {
			String procedure = "CREATE DEFINER=`root`@`localhost` PROCEDURE `myProcedure`(IN `name` VARCHAR(40), IN `genre` VARCHAR(25), IN `director` VARCHAR(30), IN `date` VARCHAR(10)) NOT DETERMINISTIC NO SQL SQL SECURITY DEFINER BEGIN insert into movies (name,genre,director,date) values (name,genre,director,date); END";
			stmt = conn.createStatement();
			contador = stmt.executeUpdate(procedure);
			CallableStatement cs = conn.prepareCall(
					"{call myProcedure('The Dark Knight Rises','Action/Thriller','Christopher Nolan','20/07/2012')}");
			contador = cs.executeUpdate();

			RegistroSQLBean objeto = new RegistroSQLBean();
			objeto.setNombre_bd(bd);
			objeto.setUsuari_Conexio(usuario);
			objeto.setTipus_Consulta(tipo_consulta[0]);
			objeto.setSentencia(query);
			objeto.setFecha(date);
			objeto.setNum_Registros(contador);
			changeSupp.firePropertyChange("CALL", null, objeto);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Metodo para printar informacion recibiendo parametros el nombre de base de
	// datos y otro dato que puede ser tipo de consulta o usuario
	public void consultarPor(String nombre_db, String tipo_o_usuario) {
		Eventos_Registros e = new Eventos_Registros();
		ArrayList<RegistroSQLBean> registros = e.getArraylist_registros();
		// Si lo que recibimos es un tipo de consulta
		if (tipo_o_usuario.equalsIgnoreCase("INSERT") || tipo_o_usuario.equalsIgnoreCase("SELECT")
				|| tipo_o_usuario.equalsIgnoreCase("UPDATE") || tipo_o_usuario.equalsIgnoreCase("DELETE")
				|| tipo_o_usuario.equalsIgnoreCase("CALL")) {
			System.out.println(" ------------------------------------------------------------------- ");
			System.out.println("|    MOSTRANDO INFORMACION POR NOMBRE DE BD Y TIPO DE CONSULTA      |");
			System.out.println(" ------------------------------------------------------------------- ");
			for (int i = 0; i < registros.size(); i++) {
				if (registros.get(i).getNombre_bd().equalsIgnoreCase(nombre_db)
						&& registros.get(i).getTipus_Consulta().equalsIgnoreCase(tipo_o_usuario)) {
					System.out.println("Sentencia Executada = " + registros.get(i).getSentencia() + " || Fecha = "
							+ registros.get(i).getFecha() + " || Usuari Conexio = "
							+ registros.get(i).getUsuari_Conexio());
				}

			}
			// Si lo que recibimos es un usuario y base de datos
		} else {
			System.out.println(" ------------------------------------------------------------------- ");
			System.out.println("|       MOSTRANDO INFORMACION POR NOMBRE DE BD Y USUARIO	    |");
			System.out.println(" ------------------------------------------------------------------- ");
			for (int i = 0; i < registros.size(); i++) {
				if (registros.get(i).getNombre_bd().equalsIgnoreCase(nombre_db)
						&& registros.get(i).getUsuari_Conexio().equalsIgnoreCase(tipo_o_usuario)) {
					System.out.println("Sentencia Executada = " + registros.get(i).getSentencia() + " || Fecha = "
							+ registros.get(i).getFecha());
				}
			}
		}

	}

	// Metodo para printar informacion recibiendo parametros el nombre usuario, base
	// de datos y tipo de consulta
	public void consultarPor(String nombre_db, String usuario, String tipo_consulta) {
		Eventos_Registros e = new Eventos_Registros();
		ArrayList<RegistroSQLBean> registros = e.getArraylist_registros();
		System.out.println(" --------------------------------------------------------------------");
		System.out.println("| MOSTRANDO INFORMACION POR NOMBRE DE BD, USUARIO Y TIPO DE CONSULTA |");
		System.out.println(" --------------------------------------------------------------------");
		for (int i = 0; i < registros.size(); i++) {
			if (registros.get(i).getNombre_bd().equalsIgnoreCase(nombre_db)
					&& registros.get(i).getUsuari_Conexio().equalsIgnoreCase(usuario)
					&& registros.get(i).getTipus_Consulta().equalsIgnoreCase(tipo_consulta)) {
				System.out.println("Sentencia Executada = " + registros.get(i).getSentencia() + " || Fecha = "
						+ registros.get(i).getFecha());

			}

		}
	}

	// Getters y Setters
	public String getBd() {
		return bd;
	}

	public void setBd(String bd) {
		this.bd = bd;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	//Metodos PropertyChangeListener
	public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupp.removePropertyChangeListener(l);
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupp.addPropertyChangeListener(l);
	}

}
