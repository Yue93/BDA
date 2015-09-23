package Vista;

import java.util.Scanner;

public class Visor {
	
	private Scanner sc;
	
	private enum Menu_Usuario{ Crear_Receta,Consultar_Receta,Actualizar_Receta,Eleminar_Receta};
	
	private String[] Menu_Usuario=("Crear Receta","Consultar Receta","Actualizar_Receta","Eliminar Receta");
	
	public static void main(String[] args){
		sc=new Scanner(System.in);
	}
	
	private void printMenu(){
		
	}
}
