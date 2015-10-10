package Modelo;

import java.util.Set;

public class Comida {
	private int id;
	private String nombre;
	private String descripcion;
	
	private Set<Receta> recetas;
	
	
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
