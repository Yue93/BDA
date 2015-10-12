package Modelo;

import java.sql.Time;
import java.util.Set;

public class Receta {
	private int id;
	private String nombre;
	private String elaboracion;
	private int dificultad;
	private int tiempo;
	
	private Comida comida;
	private Plato plato;
	private Chef chef;
	private Set<Ingrediente> ingredientes;
	
	public Receta(){
		
	}
	
	public Receta(int id,String nombre, String elaboracion,int dificultad, int tiempo){
		this.id=id;
		this.nombre=nombre;
		this.elaboracion=elaboracion;
		this.dificultad=dificultad;
		this.tiempo=tiempo;
	}
	
	public Receta(String nombre, String elaboracion,int dificultad, int tiempo){
		this.nombre=nombre;
		this.elaboracion=elaboracion;
		this.dificultad=dificultad;
		this.tiempo=tiempo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getElaboracion() {
		return elaboracion;
	}

	public void setElaboracion(String elaboracion) {
		this.elaboracion = elaboracion;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public Comida getComida() {
		return comida;
	}

	public void setComida(Comida comida) {
		this.comida = comida;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}	
}
