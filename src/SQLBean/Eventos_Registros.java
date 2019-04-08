package SQLBean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Eventos_Registros implements PropertyChangeListener {

	//Arraylist de registros
	private static ArrayList<RegistroSQLBean> arraylist_registros = new ArrayList<>();

	//Metodo para anadir al arraylist los registros que se consiguen a partir del observable.
	public void propertyChange(PropertyChangeEvent e) {
		arraylist_registros.add((RegistroSQLBean) e.getNewValue());

	}

	//Getters y setters del arraylist
	public ArrayList<RegistroSQLBean> getArraylist_registros() {
		return arraylist_registros;
	}

	public void setArraylist_registros(ArrayList<RegistroSQLBean> arraylist_registros) {
		this.arraylist_registros = arraylist_registros;
	}

	public RegistroSQLBean setArraylist_registros(int i) {
		return arraylist_registros.get(i);
	}

	public void setArraylist_registros(RegistroSQLBean registro, int i) {
		this.arraylist_registros.set(i, registro);
	}

}
