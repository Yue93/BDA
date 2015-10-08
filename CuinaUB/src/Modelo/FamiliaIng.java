package Modelo;

import java.util.Set;

public class FamiliaIng {
	private int id;
	private String nombre;
	private String descripcion;
	
	private Set<Ingrediente> ingredientes;
	
	public FamiliaIng(int id, String nombre, String descripcion){
		this.id=id;
		this.nombre=nombre;
		this.descripcion=descripcion;
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
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}
}
