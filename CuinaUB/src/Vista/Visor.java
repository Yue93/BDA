package Vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import Controlador.ConnectorHB;
import Modelo.Receta;
//import Modelo.Usuario;
import Modelo.Usuario;

public class Visor {
	
	private static int nerrors;
	private static Scanner sc;
	
	private static String username="test";
	private static String password="test";
	
	static private String usuario="";
	
	static private String contrasenya="";
	
	static private enum MenuReceta{ Añadir_Nueva_Receta,Consultar_Receta,Actualizar_Receta,Eliminar_Receta,Salir};
	static private enum MenuComida{ Añadir_Nueva_Comida,Consultar_Comida,Actualizar_Comida,Eliminar_Comida,Salir};
	static private enum MenuPlato{ Añadir_Nuevo_Plato,Consultar_Plato,Actualizar_Plato,Eliminar_Plato,Salir};
	static private enum MenuIngrediente{ Añadir_Nuevo_Ingrediente,Consultar_Ingrediente,Actualizar_Ingrediente,Eliminar_Ingrediente,Salir};
	static private enum MenuFamiliaIng{ Añadir_Nueva_Familia_Ingrediente,Consultar_Familia_Ingrediente,Actualizar_Familia_Ingrediente,Eliminar_Familia_Ingrediente,Salir};
	static private enum MenuChef{ Añadir_Nuevo_Chef,Consultar_Chef,Actualizar_Chef,Eliminar_Chef,Salir};
	static private enum MenuElemento{Receta,Tipo_Plato,Tipo_Comida,Chef,Ingrediente,Familia_Ingrediente,Salir};
	
	private static String[] opcionsMenuReceta={ "Añadir_Nueva_Receta","Consultar_Receta","Actualizar_Receta","Eliminar_Receta","Salir"};
	private static String[] opcionsMenuComida={ "Añadir_Nueva_Comida","Consultar_Comida","Actualizar_Comida","Eliminar_Comida","Salir"};
	private static String[] opcionsMenuPlato={ "Añadir_Nuevo_Plato","Consultar_Plato","Actualizar_Plato","Eliminar_Plato","Salir"};
	private static String[] opcionsMenuIngrediente={ "Añadir_Nuevo_Ingrediente","Consultar_Ingrediente","Actualizar_Ingrediente","Eliminar_Ingrediente","Salir"};
	private static String[] opcionsMenuFamiliaIng={ "Añadir_Nueva_Familia_Ingrediente","Consultar_Familia_Ingrediente","Actualizar_Familia_Ingrediente","Eliminar_Familia_Ingrediente","Salir"};
	private static String[] opcionsMenuChef={ "Añadir_Nueva_Chef","Consultar_Chef","Actualizar_Chef","Eliminar_Chef","Salir"};
	private static String[] opcionsMenuElemento={"Receta","Tipo_Plato","Tipo_Comida","Chef","Ingrediente","Familia_Ingrediente","Salir"};
	
	private static ConnectorHB conector;
	
	private static Usuario user;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args){
		nerrors=0;
		sc=new Scanner(System.in);
		System.out.println("Entra el nombre de usuario y la contrasenya:");
		usuario=sc.next();
		contrasenya=sc.next();
		if(checkUser(usuario,contrasenya)){
			System.out.println("===========================================");
			System.out.println("                 Welcome                   ");
			System.out.println("===========================================");
			//user=new Usuario(usuario,contrasenya);
			conector=new ConnectorHB();
			conector.conectar();
			gestionMenuPrincipal();
		}	
	}
	
	
	private static boolean checkUser(String enterUsername,String enterPassaword){
		boolean correct=false;
		if(username.equals(enterUsername) && password.equals(enterPassaword)){
			correct=true;
		}
		return correct;
	}
	
	/**
     * Método donde gestionamo nuestra primer menú textual.Donde usuario decide si empezar una
     * nueva partida o acabar.
     */
    private static void gestionMenuPrincipal(){
        int opcion;
        boolean finalizar;
        finalizar=false;
        //Imprimimos el menu
        printMenu(opcionsMenuElemento);
        while(!finalizar){
	        //Guardamos la opcion escogida por el usuario
	        opcion=getOpcion(sc,opcionsMenuElemento);
	        //Hacemos la conversion textual de la opcion escogida
	        MenuElemento opcionMenuInicio=MenuElemento.valueOf(opcionsMenuElemento[opcion-1]);
	        switch(opcionMenuInicio){
	            case Receta:     
	            	nerrors=0;
	            	gestionMenuReceta(sc);
	                break;
	            case Tipo_Plato:      
	            	nerrors=0;
	            	gestionMenuPlato(sc);
	            	break;
	            case Tipo_Comida:
	            	nerrors=0;
	            	gestionMenuComida(sc);
	            	break;
	            case Ingrediente:
	            	nerrors=0;
	            	gestionMenuIngrediente(sc);
	            	break;
	            case Familia_Ingrediente:
	            	nerrors=0;
	            	gestionMenuFamiliaIng(sc);
	            	break;
	            case Chef:
	            	nerrors=0;
	            	gestionMenuChef(sc);
	            	break;
	            default:
	            	System.out.println("Programa Finalizado");
	            	//conector.close();
	            	finalizar=true;
	                break;
	        }
	        if(!finalizar){
            	printMenu(opcionsMenuElemento);
            } 
        }
    }
    
    
    /**
     * Método de gestion del menú del juego.Donde usuario debe escoger si quiere robar una carta
     * aumentar apuesta o pasar su turno y acabar el juego.
     * @param sc 
     */
    private static void gestionMenuReceta(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuReceta);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuReceta);
            MenuReceta opcionMenuElemento=MenuReceta.valueOf(opcionsMenuReceta[opcion-1]);
            switch(opcionMenuElemento){
                case Añadir_Nueva_Receta:  
                	nerrors=0;
                	afegirReceta();
                    break;
                case Consultar_Receta:   
                	nerrors=0;
                	conector.listReceta();
                    break;
                case Actualizar_Receta:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Receta:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                default:
                	System.out.println("Volviendo al menu principal");
                	salir=true;
                    break;
            }
            if(!salir){
            	printMenu(opcionsMenuReceta);
            } 
        }
    }

    private static void afegirReceta(){
    	System.out.println("Nombre de la receta?");
    	String nombre=sc.next();
    	System.out.println("Descripcion de la elaboracion?");
    	String elaboracion=sc.nextLine();
    	sc.nextLine();
    	System.out.println("Dificultat de la elaboracion(1 a 10)?");
    	int dificultat=sc.nextInt();
    	System.out.println("Tiempo de eleboracion(en segundo)?");
    	int tiempo=sc.nextInt();
    	Receta receta=new Receta(nombre," ",dificultat,tiempo);
    	conector.saveReceta(receta);
    }
	
    private static void gestionMenuComida(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuComida);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuComida);
            MenuComida opcionMenuElemento=MenuComida.valueOf(opcionsMenuComida[opcion-1]);
            switch(opcionMenuElemento){
                case Añadir_Nueva_Comida:  
                	System.out.println("Has escogido la receta");
                	nerrors=0;
                    break;
                case Consultar_Comida:                   
                	System.out.println("Has escogido el tipo de plato");
                	nerrors=0;
                    break;
                case Actualizar_Comida:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Comida:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                default:
                	System.out.println("Volviendo al menu principal");
                	salir=true;
                    break;
            }
            if(!salir){
            	printMenu(opcionsMenuComida);
            } 
        }
    }
    
    
    private static void gestionMenuPlato(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuPlato);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuPlato);
            MenuPlato opcionMenuElemento=MenuPlato.valueOf(opcionsMenuPlato[opcion-1]);
            switch(opcionMenuElemento){
                case Añadir_Nuevo_Plato:  
                	System.out.println("Has escogido la receta");
                	nerrors=0;
                    break;
                case Consultar_Plato:                   
                	System.out.println("Has escogido el tipo de plato");
                	nerrors=0;
                    break;
                case Actualizar_Plato:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Plato:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                default:
                	System.out.println("Volviendo al menu principal");
                	salir=true;
                    break;
            }
            if(!salir){
            	printMenu(opcionsMenuComida);
            } 
        }
    }
    
    
    
    private static void gestionMenuIngrediente(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuIngrediente);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuIngrediente);
            MenuIngrediente opcionMenuElemento=MenuIngrediente.valueOf(opcionsMenuIngrediente[opcion-1]);
            switch(opcionMenuElemento){
                case Añadir_Nuevo_Ingrediente:  
                	System.out.println("Has escogido la receta");
                	nerrors=0;
                    break;
                case Consultar_Ingrediente:                   
                	System.out.println("Has escogido el tipo de plato");
                	conector.listIngredientes();
                	nerrors=0;
                    break;
                case Actualizar_Ingrediente:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Ingrediente:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                default:
                	System.out.println("Volviendo al menu principal");
                	salir=true;
                    break;
            }
            if(!salir){
            	printMenu(opcionsMenuIngrediente);
            } 
        }
    }
    
    
    
    private static void gestionMenuFamiliaIng(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuFamiliaIng);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuFamiliaIng);
            MenuFamiliaIng opcionMenuElemento=MenuFamiliaIng.valueOf(opcionsMenuFamiliaIng[opcion-1]);
            switch(opcionMenuElemento){
                case Añadir_Nueva_Familia_Ingrediente:  
                	System.out.println("Has escogido la receta");
                	nerrors=0;
                    break;
                case Consultar_Familia_Ingrediente:                   
                	System.out.println("Has escogido el tipo de plato");
                	nerrors=0;
                    break;
                case Actualizar_Familia_Ingrediente:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Familia_Ingrediente:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                default:
                	System.out.println("Volviendo al menu principal");
                	salir=true;
                    break;
            }
            if(!salir){
            	printMenu(opcionsMenuFamiliaIng);
            } 
        }
    }
    
    
    
    private static void gestionMenuChef(Scanner sc){
        int opcion;
        boolean salir;
        salir=false;
        //Imprimimos el menu
        printMenu(opcionsMenuChef);
        while(!salir){        	
            opcion=getOpcion(sc,opcionsMenuChef);
            MenuChef opcionMenuElemento=MenuChef.valueOf(opcionsMenuChef[opcion-1]);
            switch(opcionMenuElemento){
                case Añadir_Nuevo_Chef:  
                	System.out.println("Has escogido la receta");
                	nerrors=0;
                    break;
                case Consultar_Chef:                   
                	System.out.println("Has escogido el tipo de plato");
                	nerrors=0;
                    break;
                case Actualizar_Chef:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Chef:
                	System.out.println("Has escogido el chef");
                	nerrors=0;
                	break;
                default:
                	System.out.println("Volviendo al menu principal");
                	salir=true;
                    break;
            }
            if(!salir){
            	printMenu(opcionsMenuChef);
            }           
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
        System.out.println("Escoge una opcion:");
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
