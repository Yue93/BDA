package Vista;

import java.util.ArrayList;
import java.util.Scanner;

import Modelo.Usuario;

public class Visor {
	
	private static int nerrors;
	private static Scanner sc;
	
	static private String usuario="";
	
	static private String contrasenya="";
	
	static private enum MenuUsuario{ Crear_Receta,Consultar_Receta,Actualizar_Receta,Eliminar_Receta,Salir};
	
	static private enum MenuElemento{Receta,Tipo_Plato,Tipo_Comida,Chef,Ingredientes,Salir};
	
	private static String[] opcionsMenuUsuario={"Crear_Receta","Consultar_Receta","Actualizar_Receta","Eliminar_Receta","Salir"};
	
	private static String[] opcionsMenuElemento={"Receta","Tipo_Plato","Tipo_Comida","Chef","Ingredientes","Salir"};
	
	private ArrayList<Usuario> usuarios=new ArrayList();
	
	public static void main(String[] args){
		nerrors=0;
		sc=new Scanner(System.in);
		System.out.println("Entra el nombre de usuario y la contrasenya:");
		usuario=sc.next();
		contrasenya=sc.next();
		gestionMenuUsuario();
		
	}
	
	
	
	/**
     * Método donde gestionamo nuestra primer menú textual.Donde usuario decide si empezar una
     * nueva partida o acabar.
     */
    private static void gestionMenuUsuario(){
        int opcion;
        boolean finalizar;
        finalizar=false;
        //Imprimimos el menu
        printMenu(opcionsMenuUsuario);
        while(!finalizar){
	        //Guardamos la opcion escogida por el usuario
	        opcion=getOpcion(sc,opcionsMenuUsuario);
	        //Hacemos la conversion textual de la opcion escogida
	        MenuUsuario opcionMenuInicio=MenuUsuario.valueOf(opcionsMenuUsuario[opcion-1]);
	        switch(opcionMenuInicio){
	            case Crear_Receta:     
	            	nerrors=0;
	            	gestionMenuElemento(sc);
	                break;
	            case Consultar_Receta:      
	            	nerrors=0;
	            	gestionMenuElemento(sc);
	            	break;
	            case Actualizar_Receta:
	            	nerrors=0;
	            	gestionMenuElemento(sc);
	            	break;
	            case Eliminar_Receta:
	            	nerrors=0;
	            	gestionMenuElemento(sc);
	            	break;
	            default:
	            	finalizar=true;
	                break;
	        }
	        printMenu(opcionsMenuUsuario);
        }
    }
    
    
    /**
     * Método de gestion del menú del juego.Donde usuario debe escoger si quiere robar una carta
     * aumentar apuesta o pasar su turno y acabar el juego.
     * @param sc 
     */
    private static void gestionMenuElemento(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuElemento);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuElemento);
            MenuElemento opcionMenuElemento=MenuElemento.valueOf(opcionsMenuElemento[opcion-1]);
            switch(opcionMenuElemento){
                case Receta:  
                	System.out.println("Has escogido la receta");
                	nerrors=0;
                    break;
                case Tipo_Plato:                   
                	System.out.println("Has escogido el tipo de plato");
                	nerrors=0;
                    break;
                case Tipo_Comida:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Chef:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                case Ingredientes:
                	System.out.println("Has escogido los ingredientes");
                	nerrors=0;
                	break;
                default:
                	salir=true;
                    break;
            }
            printMenu(opcionsMenuElemento);
        }
    }

	
	/**
     * Método donde pedimos la entrada por el teclado la opcion que quiere escoger el 
     * usuario
     * @param sc
     * @param opcionsMenu lista que contiene las opciones disponibles del menu
     * @return devolvemos la opcion escogida
     */
    private static int getOpcion(Scanner sc, String[] opcionsMenu){
        String opcion;
        opcion=sc.next();
        //Bucle para comprobar el validez de la opcion escogida y el numero de intento
        while(!comprobarOpcion(opcion,opcionsMenu.length) && nerrors<2){
            nerrors+=1;
            System.out.println("Opcion Incorrecto");
            System.out.println("Escoge una opcion:");
            opcion=sc.next();
        }
        if(nerrors>=2 && !comprobarOpcion(opcion,opcionsMenu.length)){
            System.out.println("Has sobrepasado el numero maximo de intentos!!!");
            System.exit(1);
        }
        return Integer.valueOf(opcion);
    }
	
	
	/**
     * Metodo para imprimir las opciones disponibles de nuestro menu
     * @param menu lista que contienes las opciones del menu
     */
    private static void printMenu(String[] menu){
        int nopcions;
        nopcions=menu.length;
        System.out.println("================================");
        for(int i=0;i<nopcions;i++){
            System.out.println("    "+(i+1)+"."+menu[i]);
        }
        System.out.println("================================");
        System.out.println("Escull una opcio:");
    }
    
    
    /**
     * Metodo donde comprobamos si el usuario ha escogido una opcion correcta
     * @param opcion Es la opcion que ha entrado el usuario.Es un string
     * @param nopcionsMenu numero de opciones existentes en el menu.
     * @return 
     */
    private static boolean comprobarOpcion(String opcion,int nopcionsMenu){
        boolean opcionCorrecto;
        opcionCorrecto=true;
        char a;
        int opcio;
        a=opcion.charAt(0);
        if(!Character.isDigit(a) || opcion.length()>1){
            return false;
        }else{
            //Unicamente si la opcion escogida es menor que el numero total de opciones existentes
            //,entonces la entrada es correcta
            opcio=Integer.valueOf(opcion);
            if(opcio<1 || opcio>nopcionsMenu){       
                opcionCorrecto=false;
            }
        }       
        return opcionCorrecto;
    }
}
