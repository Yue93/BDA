package Modelo;

public class Ingrediente {
	private String nombre;
	private FamiliaIng familia;
	private boolean refrigeracion;
	
	
	public Ingrediente(String nombre,FamiliaIng familia,boolean refrigeracion){
		this.nombre=nombre;
		this.familia=familia;
		this.refrigeracion=refrigeracion;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public FamiliaIng getFamilia(){
		return this.familia;
	}
	
	public void setFamilia(FamiliaIng familia){
		this.familia=familia;
	}
	
	public boolean getRefrigeracion(){
		return this.refrigeracion;
	}
	
	public void setRefrigeracion(boolean refrigeracion){
		this.refrigeracion=refrigeracion;
	}
}
