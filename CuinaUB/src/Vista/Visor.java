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
	
	static private enum MenuReceta{Añadir_Nueva_Receta,Consultar_Receta,Actualizar_Receta,Eliminar_Receta,Salir};
	static private enum MenuComida{Añadir_Nueva_Comida,Consultar_Comida,Actualizar_Comida,Eliminar_Comida,Salir};
	static private enum MenuPlato{Añadir_Nuevo_Plato,Consultar_Plato,Actualizar_Plato,Eliminar_Plato,Salir};
	static private enum MenuIngrediente{ Añadir_Nuevo_Ingrediente,Consultar_Ingrediente,Actualizar_Ingrediente,Eliminar_Ingrediente,Salir};
	static private enum MenuFamiliaIng{ Añadir_Nueva_Familia_Ingrediente,Consultar_Familia_Ingrediente,Actualizar_Familia_Ingrediente,Eliminar_Familia_Ingrediente,Salir};
	static private enum MenuChef{ Añadir_Nuevo_Chef,Consultar_Chef,Actualizar_Chef,Eliminar_Chef,Salir};
	static private enum MenuElemento{Receta,Tipo_Plato,Tipo_Comida,Chef,Ingrediente,Familia_Ingrediente,Salir};
	
	private static String[] opcionsMenuReceta={ "Añadir_Nueva_Receta","Consultar_Receta","Actualizar_Receta","Eliminar_Receta","Salir"};
	private static String[] opcionsMenuComida={ "Añadir_Nueva_Comida","Consultar_Comida","Actualizar_Comida","Eliminar_Comida","Salir"};
	private static String[] opcionsMenuPlato={ "Añadir_Nuevo_Plato","Consultar_Plato","Actualizar_Plato","Eliminar_Plato","Salir"};
	private static String[] opcionsMenuIngrediente={ "Añadir_Nuevo_Ingrediente","Consultar_Ingrediente","Actualizar_Ingrediente","Eliminar_Ingrediente","Salir"};
	private static String[] opcionsMenuFamiliaIng={ "Añadir_Nueva_Familia_Ingrediente","Consultar_Familia_Ingrediente","Actualizar_Familia_Ingrediente","Eliminar_Familia_Ingrediente","Salir"};
	private static String[] opcionsMenuChef={ "Añadir_Nuevo_Chef","Consultar_Chef","Actualizar_Chef","Eliminar_Chef","Salir"};
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
    
    /*RECETA*/
    
    /**
     * Método de gestion del menú de la aplicación. Donde el usuario debe escoger si quiere añadir una receta,
     * consultar la información de una receta ya existente, actualizar la info de una receta
     * eliminar una receta o salir del menú secundario para ir al principal
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
                	System.out.println("Has escogido actualizar receta");
                	nerrors=0;
                    break;
                case Eliminar_Receta:
                	nerrors=0;
                	deleteReceta();
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

    /**
     * Método para añadir una receta
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void afegirReceta(){
    	try{
            System.out.println("Nombre de la receta: ");
            sc.nextLine();
            String nombre=sc.nextLine();
            System.out.println("Tiempo de eleboracion(en minutos): ");
            int tiempo=sc.nextInt();
            System.out.println("Descripcion de la elaboracion: ");
            sc.nextLine();
            String elaboracion=sc.nextLine();
            System.out.println("Dificultat de la elaboracion(1 a 10): ");
            int dificultat=sc.nextInt();
            
            Receta receta=new Receta(nombre,elaboracion,dificultat,tiempo);
            conector.saveReceta(receta);
        }catch(NumberFormatException e ){
            System.out.println("Formato incorrecto!");
        }catch(InputMismatchException ime){
            System.out.println("Dato introducido incorrecto!");
        }
    }
	
    /**
     * Método para eliminar una receta
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void deleteReceta(){
    	List<Receta> recetas= conector.listReceta();
    	System.out.println("Escoge la receta a eliminar(id): ");
    	int id_receta=sc.nextInt();
    	int tamany = recetas.size();
    	boolean id_rec=comprobarOpcion(String.valueOf(id_receta),tamany+1);
    	if(id_rec){
        	Receta receta = recetas.get(id_receta-1);
        	conector.deleteReceta(receta);
    	}
    }
    
    /*PLATO*/
    /**
     * Método de gestion del menú de la aplicación. Donde el usuario debe escoger si quiere añadir un plato,
     * consultar la información de un plato ya existente, actualizar la info de un plato
     * eliminar un plato o salir del menú secundario para ir al principal
     * @param sc 
     */
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
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Plato:
                	nerrors=0;
                	deletePlato();
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
    
    /**
     * Método para añadir un plato
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void afegirPlato(){
    	System.out.println("Nombre del plato: ");
    	String nombre=sc.next();
    	System.out.println("Descripcion del plato: ");
    	String descripcion=sc.nextLine();
    	sc.nextLine();
    	
    	Plato plato=new Plato(nombre,descripcion);
    	conector.savePlato(plato);
    }
    
    /**
     * Método para eliminar un plato
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void deletePlato(){
    	List<Plato> platos= conector.listPlato();
    	System.out.println("Escoge el plato a eliminar(id): ");
    	int id_plato=sc.nextInt();
    	int tamany = platos.size();
    	boolean id_plat=comprobarOpcion(String.valueOf(id_plato),tamany+1);
    	if(id_plat){
        	Plato plato = platos.get(id_plato-1);
        	conector.deletePlato(plato);
    	}
    }
    
    /*COMIDA*/
    /**
     * Método de gestion del menú de la aplicación. Donde el usuario debe escoger si quiere añadir una comida,
     * consultar la información de una comida ya existente, actualizar la info de una comida
     * eliminar una comida o salir del menú secundario para ir al principal
     * @param sc 
     */
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
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Comida:
                	nerrors=0;
                	deleteComida();
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
    
    /**
     * Método para añadir una comida
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void afegirComida(){
    	System.out.println("Nombre de la comida: ");
    	String nombre=sc.next();
    	System.out.println("Descripcion: ");
    	String descripcion=sc.nextLine();
    	sc.nextLine();
    	
    	Comida comida=new Comida(nombre,descripcion);
    	conector.saveComida(comida);
    } 
    
    /**
     * Método para eliminar una comida
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void deleteComida(){
    	List<Comida> comidas= conector.listComida();
    	System.out.println("Escoge la comida a eliminar(id): ");
    	int id_comida=sc.nextInt();
    	int tamany = comidas.size();
    	boolean id_comer=comprobarOpcion(String.valueOf(id_comida),tamany+1);
    	if(id_comer){
        	Comida comida = comidas.get(id_comida-1);
        	conector.deleteComida(comida);
    	}
    }
    
    /*INGREDIENTE*/
    /**
     * Método de gestion del menú de la aplicación. Donde el usuario debe escoger si quiere añadir un ingrediente,
     * consultar la información de un ingrediente ya existente, actualizar la info de un ingrediente
     * eliminar un ingrediente o salir del menú secundario para ir al principal
     * @param sc 
     */
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
                	System.out.println("Lista de ingredientes"+"\n");
                	conector.listIngredientes();
                	nerrors=0;
                    break;
                case Actualizar_Ingrediente:                    
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Ingrediente:
                	nerrors=0;
                	deleteIngrediente();
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
    
    /**
     * Método para añadir un ingrediente
     * @param no le pasamos parametros
     * @return no retornamos nada*/
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
    
    /**
     * Método para eliminar un ingrediente
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void deleteIngrediente(){
    	List<Ingrediente> ingredientes= conector.listIngredientes();
    	
    	int tamany = ingredientes.size();
    	System.out.println("Escoge el ingrediente a eliminar(id): ");
    	int id_ing=sc.nextInt();

    	boolean id_ingre=comprobarOpcion(String.valueOf(id_ing),tamany+1);
    	if(id_ingre){
        	Ingrediente ingrediente = ingredientes.get(id_ing-1);
        	conector.deleteIngrediente(ingrediente);
    	}
    }
    
    /*FAMILIA ING*/
    /**
     * Método de gestion del menú de la aplicación. Donde el usuario debe escoger si quiere añadir una familia de ingredientes,
     * consultar la información de una familia de ingredientes ya existente, actualizar la info de una familia de ingredientes
     * eliminar una familia de ingredientes o salir del menú secundario para ir al principal
     * @param sc 
     */
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
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Familia_Ingrediente:
                	nerrors=0;
                	deleteFamiliaIng();
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
    
    /**
     * Método para añadir una familia de ingredientes
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void afegirFamiliaIng(){
    	System.out.println("Tipo de familia: ");
    	String nombre=sc.next();
    	System.out.println("Descripcion: ");
    	String descripcion=sc.nextLine();
    	sc.nextLine();
    	
    	FamiliaIng familiaing=new FamiliaIng(nombre,descripcion);
    	conector.saveFamiliaIng(familiaing);
    }
    
    /**
     * Método para eliminar una familia de ingredientes
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void deleteFamiliaIng(){
    	List<FamiliaIng> familiaings= conector.listFamiliaIng();
    	
    	int tamany = familiaings.size();
    	System.out.println("Escoge la familia de ingredientes a eliminar(id): ");
    	int id_famings=sc.nextInt();

    	boolean id_faming=comprobarOpcion(String.valueOf(id_famings),tamany+1);
    	if(id_faming){
        	FamiliaIng familiaing = familiaings.get(id_famings-1);
        	conector.deleteFamiliaIng(familiaing);
    	}
    }
    
    /*CHEF*/
    /**
     * Método de gestion del menú de la aplicación. Donde el usuario debe escoger si quiere añadir un chef,
     * consultar la información de un chef ya existente, actualizar la info de un chef
     * eliminar un chef o salir del menú secundario para ir al principal
     * @param sc 
     */
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
                	System.out.println("Has escogido el tipo de comida");
                	nerrors=0;
                    break;
                case Eliminar_Chef:
                	nerrors=0;
                	deleteChef();
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
     * Método para añadir a un chef
     * @param no le pasamos parametros
     * @return no retornamos nada*/
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
     * Método para eliminar a un chef
     * @param no le pasamos parametros
     * @return no retornamos nada*/
    private static void deleteChef(){
    	List<Chef> chefs= conector.listChef();
    	
    	int tamany = chefs.size();
    	System.out.println("Escoge el chef a eliminar(id): ");
    	int id_chefs=sc.nextInt();

    	boolean id_chef=comprobarOpcion(String.valueOf(id_chefs),tamany+1);
    	if(id_chef){
        	Chef chef = chefs.get(id_chefs-1);
        	conector.deleteChef(chef);
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
