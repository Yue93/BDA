package Modelo;

import java.sql.Time;

public class Receta {
	private int id;
	private String nombre;
	private String elaboracion;
	private int dificultad;
	private int tiempo;
	
	public Receta(int id,String nombre, String elaboracion,int dificultad, int tiempo){
		this.id=id;
		this.nombre=nombre;
		this.elaboracion=elaboracion;
		this.dificultad=dificultad;
		this.tiempo=tiempo;
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
	
	public String getElaboracion(){
		return this.elaboracion;
	}
	
	public void setElaboraicion(String elaboracion){
		this.elaboracion=elaboracion;
	}
	
	public int getDifucultad(){
		return this.dificultad;
	}
	
	public void setDificultad(int dificultad){
		this.dificultad=dificultad;
	}
	
	public int getTElaboracion(){
		return this.tiempo;
	}
	
	public void setTElaboracion(int tiempo){
		this.tiempo=tiempo;
	}
}
