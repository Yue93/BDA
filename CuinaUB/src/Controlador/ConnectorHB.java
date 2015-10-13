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

/**
 * Clase ConnectorUB
 * @author SIR
 *
 */
@SuppressWarnings("deprecation")
public class ConnectorHB {	
    
    private static SessionFactory sf = null;
    private static Session session = null;
    private static Transaction tx=null;

    public ConnectorHB(){}
    
    /**
     * Constructor de la clase ConnectorUB.Aqui se instancia un objeto sessionFactory para 
     * poder comunicar con la base de datos a posteriori
     */
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
    
    
    /**
     * Metodo para devolver una sesion
     * @return sesion
     */
    public static Session getSession(){
        Session session = sf.openSession();
        return session;
    }

    
    
    /*+++++++++++++++SAVE ACTIONS+++++++++++++++++*/
    
    
    /*RECETA*/
    /**
     * Metodo para guardar una nueva receta en la base de datos
     * @param receta Un objeto de la clase receta
     * @param idComida id de tipo de comida que pertenece la receta
     * @param idChef id del chef quien posee la receta
     * @param idPlato id del tipo de plato al que pertenece la receta
     * @param idIngredientes id de los ingrediente que se utilizar en la receta
     */
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
    			if(ingrediente!=null && !ingredientes.contains(ingrediente)){
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
    /**
     * Metodo para guardar un nuevo plato en la base de datos
     * @param plato objeto plato que queremos guardar
     */
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
    /**
     * Metodo para guardar una nueva comida en la base de datos
     * @param comida objeto comida al que queremos guardar
     */
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
    /**
     * Metodo para guardar una nueva ingrediente en la base de datos
     * @param ingrediente objeto ingrediente que queremos guardar
     */
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
    /**
     * Metodo para guardar una nueva Familia de ingredientes en la base de datos
     * @param familiaing objeto Familia Ingrediente que queremos guardar
     */
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
    /**
     * Metodo para guardar un nuevo chef en la base de datos
     * @param chef Objeto chef que queremos guardar
     */
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
    /**
     * Metodo para imprimir las recetas de la base de datos
     * @return devolvemos la lista de recetas
     */
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
    /**
     * Metodo para imprimir los tipos de plato guardados en la base de datos
     * @return la lista de los tipos de plato
     */
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
    /**
     * Metodo para imprimir las comidas guardadas en la base de datos
     * @return devolvemos la lista de comidas
     */
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
    /**
     * Metodo para imprimir los chef guardados en la base de datos
     * @return devolvemos la lista de chefs
     */
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
    /**
     * Metodo para imprimir los ingredientes guardados en la base de datos
     * @return devolvemos la lista de ingredientes
     */
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
   /**
    * Metodo para imprimir la lista de familias de ingrediente
    * @return devolvemos la lista de familias de ingrediente
    */
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
    /**
     * Metodo para actualizar las informaciones de una comida
     * @param id id de la comida que queremos actualizar
     * @param nombre nombre de la comida
     * @param descripcion descripcion de la comida
     * @param idRecetas id de las recetas del tipo de comida
     */
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
	    			if(receta!=null && !recetas.contains(receta)){
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
    
    /**
     * Metodo para actualizar las informaciones de un tipo de plato
     * @param id id del tipo de plato 
     * @param nombre nombre del tipo de plato
     * @param descripcion descripcion del tipo de plato
     * @param idRecetas id de las recetas del tipo de plato
     */
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
	    			if(receta!=null && !recetas.contains(receta)){
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
    
    
    /**
     * Metodo para actualizar las informaciones de un chef
     * @param id id del chef al cual queremos modificar su informacion
     * @param nombre nombre del chef
     * @param apellido apellido del chef
     * @param nEstrellas numero de estrellas que tiene el chef
     * @param idRecetas id de las recetas 
     */
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
	    			if(receta!=null && !recetas.contains(receta)){
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
    
    /**
     * Metodo para actualizar las informaciones de un ingrediente
     * @param id id del ingrediente que queremos actualizar
     * @param nombre nombre del ingrediente
     * @param boolRefrigeracion boolean que nos indica si el ingrediente necesita refrigerarse
     * @param familia familia a la que pertenece la ingrediente
     * @param idRecetas id de las recetas que tiene este ingrediente
     */
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
		    			if(receta!=null && !recetas.contains(receta)){
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
    
    /**
     * Metodo para actualizar las informaciones de una familia de ingredientes
     * @param id id de la familia de ingrediente que queremos actualizar
     * @param nombre nombre de la familia 
     * @param descripcion descripcion de la familia
     * @param idIngredientes id de los ingredientes que pertenece a esta familia
     */
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
	    			if(ingrediente!=null && !ingredientes.contains(ingrediente)){
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
    
    /**
     * Metodo para actualizar las informaciones de una receta
     * @param id id de la receta que queremos modificar su informacion
     * @param idPlato id del plato al que pertenece
     * @param idChef id del chef quien tiene la receta 
     * @param idComida id del tipo de comida que pertenece la receta
     * @param nombre nombre de la receta
     * @param elaboracion descripcion de la elaboracion de la receta
     * @param dificultad nivel de dificultad de elaboracion de la receta
     * @param tiempo tiempo de elaboracion
     * @param idIngredientes id de los ingredientes que utiliza en la receta
     */
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
	    			if(ingrediente!=null && !ingredientes.contains(ingrediente)){
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
    /**
     * Metodo para eliminar una receta
     * @param idReceta id de la receta que queremos eliminar
     */
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
	/**
	 * Metodo para eliminar un tipo de plato
	 * @param idPlato id del tipo de plato que queremos eliminar
	 */
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
	/**
	 * Metodo para eliminar una comida
	 * @param idComida id de la comida que queremos eliminar
	 */
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
	/**
	 * Metodo para eliminar un ingrediente
	 * @param idIngrediente id del ingrediente que queremos eliminar
	 */
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
	/**
	 * Metodo para eliminar un familia de ingredientes
	 * @param idFamiliaIng id de la familia de ingredientes que queremos eliminar
	 */
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
	/**
	 * Meotodo para eliminar un chef 
	 * @param idChef id del chef que queremos eliminar
	 */
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
	    
	/**
	 * Metodo para obtener un objeto tipo Familia Ingrediente pasando el id de la familia
	 * @param idFamilia id de la familia de ingrediente
	 * @return el objeto buscado
	 */
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
    
    /**
     * Metodo para obtenenr un objeto comida pasando su id
     * @param idComida id de la comida que queremos obtener 
     * @return objeto tipo de comida buscado
     */
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
    
    /**
     * Metodo para obtener un objeto plato pasando su id
     * @param idPlato id del plato buscado
     * @return un objeto tipo de plato
     */
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
    
    /**
     * Metodo para obtener un objeto chef pasando su id
     * @param idChef id del chef que queremos obtener 
     * @return El objeto chef con el id idChef
     */
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