package Vista;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import Controlador.ConnectorHB;
import Modelo.Chef;
import Modelo.Comida;
import Modelo.FamiliaIng;
import Modelo.Ingrediente;
import Modelo.Plato;
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
		}else{
			System.out.println("Usuario o contraseña incorrecto!");
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
                	nerrors=0;
                	conector.listReceta();
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
    	try{
    		System.out.println("Nombre de la receta?");
        	sc.nextLine();
        	String nombre=sc.nextLine();
        	System.out.println("Tiempo de eleboracion(en segundo)?");
        	int tiempo=sc.nextInt();
        	System.out.println("Descripcion de la elaboracion?");
        	sc.nextLine();
        	String elaboracion=sc.nextLine();
        	System.out.println("Dificultat de la elaboracion(1 a 10)?");
        	int dificultat=sc.nextInt();
        	
        	Receta receta=new Receta(nombre,elaboracion,dificultat,tiempo);
        	conector.saveReceta(receta);
    	}catch(NumberFormatException e ){
    		System.out.println("Formato incorrecto!");
    	}catch(InputMismatchException ime){
    		System.out.println("Dato introducido incorrecto!");
    	}
    	
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
                	nerrors=0;
                	afegirComida();
                    break;
                case Consultar_Comida:                   
                	nerrors=0;
                	conector.listComida();
                    break;
                case Actualizar_Comida:                    
                	nerrors=0;
                	System.out.println("Has escogido el tipo de comida");
                	conector.listComida();
                    break;
                case Eliminar_Comida:
                	nerrors=0;
                	System.out.println("Has escogido el chef");
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
    
    
    private static void afegirComida(){
    	System.out.println("Nombre de la comidas:");
    	sc.nextLine();
    	String nombre=sc.nextLine(); 	
    	System.out.println("Descripcion:");
    	String descripcion=sc.nextLine();
    	Comida comida=new Comida(nombre,descripcion);
    	conector.saveComida(comida);
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
                	nerrors=0;
                	afegirPlato();
                    break;
                case Consultar_Plato:    
                	nerrors=0;
                	conector.listPlato();
                    break;
                case Actualizar_Plato:                    
                	nerrors=0;
                	System.out.println("Has escogido el tipo de comida");
                	conector.listPlato();
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
            	printMenu(opcionsMenuPlato);
            } 
        }
    }
    
    private static void afegirPlato(){
    	System.out.println("Que tipo de plato es?");
    	sc.nextLine();
    	String nombre=sc.nextLine(); 	
    	System.out.println("Descripcion:");
    	String descripcion=sc.nextLine();
    	Plato plato=new Plato(nombre,descripcion);
    	conector.savePlato(plato);
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
                	nerrors=0;
                	afegirIngrediente();
                    break;
                case Consultar_Ingrediente:          
                	nerrors=0;
                	conector.listIngrediente();
                    break;
                case Actualizar_Ingrediente:   
                	nerrors=0;
                	System.out.println("Has escogido el tipo de comida");
                	conector.listIngrediente();
                    break;
                case Eliminar_Ingrediente:
                	nerrors=0;
                	System.out.println("Has escogido el chef");
                	
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
    
    //Not finish method
    private static void afegirIngrediente(){
    	try{
	    	boolean boolRefrigeracion=false;
	    	FamiliaIng familia=null;
	    	System.out.println("Nombre del ingrediente:");
	    	sc.nextLine();
	    	String nombre=sc.nextLine(); 	
	    	System.out.println("Refrigeracion? Yes(y)/No(n)");
	    	String refrigeracion=sc.next();
	    	if(refrigeracion.toLowerCase().equals("y")){
	    		boolRefrigeracion=true;
	    	}
	    	conector.listFamiliaIng();
	    	System.out.println("Id de la familia a la que pertenece?");
	    	int idFamilia=sc.nextInt();
	    	familia=conector.getFamiliaIng(idFamilia);
	    	Ingrediente ingrediente=new Ingrediente(nombre,familia,boolRefrigeracion);
	    	conector.saveIngrediente(ingrediente);
    	}catch(InputMismatchException ime){
    		System.out.println("Tipo de datos incorrecto!");
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
                	nerrors=0;
                	afegirFamiliaIng();
                    break;
                case Consultar_Familia_Ingrediente:   
                	nerrors=0;
                	conector.listFamiliaIng();
                    break;
                case Actualizar_Familia_Ingrediente:    
                	nerrors=0;
                	System.out.println("Has escogido el tipo de comida");
                	conector.listFamiliaIng();
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
    
    private static void afegirFamiliaIng(){
    	System.out.println("Nombre de la familia:");
    	sc.nextLine();
    	String nombre=sc.nextLine(); 	
    	System.out.println("Descripcion:");
    	String descripcion=sc.nextLine();
    	FamiliaIng familia=new FamiliaIng(nombre,descripcion);
    	conector.saveFamiliaIng(familia);
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
                	nerrors=0;
                	afegirChef();
                    break;
                case Consultar_Chef:                   
                	nerrors=0;
                	conector.listChef();
                    break;
                case Actualizar_Chef:       
                	nerrors=0;
                	System.out.println("Has escogido el tipo de comida");
                	conector.listChef();
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
    
    private static void afegirChef(){
    	try{
    		System.out.println("Nombre del chef:");
        	String nombre=sc.next();
        	System.out.println("Apellido del chef:");
        	String apellido=sc.next();
        	System.out.println("Numero de estrella michelin:");
        	int nEstrella=sc.nextInt();
        	Chef chef=new Chef(nombre,apellido,nEstrella);
        	conector.saveChef(chef);
    	}catch(InputMismatchException e){
    		System.out.println("Dato introducido incorrecto!");
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
