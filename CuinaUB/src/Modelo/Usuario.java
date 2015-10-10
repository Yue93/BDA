package Modelo;

public class Usuario{
	private String nombre;
	private String password;
	
	public Usuario(String nombre, String password){
		
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
}
