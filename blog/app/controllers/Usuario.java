package controllers;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Random;


import models.DAO.TokenDAO;
import models.DAO.UsuarioDAO;
import models.DAO.MongoDB.TokenMongoDB;
import models.DAO.MongoDB.UsuarioMongoDB;
import models.Negocio.GestorComentario;
import models.Negocio.GestorUsuario;
import models.OD.ComentarioOD;
import models.OD.TokenOD;
import models.OD.UsuarioOD;
import play.libs.XPath;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Usuario  extends Controller {

	
	
	
	public static Result index(){

		return ok("Proyecto de servicios webs");

	}
	
	
	
	
	
	public static Result listar()
	  {
		  
	      UsuarioDAO Persona = new UsuarioMongoDB();
	      
	      
	      
	      List<UsuarioOD> list = Persona.listar();
	      
	  
	    XStream xstream = new XStream(new DomDriver());
	    String xml = xstream.toXML(list);
	   
	    return  ok(xml);

	  }
	  

	  
	  @BodyParser.Of(BodyParser.Xml.class)
	  public static Result insertar() {
		
		  
		  System.out.println("Insertando Usuario");  
		  
	  	String nombre = XPath.selectText("//nombre", request().body().asXml());
	  	String nombres = XPath.selectText("//nombres", request().body().asXml());
	  	String apellido = XPath.selectText("//apellido", request().body().asXml());
	  	String apellidos = XPath.selectText("//apellidos", request().body().asXml());
	  	String email = XPath.selectText("//email", request().body().asXml());
	  	String fecha = XPath.selectText("//fecha", request().body().asXml());
	  	String nick = XPath.selectText("//nick", request().body().asXml());
	  	String pais = XPath.selectText("//pais", request().body().asXml());
	  	String biografia = XPath.selectText("//biografia", request().body().asXml());
	  	String sexo = XPath.selectText("//sexo", request().body().asXml());
	  	String clave = XPath.selectText("//clave", request().body().asXml());
	  	
	  	if(nick == null) {
	    	return badRequest("Missing parameter [nombre]");
	  	} else {
	  		boolean existe = false;
	  		UsuarioOD usuario = new UsuarioOD(nombre, nombres, apellido, apellidos, email, nick, pais, biografia, sexo, fecha, clave);
	  		GestorUsuario nuevo =  new GestorUsuario();
	  		existe = nuevo.insertar(usuario);
	  		if (existe == true){
		  		XStream xstream = new XStream(new DomDriver());
		  		xstream.alias("usuario", UsuarioOD.class);
			    String xml = xstream.toXML(usuario);
			    return ok(xml);
	  		}
	  		else 
	  			return ok("Nickname ya existente");
	  	}
	  }
	  
	  
  public static Result insertarfoto(String id_u) {
	   int id_usuario = Integer.parseInt(id_u);
		
		  
		  System.out.println("Insertando Usuario foto ");  
		  
	  	
	  	if(id_u == null) {
	    	return badRequest("Missing parameter [nombre]");
	  	} else {
	  		
	  		UsuarioOD usuario = new UsuarioOD();
	  		usuario.setId_u(id_usuario);
	  		GestorUsuario nuevo =  new GestorUsuario();
	  		usuario = nuevo.Buscar(usuario);
	  		
		  		XStream xstream = new XStream(new DomDriver());
			    String xml = xstream.toXML(usuario);
			    return ok(xml);
	  	}
	  }
  
  
	  
	  public static Result eliminarUsuario(String nick) {
		  System.out.println("Eliminando Usuario");
		  GestorUsuario beta =new GestorUsuario();
		  UsuarioOD alfa = new UsuarioOD(null, null, null, null, null, nick, null, null, null, null, null);
		 
		  beta.eliminar(alfa);
	  	
	    	return ok("Usuario Eliminado");
	  	
	  }
	  
	  
	  
	  @BodyParser.Of(BodyParser.Xml.class)
	  public static Result modificarUsuario(String token) {
		  System.out.println("Modificando Usuario");
		   
		  	int tokens = (Integer) Integer.parseInt(token);
		    System.out.println(tokens);
		  	String id_u = XPath.selectText("//id_u", request().body().asXml());
		  	String nombre = XPath.selectText("//nombre", request().body().asXml());
		  	String nombres = XPath.selectText("//nombres", request().body().asXml());
		  	String apellido = XPath.selectText("//apellido", request().body().asXml());
		  	String apellidos = XPath.selectText("//apellidos", request().body().asXml());
		  	String email = XPath.selectText("//email", request().body().asXml());
		  	String fecha = XPath.selectText("//fecha", request().body().asXml());
		  	String nick = XPath.selectText("//nick", request().body().asXml());
		  	String pais = XPath.selectText("//pais", request().body().asXml());
		  	String biografia = XPath.selectText("//biografia", request().body().asXml());
		  	String sexo = XPath.selectText("//sexo", request().body().asXml());
		  	String clave = XPath.selectText("//clave", request().body().asXml());
	  	
		  	UsuarioOD usuario = new UsuarioOD(Integer.parseInt(id_u), nombre, nombres, apellido, apellidos, email, nick, pais, biografia, sexo, fecha, clave,tokens);
		  	GestorUsuario beta = new GestorUsuario();
		  	boolean modifico = beta.modificar(usuario);
		  	if(modifico == true){
	  		
		  	XStream xstream = new XStream(new DomDriver());
		    String xml = xstream.toXML(usuario);
		    return ok(xml);
		  	}
		  	else {
		  		XStream xstream = new XStream(new DomDriver());
			    String xml = xstream.toXML("No se pudo modificar ");
		  		return ok(xml);
		  	}
	  
	  }
	  
	  
	  
	  
	  
	  @BodyParser.Of(BodyParser.Xml.class)
	  public static Result validarUsuario() {
		  
		  
		  UsuarioOD validarusuario= null;
		  System.out.println("Validando Usuario");
		  	String nick = XPath.selectText("//nick", request().body().asXml());
		  	String clave = XPath.selectText("//clave", request().body().asXml());
		  	UsuarioOD usuario = new UsuarioOD(nick,clave);
		  	GestorUsuario validar = new GestorUsuario();
		  	

			  String IP = null;
			  
			try {
				IP = java.net.InetAddress.getLocalHost().getHostAddress();
				
				
				validarusuario = validar.Login(usuario,IP);
			  	if(validarusuario != null){
			  		
			  		XStream xstream = new XStream(new DomDriver());		
			  		String xml = xstream.toXML(validarusuario);
			  		xstream.alias("usuario", UsuarioOD.class);
			  		return ok(xml);
			  	}
			  
			
			
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ok("Usuario no registrado ");
			  
		 	
	  }
		  	
	  

	  public static Result especifico(String nick)
	  {
		  
		  UsuarioOD erestu = null;
		  System.out.println("Buscando Usuario");
		  GestorUsuario beta =new GestorUsuario();
		  UsuarioOD alfa = new UsuarioOD(null, null, null, null, null, nick, null, null, null, null, null);
		  erestu = beta.usuarioespecifico(alfa);
		 if (erestu != null)
		 {
		    XStream xstream = new XStream(new DomDriver());
		    String xml = xstream.toXML(erestu);
		    return  ok(xml);}
		 else

			 return  ok("Usuario no encontrado"); 
	  }
	  
	  
	  
}
