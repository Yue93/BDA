package Modelo;

public class Usuario{
	private int id;
	private String nombre;
	private String password;
	
	public Usuario(int id,String nombre, String password){
		this.id=id;
		this.nombre=nombre;
		this.password=password;
	}
	
	public Usuario(String nombre, String password){
		this.nombre=nombre;
		this.password=password;
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
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
}
