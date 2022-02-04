
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "gas_categories" )
@SequenceGenerator(name="CATEGORIES_SEQ", sequenceName="gas_categories_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class CategoriesJPA implements Categories {



private static final long serialVersionUID = 685245439L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CATEGORIES_SEQ")
    @Index(name="gas_categories_pk_i")
    @Column(name="categoryid",nullable = false,length = 19)
    long categoryid;

    @Column(name="categoryname",length = 255)
    java.lang.String categoryName;

    @Column(name="description",length = 255)
    java.lang.String description;



  /** Constructor Buit */
  public CategoriesJPA() {
  }

  /** Constructor amb tots els camps  */
  public CategoriesJPA(long categoryid , java.lang.String categoryName , java.lang.String description) {
    this.categoryid=categoryid;
    this.categoryName=categoryName;
    this.description=description;
}
  /** Constructor sense valors autoincrementals */
  public CategoriesJPA(java.lang.String categoryName , java.lang.String description) {
    this.categoryName=categoryName;
    this.description=description;
}
  /** Constructor dels valors Not Null */
  public CategoriesJPA(long categoryid) {
    this.categoryid=categoryid;
}
  public CategoriesJPA(Categories __bean) {
    this.setCategoryid(__bean.getCategoryid());
    this.setCategoryName(__bean.getCategoryName());
    this.setDescription(__bean.getDescription());
	}

	public long getCategoryid() {
		return(categoryid);
	};
	public void setCategoryid(long _categoryid_) {
		this.categoryid = _categoryid_;
	};

	public java.lang.String getCategoryName() {
		return(categoryName);
	};
	public void setCategoryName(java.lang.String _categoryName_) {
		this.categoryName = _categoryName_;
	};

	public java.lang.String getDescription() {
		return(description);
	};
	public void setDescription(java.lang.String _description_) {
		this.description = _description_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Categories) {
      Categories __instance = (Categories)__obj;
      __result = true;
      __result = __result && (this.getCategoryid() == __instance.getCategoryid()) ;
    } else {
      __result = false;
    }
    return __result;
  }


 // ---------------  STATIC METHODS ------------------
  public static CategoriesJPA toJPA(Categories __bean) {
    if (__bean == null) { return null;}
    CategoriesJPA __tmp = new CategoriesJPA();
    __tmp.setCategoryid(__bean.getCategoryid());
    __tmp.setCategoryName(__bean.getCategoryName());
    __tmp.setDescription(__bean.getDescription());
		return __tmp;
	}


  public static CategoriesJPA copyJPA(CategoriesJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<CategoriesJPA> copyJPA(java.util.Set<CategoriesJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<CategoriesJPA> __tmpSet = (java.util.Set<CategoriesJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<CategoriesJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (CategoriesJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static CategoriesJPA copyJPA(CategoriesJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    CategoriesJPA __tmp = (CategoriesJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
