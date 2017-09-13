/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.franciscohansen.avaliacao1_franciscohansen.dao;

import br.com.franciscohansen.avaliacao1_franciscohansen.model.AbstractModel;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Francisco
 */
public abstract class AbstractDao<T extends AbstractModel> implements Serializable{

  protected Class<T> persistedClass = getClazz();

  @Inject
  protected EntityManager em;

  public AbstractDao() {
  }

  ;
    
    public AbstractDao( Class<T> persistedClass, EntityManager em ) {
    this.persistedClass = persistedClass;
    this.em = em;
  }

  public void save( T entity ) throws Exception {
    em.getTransaction().begin();
    try {
      if ( entity.getId() == 0 ) {
        em.persist( entity );
      }
      else {
        em.merge( entity );
      }
    }
    catch ( Exception ex ) {
      em.getTransaction().rollback();
      throw new Exception( "Erro ao salvar!", ex );
    }
  }

  public void insert( T entity ) throws Exception {
    em.getTransaction().begin();
    try {
      em.persist( entity );
      em.getTransaction().commit();
    }
    catch ( Exception ex ) {
      em.getTransaction().rollback();
      throw new Exception( "Erro ao inserir!", ex );
    }
  }

  public void update( T entity ) throws Exception {
    em.getTransaction().begin();
    try {
      em.merge( entity );
      em.getTransaction().commit();
    }
    catch ( Exception ex ) {
      em.getTransaction().rollback();
      throw new Exception( "Erro ao atualizar!", ex );
    }
  }

  public void remove( long id ) throws Exception {
    em.getTransaction().begin();
    try {
      em.remove( em.getReference( persistedClass, id ) );
      em.getTransaction().commit();
    }
    catch ( Exception ex ) {
      em.getTransaction().rollback();
      throw new Exception( "Erro ao excluir!", ex );
    }
  }

  public T findById( long id ) throws Exception {
    T t;
    try {
      t = em.find( persistedClass, id );
    }
    catch ( Exception ex ) {
      throw new Exception( "Erro ao buscar!", ex );
    }

    return t;
  }

  @SuppressWarnings( "unchecked" )
  public List<T> findAll() throws Exception {
    List<T> tList;
    try {
      tList = em.createQuery( "from " + persistedClass.getName()
              + " Order By id" ).getResultList();
    }
    catch ( Exception ex ) {
      throw new Exception( "Erro ao buscar!", ex );
    }
    return tList;
  }

  @SuppressWarnings( { "unchecked" } )
  private Class<T> getClazz() {
    Class<?> clazz = this.getClass();

    while ( !clazz.getSuperclass().equals( AbstractDao.class ) ) {
      clazz = clazz.getSuperclass();
    }

    ParameterizedType tipoGenerico = ( ParameterizedType ) clazz.getGenericSuperclass();
    return ( Class<T> ) tipoGenerico.getActualTypeArguments()[ 0 ];
  }

}
