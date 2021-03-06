package Modelo;

import java.util.Set;

/**
 * Clase Familia de ingrediente
 * @author SIR
 *
 */
public class FamiliaIng {
	private int id;
	private String nombre;
	private String descripcion;
	
	private Set<Ingrediente> ingredientes;
	
	/**
	 * Constructor de la clase
	 */
	public FamiliaIng(){
		
	}
	
	/**
	 * Constructor de la clase Familia de Ingrediente
	 * @param nombre
	 * @param descripcion
	 */
	public FamiliaIng(String nombre, String descripcion){
		this.nombre=nombre;
		this.descripcion=descripcion;
	}
	
	/**
	 * Constructor de la clase Familia de Ingrediente
	 * @param id
	 * @param nombre
	 * @param descripcion
	 */
	public FamiliaIng(int id, String nombre, String descripcion){
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

	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	
}
