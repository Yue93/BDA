package Modelo;

import java.util.Set;

public class Ingrediente {
	private int id;
	private String nombre;
	private FamiliaIng familia;
	private boolean refrigeracion;
	
	private Set<Receta> recetas;
	
	public Ingrediente(){
		
	}
	
	public Ingrediente(int id,String nombre,FamiliaIng familia,boolean refrigeracion){
		this.id=id;
		this.nombre=nombre;
		this.familia=familia;
		this.refrigeracion=refrigeracion;
	}
	
	public Ingrediente(String nombre,FamiliaIng familia,boolean refrigeracion){
		this.nombre=nombre;
		this.familia=familia;
		this.refrigeracion=refrigeracion;
	}

	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public FamiliaIng getFamilia() {
		return familia;
	}

	public void setFamilia(FamiliaIng familia) {
		this.familia = familia;
	}

	public boolean isRefrigeracion() {
		return refrigeracion;
	}

	public void setRefrigeracion(boolean refrigeracion) {
		this.refrigeracion = refrigeracion;
	}

	public Set<Receta> getRecetas() {
		return recetas;
	}

	public void setRecetas(Set<Receta> recetas) {
		this.recetas = recetas;
	}
	
	
}
