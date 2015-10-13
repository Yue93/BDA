package Vista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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


/**
 * Clase Visor(Vista de la aplicacion)
 * @author SIR
 *
 */
public class Visor {
	
	private static int nerrors;
	private static Scanner sc;
	
	private static String username="test";
	private static String password="test";
	
	static private String usuario="";
	
	static private String contrasenya="";
	
	
	//Enum menu
	static private enum MenuReceta{Añadir_Nueva_Receta,Consultar_Receta,Actualizar_Receta,Eliminar_Receta,Salir};
	static private enum MenuComida{Añadir_Nueva_Comida,Consultar_Comida,Actualizar_Comida,Eliminar_Comida,Salir};
	static private enum MenuPlato{Añadir_Nuevo_Plato,Consultar_Plato,Actualizar_Plato,Eliminar_Plato,Salir};
	static private enum MenuIngrediente{ Añadir_Nuevo_Ingrediente,Consultar_Ingrediente,Actualizar_Ingrediente,Eliminar_Ingrediente,Salir};
	static private enum MenuFamiliaIng{ Añadir_Nueva_Familia_Ingrediente,Consultar_Familia_Ingrediente,Actualizar_Familia_Ingrediente,Eliminar_Familia_Ingrediente,Salir};
	static private enum MenuChef{ Añadir_Nuevo_Chef,Consultar_Chef,Actualizar_Chef,Eliminar_Chef,Salir};
	static private enum MenuElemento{Receta,Tipo_Plato,Tipo_Comida,Chef,Ingrediente,Familia_Ingrediente,Salir};
	
	
	//Menu options
	private static String[] opcionsMenuReceta={ "Añadir_Nueva_Receta","Consultar_Receta","Actualizar_Receta","Eliminar_Receta","Salir"};
	private static String[] opcionsMenuComida={ "Añadir_Nueva_Comida","Consultar_Comida","Actualizar_Comida","Eliminar_Comida","Salir"};
	private static String[] opcionsMenuPlato={ "Añadir_Nuevo_Plato","Consultar_Plato","Actualizar_Plato","Eliminar_Plato","Salir"};
	private static String[] opcionsMenuIngrediente={ "Añadir_Nuevo_Ingrediente","Consultar_Ingrediente","Actualizar_Ingrediente","Eliminar_Ingrediente","Salir"};
	private static String[] opcionsMenuFamiliaIng={ "Añadir_Nueva_Familia_Ingrediente","Consultar_Familia_Ingrediente","Actualizar_Familia_Ingrediente","Eliminar_Familia_Ingrediente","Salir"};
	private static String[] opcionsMenuChef={ "Añadir_Nuevo_Chef","Consultar_Chef","Actualizar_Chef","Eliminar_Chef","Salir"};
	private static String[] opcionsMenuElemento={"Receta","Tipo_Plato","Tipo_Comida","Chef","Ingrediente","Familia_Ingrediente","Salir"};
	
	//Controller
	private static ConnectorHB conector;
		
	/**
	 * Main method
	 * @param args
	 */
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
			conector=new ConnectorHB();
			conector.conectar();
			gestionMenuPrincipal();
		}else{
            System.out.println("Usuario o contraseña incorrecto!");
        }
	}
	
	/**
	 * Metodo para comprobar si el nombre de usuario y contraseña entrado es correcto
	 * @param enterUsername nombre de usuario entrado
	 * @param enterPassaword password entrado por usuario
	 * @return booleano que nos indica si la cuenta es correcto
	 */
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
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //--------------------------Receta-----------------------
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    
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
                	nerrors=0;
                    updateReceta();
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
    		Set<Integer> idIngredientes=new HashSet<Integer>();
    		boolean actualizar=true;
            List<Comida> comidas=conector.listComida();
	    	if(comidas!=null && comidas.size()>0){
	    		System.out.println("Entra el id de la comida: ");
	    		int idComida=sc.nextInt();
	    		List<Chef> chefs=conector.listChef();
	    		if(chefs!=null && chefs.size()>0){
	    			System.out.println("Entra el id del chef: ");
		    		int idChef=sc.nextInt();
	    			List<Plato> platos=conector.listPlato();
	    			if(platos!=null && platos.size()>0){
	    				System.out.println("Entra el id del plato: ");
	    	    		int idPlato=sc.nextInt();
	    				List<Ingrediente> ingredientes=conector.listIngredientes();
	    				if(ingredientes!=null && ingredientes.size()>0){
    				    	while(actualizar){
    				    		System.out.println("Escoge los ingredientes(-1 para terminar)");
    				    		int idIngrediente=sc.nextInt();
    				    		if(idIngrediente!=-1){
    				    			idIngredientes.add(idIngrediente);
    				    		}else{
    				    			actualizar=false;
    				    		}
    				    	}
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
	    		            conector.saveReceta(receta,idComida,idChef,idPlato,idIngredientes);
	    				}
	    			}
	    		}
	    	}
            
        }catch(NumberFormatException e ){
            System.out.println("Formato incorrecto!");
        }catch(InputMismatchException ime){
            System.out.println("Dato introducido incorrecto!");
        }
    }

    /**
     * Metodo para actualizar una receta.Para poder llevar a cabo la operacion debe existir
     * en la base de datos de la aplicacion algunos datos de la comida,chef y platos
     */
    private static void updateReceta(){
    	try{
	    	Set<Integer> idIngredientes=new HashSet<Integer>();
	    	boolean actualizar=true;
	    	List<Comida> comidas=conector.listComida();
	    	if(comidas!=null && comidas.size()>0){
	    		System.out.println("Entra el id de la comida: ");
	    		int idComida=sc.nextInt();
	    		List<Chef> chefs=conector.listChef();
	    		if(chefs!=null && chefs.size()>0){
	    			System.out.println("Entra el id del chef: ");
		    		int idChef=sc.nextInt();
	    			List<Plato> platos=conector.listPlato();
	    			if(platos!=null && platos.size()>0){
	    				System.out.println("Entra el id del plato: ");
	    	    		int idPlato=sc.nextInt();
	    				List<Ingrediente> ingredientes=conector.listIngredientes();
	    				if(ingredientes!=null && ingredientes.size()>0){
    				    	while(actualizar){
    				    		System.out.println("Escoge los ingredientes(-1 para terminar)");
    				    		int idIngrediente=sc.nextInt();
    				    		if(idIngrediente!=-1){
    				    			idIngredientes.add(idIngrediente);
    				    		}else{
    				    			actualizar=false;
    				    		}
    				    	}
    				    	List<Receta> recetas=conector.listReceta();
    				    	if(recetas!=null && recetas.size()>0){
		    			    	System.out.println("Entra la receta que quieres actualizar(ID):");
		    			    	int id=sc.nextInt();
		    			    	System.out.println("Nombre de la receta: ");
		    			    	sc.nextLine();
		    			    	String nombre=sc.nextLine();
		    			    	System.out.println("Tiempo de elaboracion?");
		    			    	int tiempo=sc.nextInt();
		    			    	System.out.println("Descripcion de la elaboracion: ");
		    			    	sc.nextLine();
		    			    	String elaboracion=sc.nextLine();
		    			    	System.out.println("Dificultat de la elaboracion(1 a 10): ");
		    			    	int dificultad=sc.nextInt();
		    			    	conector.updateReceta(id,idPlato,idChef,idComida,nombre,elaboracion,dificultad,tiempo,idIngredientes);
    				    	}
	    				}
	    			}
	    		}    		
	    	}	    	
    	}catch(InputMismatchException ime){
    		System.out.println("Entrada incorrecta!");
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
        	conector.deleteReceta(id_receta);
    	}
    }
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //--------------------------Plato-----------------------
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    
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
                	nerrors=0;
                	updatePlato();
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
    	sc.nextLine();
    	String nombre=sc.nextLine();
    	System.out.println("Descripcion del plato: ");
    	String descripcion=sc.nextLine();
    	
    	Plato plato=new Plato(nombre,descripcion);
    	conector.savePlato(plato);
    }
    
    /**
     * Metodo para actulizar los datos de un plato.Se pedira al usuario que entra todas las informaciones de golpe
     */
    private static void updatePlato(){
    	try{
	    	Set<Integer> idRecetas=new HashSet<Integer>();
	    	boolean actualizar=true;
	    	conector.listPlato();
	    	System.out.println("Entra el plato que quieres actualizar(ID):");
	    	int id=sc.nextInt();
	    	System.out.println("Nombre del plato: ");
	    	sc.nextLine();
	    	String nombre=sc.nextLine();
	    	System.out.println("Descripcion: ");
	    	String descripcion=sc.nextLine();
	    	if(conector.listReceta()!=null){
		    	while(actualizar){
		    		System.out.println("Escoge la receta(-1 para terminar)");
		    		int idReceta=sc.nextInt();
		    		if(idReceta!=-1){
		    			idRecetas.add(idReceta);
		    		}else{
		    			actualizar=false;
		    		}
		    	}
	    	}
	    	conector.updatePlato(id,nombre,descripcion,idRecetas);
    	}catch(InputMismatchException ime){
    		System.out.println("Entrada incorrecta!");
    	}
    	
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
        	conector.deletePlato(id_plato);
    	}
    }
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //--------------------------Comida-----------------------
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    
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
                	nerrors=0;
                	updateComida();
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
    	sc.nextLine();
    	String nombre=sc.nextLine();
    	System.out.println("Descripcion: ");
    	String descripcion=sc.nextLine();
    	
    	Comida comida=new Comida(nombre,descripcion);
    	conector.saveComida(comida);
    } 
    
    /**
     * Metodo para actualizar las infomarciones de una comida.Se escoge la comida introuduciendo su id
     * Si es correcto a posteriori se le pedira al usuario los nuevos datos de la comida
     */
    private static void updateComida(){
    	try{
	    	Set<Integer> idRecetas=new HashSet<Integer>();
	    	boolean actualizar=true;
	    	conector.listComida();
	    	System.out.println("Entra la comida que quieres actualizar(ID):");
	    	int id=sc.nextInt();
	    	System.out.println("Nombre de la comida: ");
	    	sc.nextLine();
	    	String nombre=sc.nextLine();
	    	System.out.println("Descripcion: ");
	    	String descripcion=sc.nextLine();
	    	if(conector.listReceta()!=null){
		    	while(actualizar){
		    		System.out.println("Escoge la receta(-1 para terminar)");
		    		int idReceta=sc.nextInt();
		    		if(idReceta!=-1){
		    			idRecetas.add(idReceta);
		    		}else{
		    			actualizar=false;
		    		}
		    	}
	    	}
	    	conector.updateComida(id,nombre,descripcion,idRecetas);
    	}catch(InputMismatchException ime){
    		System.out.println("Entrada incorrecta!");
    	}
    	
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
        	conector.deleteComida(id_comida);
    	}
    }
    
    
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //-----------------------Ingrediente---------------------
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    
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
                	nerrors=0;
                	updateIngrediente();
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
            List<FamiliaIng> familias=conector.listFamiliaIng();
            if(familias!=null && familias.size()>0){
	            System.out.println("Id de la familia a la que pertenece?");
	            int idFamilia=sc.nextInt();
	            familia=conector.getFamiliaIng(idFamilia);
	            if(familia!=null){
		            Ingrediente ingrediente=new Ingrediente(nombre,familia,boolRefrigeracion);
		            conector.saveIngrediente(ingrediente);
	            }
            }
        }catch(InputMismatchException ime){
            System.out.println("Tipo de datos incorrecto!");
        }
    }
    
    
    /**
     * Metodo para actualizar las informaciones de un ingrediente.Se le pedira al usuario el id 
     * del ingrediente que quiere modificar la informacion y a posteriori los nuevos datos del
     * ingrediente
     */
    private static void updateIngrediente(){
    	try{
	    	Set<Integer> idRecetas=new HashSet<Integer>();
	    	boolean actualizar=true;
	    	boolean boolRefrigeracion=false;
	    	FamiliaIng familia=null;
	    	List<Ingrediente> ingredientes=conector.listIngredientes();
	    	if(ingredientes!=null && ingredientes.size()>0){
	    		System.out.println("Seleccion el ingrediente que quieres actualizar(ID):");
		    	int id=sc.nextInt();
		    	System.out.println("Nombre del ingrediente:");
	            sc.nextLine();
	            String nombre=sc.nextLine();    
	            System.out.println("Refrigeracion? Yes(y)/No(n)");
	            String refrigeracion=sc.next();
	            if(refrigeracion.toLowerCase().equals("y")){
	                boolRefrigeracion=true;
	            }
	        	System.out.println("Id de la familia a la que pertenece?");
	            int idFamilia=sc.nextInt();
	            familia=conector.getFamiliaIng(idFamilia);
	            
		    	if(conector.listReceta()!=null){
			    	while(actualizar){
			    		System.out.println("Escoge la receta(-1 para terminar)");
			    		int idReceta=sc.nextInt();
			    		if(idReceta!=-1){
			    			idRecetas.add(idReceta);
			    		}else{
			    			actualizar=false;
			    		}
			    	}
		    	}
		    	conector.updateIngrediente(id,nombre,boolRefrigeracion,familia,idRecetas);
	    	}else{
	    		System.out.println("No hay ");
	    	}
	    	
    	}catch(InputMismatchException ime){
    		System.out.println("Entrada incorrecta!");
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
        	conector.deleteIngrediente(id_ing);
    	}
    }
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //------------------Familia Ingrediente------------------
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
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
                	nerrors=0;
                	updateFamiliaIng();
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
    	sc.nextLine();
    	String nombre=sc.nextLine();
    	System.out.println("Descripcion: ");
    	String descripcion=sc.nextLine();

    	FamiliaIng familiaing=new FamiliaIng(nombre,descripcion);
    	conector.saveFamiliaIng(familiaing);
    }
    
    
    /**
     * Metodo para actualizar los datos de una Familia de ingredientes.Se le pedira al usuario id de la familiar 
     * que quiere modificar los datos y a posteriori los nuevos datos.
     */
    private static void updateFamiliaIng(){
    	try{
	    	Set<Integer> idIngredientes=new HashSet<Integer>();
	    	boolean actualizar=true;
	    	conector.listFamiliaIng();
	    	System.out.println("Seleccion la familia de ingrediente que quieres actualizar(ID):");
	    	int id=sc.nextInt();
	    	System.out.println("Nombre de la familia: ");
	    	sc.nextLine();
	    	String nombre=sc.nextLine();
	    	System.out.println("Descripcion de la familia: ");
	    	String descripcion=sc.nextLine();
	    	List<Ingrediente> ingredientes=conector.listIngredientes();
	    	if(ingredientes!=null && ingredientes.size()>0){
		    	while(actualizar){
		    		System.out.println("Escoge la receta(-1 para terminar)");
		    		int idIngrediente=sc.nextInt();
		    		if(idIngrediente!=-1){
		    			idIngredientes.add(idIngrediente);
		    		}else{
		    			actualizar=false;
		    		}
		    	}
	    	}
	    	conector.updateFamiliaIng(id,nombre,descripcion,idIngredientes);
    	}catch(InputMismatchException ime){
    		System.out.println("Entrada incorrecta!");
    	}
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
        	conector.deleteFamiliaIng(id_famings);
    	}
    }
    
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //--------------------------Chef-------------------------
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
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
                	nerrors=0;
                	updateChef();
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
     * Metodo para actualizar los datos de un chef.Se le pedira al usuario que entra el id del chef que 
     * quiere modificar los datos y a posteriori los nuevos datos del chef.
     */
    private static void updateChef(){
    	try{
	    	Set<Integer> idRecetas=new HashSet<Integer>();
	    	boolean actualizar=true;
	    	conector.listChef();
	    	System.out.println("Seleccion el chef que quieres actualizar(ID):");
	    	int id=sc.nextInt();
	    	System.out.println("Nombre del chef: ");
	    	String nombre=sc.next();
	    	System.out.println("Apellido del chef: ");
	    	String apellido=sc.next();
	    	System.out.println("Numero de estrellas Michelin: ");
	    	int nEstrellas=sc.nextInt();
	    	List<Receta> recetas=conector.listReceta();
	    	if(recetas!=null && recetas.size()>0){
		    	while(actualizar){
		    		System.out.println("Escoge la receta(-1 para terminar)");
		    		int idReceta=sc.nextInt();
		    		if(idReceta!=-1){
		    			idRecetas.add(idReceta);
		    		}else{
		    			actualizar=false;
		    		}
		    	}
	    	}
	    	conector.updateChef(id,nombre,apellido,nEstrellas,idRecetas);
    	}catch(InputMismatchException ime){
    		System.out.println("Entrada incorrecta!");
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
        	conector.deleteChef(id_chefs);
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
     * @return booleano que nos indica si la opcion escogida existe
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
