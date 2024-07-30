
package org.fundaciobit.demogenapp.persistence;
import org.fundaciobit.demogenapp.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Index;


@javax.persistence.Embeddable
@Table(name = "dem_traducciomap" , indexes = { 
        @Index(name="dem_traducciomap_pk_i", columnList = "traducciomapid"),
        @Index(name="dem_traducciomap_idiomaid_fk_i", columnList = "idiomaid")})
@javax.xml.bind.annotation.XmlRootElement
public class TraduccioMapJPA implements TraduccioMap {

  /**  */
    @Column(name="valor",length = 4000)
    java.lang.String valor;



  /** Constructor Buit */
  public TraduccioMapJPA() {
  }

  public TraduccioMapJPA(String _valor_) {
    this.valor = _valor_;
  }

	public java.lang.String getValor() {
		return(valor);
	};
	public void setValor(java.lang.String _valor_) {
		this.valor = _valor_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof TraduccioMap) {
      TraduccioMap __instance = (TraduccioMap)__obj;
      __result = true;
      __result = super.equals(__instance);
    } else {
      __result = false;
    }
    return __result;
  }



}