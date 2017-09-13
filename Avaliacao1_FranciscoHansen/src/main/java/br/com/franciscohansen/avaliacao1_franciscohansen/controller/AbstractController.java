/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.franciscohansen.avaliacao1_franciscohansen.controller;

import br.com.franciscohansen.avaliacao1_franciscohansen.dao.AbstractDao;
import br.com.franciscohansen.avaliacao1_franciscohansen.model.AbstractModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Francisco
 */
@MappedSuperclass
public abstract class AbstractController<T extends AbstractModel, U extends AbstractDao<T>> {

  /**
   *
   */
  @Inject
  protected AbstractDao<T> dao;

  protected abstract T buildObjectFromRequest( HttpServletRequest req );

  protected void save( HttpServletRequest req, T obj ) {
    try {
      dao.save( obj );
      req.setAttribute( "msg", "Registro salvo com sucesso!" );
    }
    catch ( Exception ex ) {
      req.setAttribute( "msg", "Erro ao salvar registro!" );
      Logger.getLogger( AbstractController.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }

  protected void listar( HttpServletRequest req ) {
    try {
      req.setAttribute( "obj", dao.findAll() );
    }
    catch ( Exception ex ) {
      Logger.getLogger( AbstractController.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }

  protected void editar( HttpServletRequest req ) {
    try {
      Long id = Long.parseLong( req.getParameter( "id" ) );
      req.setAttribute( "obj", dao.findById( id ) );
    }
    catch ( Exception ex ) {
      Logger.getLogger( AbstractController.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }

  protected void excluir( HttpServletRequest req ) {
    try {
      Long id = Long.parseLong( req.getParameter( "id" ) );
      dao.remove( id );
      req.setAttribute( "msg", "Registro excluido com sucesso!" );
    }
    catch ( Exception ex ) {
      req.setAttribute( "msg", "Erro ao remover registro." );
      Logger.getLogger( AbstractController.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }

}
