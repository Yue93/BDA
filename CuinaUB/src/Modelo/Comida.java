package Modelo;

import java.util.Set;

/**
 * Clase Comida
 * @author SIR
 *
 */
public class Comida {
	private int id;
	private String nombre;
	private String descripcion;
	
	private Set<Receta> recetas;
	
	/**
	 * Constructor vacio de la clase
	 */
	public Comida(){
		
	}
	
	/**
	 * Constructor de la clase
	 * @param nombre
	 * @param descripcion
	 */
	public Comida(String nombre, String descripcion){
		this.nombre=nombre;
		this.descripcion=descripcion;
	}

	/**
	 * Constructor de la clase
	 * @param id
	 * @param nombre
	 * @param descripcion
	 */
	public Comida(int id, String nombre, String descripcion){
		this.id=id;
		this.nombre=nombre;
		this.descripcion=descripcion;
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


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Set<Receta> getRecetas() {
		return recetas;
	}


	public void setRecetas(Set<Receta> recetas) {
		this.recetas = recetas;
	}
	
	
}
