package Modelo;

import java.util.Set;

/**
 * Clase Ingrediente
 * @author SIR
 *
 */
public class Ingrediente {
	private int id;
	private String nombre;
	private boolean refrigeracion;
	
	private FamiliaIng familia;
	private Set<Receta> recetas;
	
	public Ingrediente(){
		
	}
	
	/**
	 * Constructor de la clase ingrediente
	 * @param id
	 * @param nombre
	 * @param familia
	 * @param refrigeracion
	 */
	public Ingrediente(int id,String nombre,FamiliaIng familia,boolean refrigeracion){
		this.id=id;
		this.nombre=nombre;
		this.familia=familia;
		this.refrigeracion=refrigeracion;
	}
	
	/**
	 * Constructor de la clase ingrediente
	 * @param nombre
	 * @param familia
	 * @param refrigeracion
	 */
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
