/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.franciscohansen.avaliacao1_franciscohansen.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Francisco
 */
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private long id;

  public long getId() {
    return id;
  }

  public void setId( long id ) {
    this.id = id;
  }
  
  
  
}
