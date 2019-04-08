package SQLBean;

import SQLBean.GestorSQLBean;
import SQLBean.RegistroSQLBean;

public class Test {
	public static void main(String[] args) {
		// Creamos objetos para llamar a los metodos
		GestorSQLBean g = new GestorSQLBean();
		RegistroSQLBean r = new RegistroSQLBean();
		//Conectamos a la base de datos pasando el nombre, la ip, el puerto, usuario y contrasena
		g.conexionSQL("imdb", "localhost", "3306", "root", "");

		//Hacemos un insert, tenemos 3 peliculas insertadas anteriormente, esta sera la cuarta.
		g.sentenciaSQL("INSERT INTO `movies`(`name`, `genre`, `director`, `date`) VALUES ('Iron Maannn','Action/Sci-Fi','Jon Favreau','30/04/2008')");

		//Select de todas las peliculas
		g.sentenciaSQL("SELECT * FROM movies");
		//Hacemos un update del insert que hicimos anteriormente, el nombre estaba mal
		g.sentenciaSQL("UPDATE movies SET name='Iron Man' WHERE id=4");
	
		//Eliminamos la pelicula que se llama Venom
		g.sentenciaSQL("DELETE FROM movies WHERE name = 'Venom'");
		 
		//Procedure para insertar otra pelicula, como no se especifica nada, este procedure solo funciona para la base de datos de imdb,
		//ya que los parametros que recibe son de una pelicula, y solo funciona una vez, para volver a probar hay que eliminar el procedure,
		//ya que se crea desde codigo y no desde phpmyadmin.
		g.sentenciaSQL("CALL myProcedure");

		//Metodo para printar todo el arraylist de registros.
		r.printarTodosRegistros();
		 
		//Metodo para consultar datos pasando el nombre de base de datos y tipo de consulta
		g.consultarPor("imdb", "select");
		//Metodo para consultar datos pasando el nombre de base de datos y el uusuario
		g.consultarPor("imdb", "root");
		//Metodo para consultar datos pasando el nombre de base de datos, usuario, y tipo de consulta
		g.consultarPor("imdb", "root", "SELECT");
	}
	
}	
