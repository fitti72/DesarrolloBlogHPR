package models.Negocio;

import java.util.ArrayList;
import java.util.List;

import models.DAO.ComentarioDAO;
import models.DAO.GustaDAO;
import models.DAO.TagDAO;
import models.DAO.Tag_ComentarioDAO;
import models.DAO.TokenDAO;
import models.DAO.UsuarioDAO;
import models.DAO.MongoDB.ComentarioMongoDB;
import models.DAO.MongoDB.GustaMongoDB;
import models.DAO.MongoDB.TagMongoDB;
import models.DAO.MongoDB.Tag_ComentarioMongoDB;
import models.DAO.MongoDB.TokenMongoDB;
import models.DAO.MongoDB.UsuarioMongoDB;
import models.OD.ComentarioOD;
import models.OD.GustaOD;
import models.OD.TagOD;
import models.OD.Tag_ComentarioOD;
import models.OD.TokenOD;
import models.OD.UsuarioOD;

public class GestorComentario {

	public GestorComentario(){}
	
		
		
		public boolean insertar(ComentarioOD Comentario,int token){
			UsuarioOD Usuario = new UsuarioOD();
		    TokenDAO t = new TokenMongoDB();
			UsuarioDAO u = new UsuarioMongoDB();
			ComentarioOD aux = new ComentarioOD();
				
					 GestorToken gestort = new GestorToken();
					 TokenOD Token = new TokenOD();
					 Token.setToken(token);
					 Token = t.buscar(Token); 
			
					 
					 if (gestort.validartoken(Token)){
								 ComentarioDAO c = new ComentarioMongoDB();
								 aux.setId_c(Comentario.getPadre());
								 aux = c.buscar(aux);
								 
								 if (Comentario.getPadre()!= 0){
									 
									  if(padreExistePrivasidad(Comentario)){ 
								
										  Usuario.setId_u(aux.getId_u());
										  Usuario = u.buscarID(Usuario);
										  
										  
										   // System.out.println("envio mensaje a:  "+Usuario.getEmail());
										  c.insertar(Comentario);
										  return true;
									  }
									  else{System.out.println("el comentario padre es privado"); return false;}
								 }else {c.insertar(Comentario);return true;}
			  
					 }else{System.out.println("token no valido");return false;}
					 
		}
		
		
		
		
		public boolean padreExistePrivasidad(ComentarioOD Comentario){
			System.out.println(Comentario.getPrivacidad()+"id de comentario en  existe privasidad");
			ComentarioDAO comentario = new ComentarioMongoDB();
			Comentario.setId_c(Comentario.getPadre());
			ComentarioOD result = comentario.buscar(Comentario);
			System.out.println(result);
			
			if (result != null){
				
				if (result.getPrivacidad() == 1){
				return true;	}
				else{System.out.println("Mensaje Privado no acepta respuestas");return false;}
			
				
			}else{System.out.println("Mensaje Padre no existe"+Comentario.getPadre());return false;}
			
		}
	
	
		public boolean eliminar(ComentarioOD Comentario, int token , int id_u){
			
			System.out.println(id_u + " id del usuario que quiere eliminar comentario");
			System.out.println(token + " token enviado para validar por usuario y token");
			
			ComentarioDAO c = new ComentarioMongoDB();
		    UsuarioDAO u = new UsuarioMongoDB();
		    TokenDAO t = new TokenMongoDB();
			UsuarioOD Usuario = new UsuarioOD();
			Usuario.setId_u(id_u);
			Usuario = u.buscarID(Usuario);
			Usuario.setToken(token);
			System.out.println(Usuario.getNombre() + " Nombre del usuario a buscar para elimiar comentario");
			Comentario = c.buscar(Comentario);
			
			if(Usuario!=null){
				
				if(Comentario!=null){
							
					 GestorToken gestort = new GestorToken();
					 TokenOD Token = new TokenOD();
					 System.out.println(Usuario.getId_u() + "Id del Usuario por el cual buscare el token");
					 Token = t.buscarPorUsuario(Usuario); 
					 System.out.println(Token.getToken() + " token buscado por el id de usuario");
					 if(Token.getToken() == token)
					 {
			
					 
					 if (gestort.validartoken(Token)){
						 if((Usuario.getId_u()==Comentario.getPadre())||(Usuario.getId_u()==Comentario.getId_u())){

							 c.eliminar(Comentario);
							 return true;
							 }else{System.out.println("el Usuario no tiene los permisos para borrar este mensaje");return false;}
						 
					 }else{System.out.println("token no valido");return false;}
						 
				}else{   System.out.println("no tenemos ese comentario en sistema");return false;}
	
				}else {System.out.println("token distintos");}
			}else{ System.out.println("El ususario del comenterario ya no existe");}
		
			
			return false;
		}
	
		
		
		public List<ComentarioOD> listar(){
		
			ComentarioDAO c = new ComentarioMongoDB();
			List<ComentarioOD> lista = c.listar();
			return lista;
		    
		}
		
		
		public List<ComentarioOD> listarEspesifico(ComentarioOD Comentario){
			
			ComentarioDAO c = new ComentarioMongoDB();
			List<ComentarioOD> lista = new ArrayList<ComentarioOD>();
			List<ComentarioOD> listaAux = c.listarHijos(Comentario);
				
				
				lista.add(c.buscar(Comentario));
				
				
				for (ComentarioOD result : listaAux) {
					lista.add(result);
				}	
			
		return lista;
		}
		
public List<ComentarioOD> listarUsuario(ComentarioOD Comentario){
			
			ComentarioDAO c = new ComentarioMongoDB();
			List<ComentarioOD> lista = new ArrayList<ComentarioOD>();
			List<ComentarioOD> listaAux = c.listarPorUsuario(Comentario);
				
				
				lista.add(c.buscar(Comentario));
				
				
				for (ComentarioOD result : listaAux) {
					lista.add(result);
				}	
			
		return lista;
		}


		
		
public boolean Hacerlike(UsuarioOD Usuario,ComentarioOD Comentario,int like){
	 System.out.println("hola esto es el token que llega en el usuario de gestorComentario "+Usuario.getToken());
		
	
	
			GustaDAO g = new GustaMongoDB();
	 
			ComentarioDAO c = new ComentarioMongoDB();
			Comentario = c.buscar(Comentario);
			System.out.println(Comentario.getPrivacidad()+"comentario actualizado esta todo");
			
				
			if(Comentario!=null){
				
					 GestorToken gestort = new GestorToken();
					 TokenOD Token = new TokenOD();
					
					 Token.setToken(Usuario.getToken()); 
					 System.out.println("hola esto es el token de gestorComentario "+Token.getFecha());
					 
					 if (gestort.validartoken(Token)){
						 System.out.println("Comentario get padre en gestorComentario = "+Comentario.getPadre());
									 
								 
										  GustaOD Gusta = new GustaOD(Comentario.getId_c(),like,Usuario.getId_u());
										  GustaOD GustaAux = new GustaOD();
										  GustaAux = g.buscar(Gusta);
										  
										  
										  if((Gusta.getId_u()==GustaAux.getId_u())&&(Gusta.getId_c()==GustaAux.getId_c())){
										  c.ActualizaGustar(Comentario, like);							  
										  g.insertar(Gusta);
										  return true;
										  }else {System.out.println("el usuario no puede repetir"); return false;}
			  
					 }else{System.out.println("token no valido");return false;}
					 
		}else{System.out.println("el comentario NO EXISTE");return false;}
}


public boolean insertarTag(ComentarioOD Comentario,String tags,String token){

	ComentarioDAO c = new ComentarioMongoDB();
	Comentario = c.buscar(Comentario);
	Tag_ComentarioDAO tc = new Tag_ComentarioMongoDB();
	
	
	if(Comentario!=null){
		
		if(Comentario.getPadre()==0){
		TagOD nodo = new TagOD();
			
			 nodo.setNombre(tags);
			
			TagDAO t = new TagMongoDB();
			GestorToken gestort = new GestorToken();
			 TokenOD Token = new TokenOD();
			 Token.setToken(Integer.parseInt(token));
			 if (gestort.validartoken(Token)){
			if(t.buscar(nodo)==null){
				System.out.println(nodo.getNombre()+"nombre del tag antes de insertar");
			t.insertar(nodo);
			nodo = t.buscar(nodo);
			Tag_ComentarioOD Tag_c = new Tag_ComentarioOD(Comentario.getId_c(), nodo.getId_t());
			tc.insertar(Tag_c);
							
				
			}else{
				Tag_ComentarioOD Tag_c = new Tag_ComentarioOD(Comentario.getId_c(), nodo.getId_t());
			tc.insertar(Tag_c);}
			 
		
		}else{System.out.println("Token vencido o no valido"); return false;}
		}else{System.out.println("solo se pueden etiquetar comentarios padres"); return false;}
	}else {System.out.println("no esxite el comentario que quieres");return false;}
	return true;
}


public List<ComentarioOD> listartags(int id_t){
	
	ComentarioDAO c = new ComentarioMongoDB();
	System.out.println("Listar tags en gestor comentario");
	List<ComentarioOD> lista = c.listarPortag(id_t);
	return lista;
    
}


}