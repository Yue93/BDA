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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

@SuppressWarnings("deprecation")
public class ConnectorHB {	
    
    private static SessionFactory sf = null;
    private static Session session = null;
    private static Transaction tx=null;

    public ConnectorHB(){}
    
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
    
    
    /*+++++++++++++++SAVE ACTIONS+++++++++++++++++*/
    
    
    /*RECETA*/
    public void saveReceta(Receta receta,int idComida, int idChef, int idPlato, Set<Integer> idIngredientes){
    	try{	
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		Set<Ingrediente> ingredientes=new HashSet<Ingrediente>();
    		Plato plato=(Plato)session.get(Plato.class,idPlato);
	    	Chef chef=(Chef)session.get(Chef.class, idChef);
	    	Comida comida=(Comida)session.get(Comida.class,idComida);
	    	for(int idActual:idIngredientes){
    			Ingrediente ingrediente=(Ingrediente)session.get(Ingrediente.class, idActual);
    			if(ingrediente!=null){
    				ingredientes.add(ingrediente);
    			}
    		}
	    	if(plato!=null && chef!=null && comida!=null && ingredientes.size()>0){
	    		receta.setComida(comida);
		    	receta.setChef(chef);
		    	receta.setPlato(plato);
		    	receta.setIngredientes(ingredientes);
	    		session.save(receta);	    		
	    	}
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
    
    
    
    /*++++++++++++++++++LIST ACTIONS+++++++++++++++*/
    
    
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
                System.out.println("Id: "+plato.getId()+"\nNombre: "+plato.getNombre());
                System.out.println("Descripcion: "+plato.getDescripcion());
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
                System.out.println("Id: "+chef.getId()+"\nNombre: "+chef.getNombre());
                System.out.println("Apellido: "+chef.getApellido());
                System.out.println("Numero Estrellas: "+chef.getnEstrellas());
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
                System.out.println("Refrigeracion: "+ingrediente.isRefrigeracion());
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
                System.out.println("ID: "+familia.getId()+"\nNombre: "+familia.getNombre());
                System.out.println("Descripcion: "+familia.getDescripcion());
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
    
    
    /*++++++++++++++++Update ACTIONS+++++++++++++++++++*/
    
    public void updateComida(int id,String nombre, String descripcion, Set<Integer> idRecetas){
    	try{
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
	    	Comida comida=(Comida)session.get(Comida.class,id);
	    	if(comida!=null){
	    		Set<Receta> recetas=new HashSet<Receta>();
	    		comida.setNombre(nombre);
	    		comida.setDescripcion(descripcion);
	    		for(int idActual:idRecetas){
	    			Receta receta=(Receta)session.get(Receta.class, idActual);
	    			if(receta!=null){
	    				recetas.add(receta);
	    			}
	    		}
	    		comida.setRecetas(recetas);
	    		tx.commit();
	    	}
    	}catch(HibernateException e){
    		System.out.println("No se ha podido realizar la operacion!");
    	}finally{
    		session.close();
    	}
    }
    
    
    public void updatePlato(int id,String nombre, String descripcion, Set<Integer> idRecetas){
    	try{
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
	    	Plato plato=(Plato)session.get(Plato.class,id);
	    	if(plato!=null){
	    		Set<Receta> recetas=new HashSet<Receta>();
	    		plato.setNombre(nombre);
	    		plato.setDescripcion(descripcion);
	    		for(int idActual:idRecetas){
	    			Receta receta=(Receta)session.get(Receta.class, idActual);
	    			if(receta!=null){
	    				recetas.add(receta);
	    			}
	    		}
	    		plato.setRecetas(recetas);
	    		tx.commit();
	    	}
    	}catch(HibernateException e){
    		System.out.println("No se ha podido realizar la operacion!");
    	}finally{
    		session.close();
    	}
    }
    
    
    public void updateChef(int id,String nombre, String apellido, int nEstrellas, Set<Integer> idRecetas){
    	try{
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
	    	Chef chef=(Chef)session.get(Chef.class,id);
	    	if(chef!=null){
	    		Set<Receta> recetas=new HashSet<Receta>();
	    		chef.setNombre(nombre);
	    		chef.setApellido(apellido);
	    		chef.setnEstrellas(nEstrellas);
	    		for(int idActual:idRecetas){
	    			Receta receta=(Receta)session.get(Receta.class, idActual);
	    			if(receta!=null){
	    				recetas.add(receta);
	    			}
	    		}
	    		chef.setRecetas(recetas);
	    		tx.commit();
	    	}
    	}catch(HibernateException e){
    		System.out.println("No se ha podido realizar la operacion!");
    	}finally{
    		session.close();
    	}
    }
    
    
    public void updateIngrediente(int id,String nombre, boolean boolRefrigeracion, FamiliaIng familia, Set<Integer> idRecetas){
    	try{
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
	    	if(familia!=null){
		    	Ingrediente ingrediente=(Ingrediente)session.get(Ingrediente.class,id);
		    	if(ingrediente!=null){
		    		Set<Receta> recetas=new HashSet<Receta>();
		    		ingrediente.setNombre(nombre);
		    		ingrediente.setRefrigeracion(boolRefrigeracion);
		    		ingrediente.setFamilia(familia);
		    		for(int idActual:idRecetas){
		    			Receta receta=(Receta)session.get(Receta.class, idActual);
		    			if(receta!=null){
		    				recetas.add(receta);
		    			}
		    		}
		    		ingrediente.setRecetas(recetas);
		    		tx.commit();
		    	}
	    	}
    	}catch(HibernateException e){
    		System.out.println("No se ha podido realizar la operacion!");
    	}finally{
    		session.close();
    	}
    }
    
    
    public void updateFamiliaIng(int id,String nombre, String descripcion, Set<Integer> idIngredientes){
    	try{
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
	    	FamiliaIng familia=(FamiliaIng)session.get(FamiliaIng.class,id);
	    	if(familia!=null){
	    		Set<Ingrediente> ingredientes=new HashSet<Ingrediente>();
	    		familia.setNombre(nombre);
	    		familia.setDescripcion(descripcion);
	    		for(int idActual:idIngredientes){
	    			Ingrediente ingrediente=(Ingrediente)session.get(Ingrediente.class, idActual);
	    			if(ingrediente!=null){
	    				ingredientes.add(ingrediente);
	    			}
	    		}
	    		familia.setIngredientes(ingredientes);
	    		tx.commit();
	    	}
    	}catch(HibernateException e){
    		System.out.println("No se ha podido realizar la operacion!");
    	}finally{
    		session.close();
    	}
    }
    
    
    public void updateReceta(int id,int idPlato, int idChef, int idComida, String nombre, String elaboracion, int dificultad, int tiempo,
    		Set<Integer> idIngredientes){
    	try{
	    	session=sf.openSession();
	    	tx=session.beginTransaction();
	    	Plato plato=(Plato)session.get(Plato.class,idPlato);
	    	Chef chef=(Chef)session.get(Chef.class, idChef);
	    	Comida comida=(Comida)session.get(Comida.class,idComida);
	    	Receta receta=(Receta)session.get(Receta.class,id);
	    	if(plato!=null && chef!=null && comida!=null && receta!=null){
	    		Set<Ingrediente> ingredientes=new HashSet<Ingrediente>();
	    		receta.setNombre(nombre);
	    		receta.setElaboracion(elaboracion);
	    		receta.setDificultad(dificultad);
	    		receta.setTiempo(tiempo);
	    		receta.setChef(chef);
	    		receta.setComida(comida);
	    		receta.setPlato(plato);
	    		for(int idActual:idIngredientes){
	    			Ingrediente ingrediente=(Ingrediente)session.get(Ingrediente.class, idActual);
	    			if(ingrediente!=null){
	    				ingredientes.add(ingrediente);
	    			}
	    		}
	    		receta.setIngredientes(ingredientes);
	    		tx.commit();
	    	}
    	}catch(HibernateException e){
    		System.out.println("No se ha podido realizar la operacion!");
    	}finally{
    		session.close();
    	}
    }
    
    
    /*++++++++++++++++DELETE ACTIONS+++++++++++++++++++*/
    
    
    
    /*RECETA*/
	public void deleteReceta(int idReceta) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		Receta receta=(Receta) session.get(Receta.class, idReceta);
    		if(receta!=null){
    			session.delete(receta);
    		}
			tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*PLATO*/
	public void deletePlato(int idPlato) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		Plato plato=(Plato) session.get(Plato.class,idPlato);
    		if(plato!=null){
    			session.delete(plato);
    		}
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*COMIDA*/
	public void deleteComida(int idComida) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		Comida comida=(Comida)session.get(Comida.class, idComida);
    		if(comida!=null){
    			session.delete(comida);
    		}
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*INGREDIENTE*/
	public void deleteIngrediente(int idIngrediente) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		Ingrediente ingrediente=(Ingrediente)session.get(Ingrediente.class, idIngrediente);
    		if(ingrediente!=null){
    			session.delete(ingrediente);
    		}
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}
	
	/*FAMILIA INGREDIENTE*/
	public void deleteFamiliaIng(int idFamiliaIng) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		FamiliaIng familia=(FamiliaIng)session.get(FamiliaIng.class, idFamiliaIng);
    		if(familia!=null){
    			session.delete(familia);
    		}
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
	}

	/*CHEF*/
	public void deleteChef(int idChef) {
    	try{
    		session=sf.openSession();
    		tx=session.beginTransaction();
    		Chef  chef=(Chef)session.get(Chef.class,idChef);
    		if(chef!=null){
    			session.delete(chef);
    		}
    		tx.commit();
    	}catch(HibernateException e) {
    		e.printStackTrace();
    	}finally{
    		session.close();
    	}
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
    
    public Comida getComida(int idComida){
        Comida comida=null;
        try{
            session=sf.openSession();
            tx=session.beginTransaction();
            comida=(Comida) session.get(Comida.class, idComida);
            tx.commit();
        }catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return comida;
    }
    
    public Plato getPlato(int idPlato){
        Plato plato=null;
        try{
            session=sf.openSession();
            tx=session.beginTransaction();
            plato=(Plato) session.get(Plato.class, idPlato);
            tx.commit();
        }catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return plato;
    }
    
    public Chef getChef(int idChef){
        Chef chef=null;
        try{
            session=sf.openSession();
            tx=session.beginTransaction();
            chef=(Chef) session.get(Chef.class, idChef);
            tx.commit();
        }catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return chef;
    }
    
}