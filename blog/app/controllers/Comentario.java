package controllers;

import java.util.Date;
import java.util.List;

import models.Negocio.GestorComentario;

import models.OD.ComentarioOD;
import models.OD.UsuarioOD;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import play.libs.XPath;
import play.mvc.Controller;
import play.mvc.Result;


public class Comentario  extends Controller {
	
	public static Result insertarComentario(String token) {
		  System.out.println("Insertando Comentario");
		   
		  	int tokens = (Integer) Integer.parseInt(token);
		    System.out.println(tokens);
		
		  	
		  	String texto = XPath.selectText("//texto", request().body().asXml());
		  	String privacidad = XPath.selectText("//privacidad", request().body().asXml());
		  	String id_u = XPath.selectText("//id_u", request().body().asXml());
		  	String padre = XPath.selectText("//padre", request().body().asXml());
		  	Date fecha = new Date();
		  	ComentarioOD comentario = new ComentarioOD(texto, fecha.toString() , Integer.parseInt(id_u), Integer.parseInt(padre), Integer.parseInt(privacidad),0 ,0);
		  	GestorComentario beta = new GestorComentario();
		  	boolean modifico = beta.insertar(comentario, tokens);
		  	if(modifico == true){
	  		
		  	XStream xstream = new XStream(new DomDriver());
		    String xml = xstream.toXML(comentario);
		    return ok(xml);
		  	}
		  	else {
		  		XStream xstream = new XStream(new DomDriver());
			    String xml = xstream.toXML("No insertar tu comentario ");
		  		return ok(xml);
		  	}
	}
	  
	public static Result insertarComentariotag(String token)
	{  System.out.println("Insertando Comentario ");
	String id_c = XPath.selectText("//id_c", request().body().asXml());
	String etiqueta = XPath.selectText("//etiqueta" ,request().body().asXml());
		
	GestorComentario nuevo = new GestorComentario();
	
	ComentarioOD tagear = new ComentarioOD	();
	tagear.setId_c(Integer.parseInt(id_c));
		boolean tag  =nuevo.insertarTag(tagear, etiqueta , token);
		if(tag ==true)
		{

			return ok(id_c +" " + etiqueta);
		}else return ok(id_c +" " + etiqueta);
	}
	
	
	
	public static Result listart() {
		GestorComentario beta = new GestorComentario();
		List<ComentarioOD> list = beta.listar();
	      
		XStream xstream = new XStream(new DomDriver());
	    String xml = xstream.toXML(list);
  		return ok(xml);
	}
	
	
	public static Result listarcomentarios(String id_c) {
		GestorComentario beta = new GestorComentario();
		ComentarioOD comentario = new ComentarioOD(null,null,Integer.parseInt(id_c),0,0,0,0);
		List<ComentarioOD> list = beta.listarEspesifico(comentario);
	      
		XStream xstream = new XStream(new DomDriver());
	    String xml = xstream.toXML(list);
  		return ok(xml);
	}
	

	public static Result listarcomentariosUsuario(String id_u) {
		GestorComentario beta = new GestorComentario();
		ComentarioOD comentario = new ComentarioOD(null,null,Integer.parseInt(id_u),0,0,0,0);
		List<ComentarioOD> list = beta.listarUsuario(comentario);
	      
		XStream xstream = new XStream(new DomDriver());
	    String xml = xstream.toXML(list);
  		return ok(xml);
	}
	
	
	
	public static Result darLike(String token) {
		
		
		  System.out.println("Dando like o no like a Comentario" + token);
		   int tokens = (Integer) Integer.parseInt(token);
		    System.out.println(tokens);
			  	
		  	String id_c = XPath.selectText("//id_c", request().body().asXml());
		  	String id_u = XPath.selectText("//id_u", request().body().asXml());
		  	String like = XPath.selectText("//like", request().body().asXml());
		 
		  	ComentarioOD comentario = new ComentarioOD(Integer.parseInt(id_c), null, null, Integer.parseInt( id_u),0,0);
		  	UsuarioOD usuario = new UsuarioOD();
		  	usuario.setId_u(Integer.parseInt(id_u));
		  	usuario.setToken(Integer.parseInt(token));
		  	GestorComentario beta = new GestorComentario();
		  	int ellike = Integer.parseInt(like);
		  	boolean lik  = false;
		  	 lik = beta.Hacerlike(usuario, comentario,ellike);
		  	if(lik == true ){
		  		XStream xstream = new XStream(new DomDriver());
			    String xml = xstream.toXML("holaaaaaaaaaaaaaaaaaaaaaaaa");
		  		return ok(xml);
	
		  	}else 
		  		return ok("no se puedo hacer el like");
	}
	
	
	
	 public static Result eliminarComentarios(String token, String id_c, String id_u)
	{ 
		 GestorComentario beta = new GestorComentario();
		 ComentarioOD elcomentario = new ComentarioOD();
		 elcomentario.setId_c(Integer.parseInt(id_c));
		 
		 int eltoken = Integer.parseInt(token);
		 int elusuario = Integer.parseInt(id_u); 
		 beta.eliminar(elcomentario, eltoken, elusuario);
			 
			 return ok("elimine comentarioo");
	 }
	 	
	 
	 
	 
 
	 public static Result listartags(String tags) 
	 {
	 
		GestorComentario beta = new GestorComentario();
		List<ComentarioOD> list = beta.listartags(Integer.parseInt(tags));
	      
		XStream xstream = new XStream(new DomDriver());
	    String xml = xstream.toXML(list);
		return ok(xml);
	}
 
 
 
}

