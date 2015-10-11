package Controlador;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import Modelo.Chef;
import Modelo.Comida;
import Modelo.FamiliaIng;
import Modelo.Ingrediente;
import Modelo.Plato;
import Modelo.Receta;

import java.util.List;

@SuppressWarnings("deprecation")
public class ConnectorHB {
	private List<Chef> chefs;
	private List<Comida> comdas;
	private List<Ingrediente> ingredientes;
	private List<Plato> platos;
	private List<Receta> recetas;
	private List<FamiliaIng> familia;
	
    private static SessionFactory sf = null;
    private static Session session = null;
    private static Transaction tx=null;
    
    public ConnectorHB() {
    	try {
            sf = new Configuration().configure().buildSessionFactory();
            //Marcamos la opcion anotaciones
            session=sf.openSession();
            //sf = new AnnotationConfiguration().configure().buildSessionFactory();
            System.out.println("Instanciando SF");
        } catch (HibernateException e) {
            System.out.println("Error SF: "+e.getMessage());
        }finally{
        	if(session!=null) session.close();
        }

    }
    
    /**public void create(String type){
    	switch(type){
    		case Receta:
    			break;
    		case 
    	}
    }**/
    
    public void read(){
    	
    }
    
    public void update(){
    	
    }
    
    public void delete(){
    	
    }
    
    
}