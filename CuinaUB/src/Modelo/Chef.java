package Modelo;

import java.util.Set;

public class Chef {
	private int id;
	private String nombre;
	private String apellido;
	private int nEstrellas;
	
	private Set<Receta> recetas;
	
	public Chef(){
		
	}
	
	public Chef(int id,String nombre,String apellido,int nEstrellas){
		this.id=id;
		this.nombre=nombre;
		this.apellido=apellido;
		this.nEstrellas=nEstrellas;
	}
	
	public Chef(String nombre,String apellido,int nEstrellas){
		this.nombre=nombre;
		this.apellido=apellido;
		this.nEstrellas=nEstrellas;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public String getApellido(){
		return this.apellido;
	}
	
	public void setApellido(String apellido){
		this.apellido=apellido;
	}


	public int getnEstrellas() {
		return nEstrellas;
	}


	public void setnEstrellas(int nEstrellas) {
		this.nEstrellas = nEstrellas;
	}


	public Set<Receta> getRecetas() {
		return recetas;
	}


	public void setRecetas(Set<Receta> recetas) {
		this.recetas = recetas;
	}
}
