
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Categories;


public class CategoriesBean implements Categories {



	long categoryid;// PK
	java.lang.String categoryName;
	java.lang.String description;


  /** Constructor Buit */
  public CategoriesBean() {
  }

  /** Constructor amb tots els camps  */
  public CategoriesBean(long categoryid , java.lang.String categoryName , java.lang.String description) {
    this.categoryid=categoryid;
    this.categoryName=categoryName;
    this.description=description;
}
  /** Constructor sense valors autoincrementals */
  public CategoriesBean(java.lang.String categoryName , java.lang.String description) {
    this.categoryName=categoryName;
    this.description=description;
}
  /** Constructor dels valors Not Null */
  public CategoriesBean(long categoryid) {
    this.categoryid=categoryid;
}
  public CategoriesBean(Categories __bean) {
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



  // ======================================

  public static CategoriesBean toBean(Categories __bean) {
    if (__bean == null) { return null;}
    CategoriesBean __tmp = new CategoriesBean();
    __tmp.setCategoryid(__bean.getCategoryid());
    __tmp.setCategoryName(__bean.getCategoryName());
    __tmp.setDescription(__bean.getDescription());
		return __tmp;
	}



}
