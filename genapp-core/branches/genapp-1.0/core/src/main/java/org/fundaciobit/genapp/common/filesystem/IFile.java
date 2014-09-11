package org.fundaciobit.genapp.common.filesystem;

import org.fundaciobit.genapp.common.IGenAppEntity;

/**
 * 
 * @author anadal
 *
 */
public interface IFile extends IGenAppEntity {
  /*
  public long getFitxerID();

  public java.lang.String getNom();

  public long getTamany();

  public java.lang.String getMime();
  */
  
  public long getFitxerID();
  public void setFitxerID(long _fitxerID_);

  public java.lang.String getNom();
  public void setNom(java.lang.String _nom_);

  public java.lang.String getDescripcio();
  public void setDescripcio(java.lang.String _descripcio_);

  public long getTamany();
  public void setTamany(long _tamany_);

  public java.lang.String getMime();
  public void setMime(java.lang.String _mime_);
  
}
