package Controlador;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.GenericJDBCException;

import Modelo.Chef;
import Modelo.Comida;
import Modelo.FamiliaIng;
import Modelo.Ingrediente;
import Modelo.Plato;
import Modelo.Receta;

import java.util.List;

import javax.persistence.EntityManager;

@SuppressWarnings("deprecation")
public class ConnectorHB {	
    
    private static SessionFactory sf = null;
    private static Session session = null;
    private static Transaction tx=null;
    
    public ConnectorHB(){ }
    
    public static void conectar(){
    	try {
            sf = new Configuration().configure().buildSessionFactory();
            //Marcamos la opcion anotaciones
            //sf = new AnnotationConfiguration().configure().buildSessionFactory();
            System.out.println("Instanciando SF");
        } catch (HibernateException e) {
            System.out.println("Error SF: "+e.getMessage());
        }

    }
    
    
    public static Session getSession(){
        Session session = sf.openSession();
        return session;
    }
    
    public void close(){
    	if(session!=null){
    		session.close();
    	}
    }
    
    /*SAVE ACTIONS*/
    
    /*RECETA*/
    public void saveReceta(Receta receta){
    	try{	
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
    		session.save(receta);
    		tx.commit();
    	}catch(HibernateException e){
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }
    
    /*PLATO*/
    public void savePlato(Plato plato){
    	try{	
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
    		session.save(plato);
    		tx.commit();
    	}catch(HibernateException e){
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }
    
    /*COMIDA*/
    public void saveComida(Comida comida){
    	try{	
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
    		session.save(comida);
    		tx.commit();
    	}catch(HibernateException e){
    		if (tx!=null) tx.rollback();
            e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }
    
    /*INGREDIENTES*/
    public void saveIngrediente(Ingrediente ingrediente){
    	try{	
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
    		session.save(ingrediente);
    		tx.commit();
    	}catch(HibernateException e){
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }
    
    /*FAMILIA ING*/
    public void saveFamiliaIng(FamiliaIng familiaing){
    	try{	
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
    		session.save(familiaing);
    		tx.commit();
    	}catch(HibernateException e){
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }
    
    /*CHEF*/
    public void saveChef(Chef chef){
    	try{	
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
    		session.save(chef);
    		tx.commit();
    	}catch(HibernateException e){
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    }
    
    /*LIST ACTIONS*/
    
    /*RECETA*/    
    public List<Receta> listReceta(){
    	List<Receta> recetas = null;
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		recetas = session.createSQLQuery("Select * FROM Receta").addEntity(Receta.class).list();
    		if(recetas==null){
                System.out.println("No hay recetas! Se buena persona y añade algunas!");
            }else{
                System.out.println("--------------------------------");
                System.out.println("        Lista de recetas        ");
                System.out.println("--------------------------------");
                for(Receta receta:recetas){
                    System.out.println("Id receta: "+receta.getId()+" --- "+"Nombre: "+receta.getNombre());
                    System.out.println("Description: "+receta.getElaboracion());
                    System.out.println("Dificultad: "+receta.getDificultad());
                    System.out.println("Tiempo: "+receta.getTiempo());
                }
            }
    		tx.commit();
    	}catch(HibernateException e) {
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
		return recetas;
    }
    
    /*PLATO*/
    public List<Plato> listPlato(){
    	List<Plato> platos=null;
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		platos=session.createSQLQuery("Select * FROM Plato").addEntity(Plato.class).list();
    		System.out.println("--------------------------------");
            System.out.println("        Lista de platos     ");
            System.out.println("--------------------------------");
            for(Plato plato:platos){
                System.out.println("Id plato: "+plato.getId()+" ---- "+"Nombre: "+plato.getNombre());
            }
    		tx.commit();
    	}catch(HibernateException e) {
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return platos;
    }
    
    /*COMIDA*/
    public List<Comida> listComida(){
    	List<Comida> comidas=null;
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		comidas=session.createSQLQuery("Select * FROM Comida").addEntity(Comida.class).list();
    		System.out.println("--------------------------------");
            System.out.println("        Lista de comidas        ");
            System.out.println("--------------------------------");
            for(Comida comida: comidas){
                System.out.println("Id: "+comida.getId()+"\nNombre: "+comida.getNombre());
                System.out.println("Descripcion: "+comida.getDescripcion());
            }
    		tx.commit();
    	}catch(HibernateException e) {
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
		return comidas;
    }
    
    /*CHEF*/
    public List<Chef> listChef(){
    	List<Chef> chefs=null;
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		chefs=session.createSQLQuery("Select * FROM Chef").addEntity(Chef.class).list();
    		System.out.println("--------------------------------");
            System.out.println("        Lista de chefs      ");
            System.out.println("--------------------------------");
            for(Chef chef:chefs){
                System.out.println("Nombre: "+chef.getNombre());
            }
    		tx.commit();
    	}catch(HibernateException e) {
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return chefs;
    }    
    
    /*INGREDIENTES*/
    public List<Ingrediente> listIngredientes(){
    	List<Ingrediente> ingredientes=null;
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		ingredientes=session.createSQLQuery("Select * FROM Ingrediente").addEntity(Ingrediente.class).list();
    		System.out.println("------------------------------------");
            System.out.println("        Lista de ingredientess      ");
            System.out.println("------------------------------------");
            for(Ingrediente ingrediente: ingredientes){
                System.out.println("Id Ingrediente: "+ingrediente.getId()+" --- "+"Nombre: "+ingrediente.getNombre());
            }
    		tx.commit();
    	}catch(HibernateException e) {
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return ingredientes;
    }
    
    /*FAMILIA ING*/
    public List<FamiliaIng> listFamiliaIng(){
    	List<FamiliaIng> familias = null;
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		familias=session.createSQLQuery("Select * FROM FamiliaIng").addEntity(FamiliaIng.class).list();
    		System.out.println("--------------------------------");
            System.out.println("        Lista de familias       ");
            System.out.println("--------------------------------");
            for(FamiliaIng familia: familias){
                System.out.println("ID: "+familia.getId()+" Nombre: "+familia.getNombre());
            }
    		tx.commit();
    	}catch(HibernateException e) {
            if (tx!=null) tx.rollback();
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
    	
		return familias;
    }
    
    /*DELETE ACTIONS*/
    /*RECETA*/
	public void deleteReceta(Receta receta) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		session.delete(receta);
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*PLATO*/
	public void deletePlato(Plato plato) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		session.delete(plato);
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*COMIDA*/
	public void deleteComida(Comida comida) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		session.delete(comida);
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*INGREDIENTE*/
	public void deleteIngrediente(Ingrediente ingrediente) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		session.delete(ingrediente);
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*FAMILIA INGREDIENTE*/
	public void deleteFamiliaIng(FamiliaIng familiaing) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		session.delete(familiaing);
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}

	/*CHEF*/
	public void deleteChef(Chef chef) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		session.delete(chef);
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
    public void update(){
        
    }
    
    public FamiliaIng getFamiliaIng(int idFamilia){
        FamiliaIng familia=null;
        try{
            session=sf.openSession();
            tx=session.beginTransaction();
            familia=(FamiliaIng) session.get(FamiliaIng.class, idFamilia);
            tx.commit();
        }catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return familia;
    }
}