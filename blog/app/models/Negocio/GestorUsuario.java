package models.Negocio;

import java.awt.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import models.DAO.UsuarioDAO;
import models.DAO.MongoDB.TokenMongoDB;
import models.DAO.MongoDB.UsuarioMongoDB;
import models.OD.TokenOD;
import models.OD.UsuarioOD;

public class GestorUsuario {
	
	public GestorUsuario(){}
	
//funciones basicas	
	
	public boolean insertar(UsuarioOD Usuario){
  
		  if (validarNick(Usuario)){
			  System.out.print(Usuario.getNick());
			  UsuarioDAO Persona = new UsuarioMongoDB();
			  Persona.insertar(Usuario);
			  return true;
			  
		  }else {
			  System.out.print("validar nick dijo que no");return false;}
		  
		
	}


	
	public UsuarioOD usuarioespecifico (UsuarioOD Usuario){

		UsuarioDAO usuario = new UsuarioMongoDB();
		UsuarioOD result = usuario.buscar(Usuario);

		if (result != null){
		return result;

		}else{
		return null;
		}

		}
	
	
	
	
	public boolean eliminar(UsuarioOD usuario){
		boolean estado = false;
		UsuarioDAO c = new UsuarioMongoDB();
	    c.eliminar(usuario);
	    estado = true;
	    return estado;
}


public boolean modificar(UsuarioOD Usuario){
	
	 GestorToken gestort = new GestorToken();
	 TokenOD Token = new TokenOD();
	 Token.setToken(Usuario.getToken());
	 if (gestort.validartoken(Token)){
	UsuarioDAO usuario = new UsuarioMongoDB();
	usuario.modificar(Usuario);	//entonces primero busco y despues mando los dos //id_u debe ser el mismo
	return true;
	 }else {return false;}
	 
}
	
	
	//funciones de validacion
	
	public boolean validarNick(UsuarioOD Usuario){
	
		UsuarioDAO usuario = new UsuarioMongoDB();
		UsuarioOD result = usuario.buscar(Usuario);
		
		if (result == null){
			return true;	
			
		}else{
			return false;
		}
	
	}
	

	public boolean validarPass(UsuarioOD Usuario){
		
		UsuarioDAO usuario = new UsuarioMongoDB();
		UsuarioOD result = usuario.buscar(Usuario);
	
		
		if ((result != null)&&(result.getClave().equals(Usuario.getClave()))){
			return true;	
			
		}else{
			return false;
		}
	
	}


	
	public UsuarioOD Login(UsuarioOD Usuario,String IP){

		  if (!validarNick(Usuario)){
			  
			  if (validarPass(Usuario)){
				 
				 
				  
				  
				  
				 GestorToken gestort = new GestorToken();
				 UsuarioDAO usuario = new UsuarioMongoDB();
				 UsuarioOD result = usuario.buscar(Usuario);
				 TokenOD Token = new TokenOD();
				  System.err.println("este es el ip que llega "+IP);
				 Token.setIp(IP);
				  System.err.println("este es el ip que manda a encontara "+Token.getIp());
				  Token = gestort.BuscarIP(Token);
				// System.err.println(Token.getFecha());
				  
			if(!(Token!=null)||(!gestort.validartoken(Token))){
				 
				
		
				 TokenOD token  = gestort.insertar(result,IP);
				 Usuario = result;
				 Usuario.setToken(token.getToken());
				  
				  return Usuario;
				  
				 }else {System.out.print("No se puede Iniciar Sesion desde la misma pc"); return null;}
			  }	else{System.out.print("validar pass dijo que no"); return null;}
			  
			  
		  }else {
			  System.out.print("validar nick dijo que no");  return null;}
		  
		
	}

	public UsuarioOD Buscar(UsuarioOD Usuario){
		 UsuarioDAO usuario = new UsuarioMongoDB();
		 UsuarioOD result = usuario.buscarID(Usuario);
		 Usuario = result;
		 return result;
	}
	
}
	
	
	
	
	
