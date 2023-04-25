package org.fundaciobit.genapp.common.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;

/**
 * 
 * @author anadal
 * 
 */
public abstract class BaseFilterForm extends CommonBaseForm {

    public static final int ACTIONS_RENDERER_NONE = 0;

    public static final int ACTIONS_RENDERER_SIMPLE_ICON_LIST = 1;

    public static final int ACTIONS_RENDERER_DROPDOWN_BUTTON = 2;

    protected final Logger log = Logger.getLogger(getClass());

    // Activar FILTER BY?
    private boolean visibleFilterBy = false;

    // Activar GROUP BY?
    private boolean visibleGroupBy;

    private String groupBy;

    private String groupValue;

    // ORDER BY
    private boolean visibleOrderBy = true;

    private String orderBy = null;

    private boolean orderAsc = true;

    private int[] allItemsPerPage = new int[] { 5, 10, 20, 30, 40, 50, 75, 100, -1 };

    private Integer itemsPerPage = allItemsPerPage[1];

    private List<Field<?>> groupByFields;

    private List<Field<?>> filterByFields;

    /**
     * Indica si mostrar el boto d'afegir adalt del llistat
     */
    private boolean addButtonVisible = true;

    /**
     * Indica si mostrar el boto d'edicio en cada element del llistat
     */
    private boolean editButtonVisible = true;

    /**
     * Pàgina de l'actual llistat
     */
    private int page = 1;

    /**
     * Mostra o oculta el boto de borrar de forma múltiple. El boto serà
     * visible a més el valors deleteButtonVisible
     * i visibleMultipleSelection valen true  
     */
    private boolean deleteSelectedButtonVisible = true;

    /**
     * Elements de la selecció multiple elegits 
     */
    private String[] selectedItems;

    /**
     * Mostrar selecció multiple d'elements
     */
    private boolean visibleMultipleSelection = true;

    /**
     * Conté la llista de botons adicionals que es poden mostrar en un form o en
     * cada element de la llista
     */
    private Map<Object, ArrayList<AdditionalButton>> additionalButtonsByPK = new HashMap<Object, ArrayList<AdditionalButton>>();

    private List<AdditionalButton> additionalButtonsForEachItem = new ArrayList<AdditionalButton>();

    private boolean visibleExportList = true;

    private int actionsRenderer = ACTIONS_RENDERER_SIMPLE_ICON_LIST;

    private Map<Object, Object> additionalInfoForActionsRendererByPK = new HashMap<Object, Object>();

    /**
     * 
     * @param toClone
     */
    public BaseFilterForm(BaseFilterForm toClone) {

        super(toClone);

        this.visibleFilterBy = toClone.visibleFilterBy;
        this.orderBy = toClone.orderBy;
        this.orderAsc = toClone.orderAsc;
        this.visibleGroupBy = toClone.visibleGroupBy;
        this.visibleOrderBy = toClone.visibleOrderBy;
        this.groupBy = toClone.groupBy;
        this.groupValue = toClone.groupValue;
        this.itemsPerPage = toClone.itemsPerPage;
        this.allItemsPerPage = toClone.allItemsPerPage;
        this.groupByFields = toClone.groupByFields;
        this.filterByFields = toClone.filterByFields;
        this.addButtonVisible = toClone.addButtonVisible;
        this.editButtonVisible = toClone.editButtonVisible;
        this.page = toClone.page;
        this.selectedItems = toClone.selectedItems;
        this.visibleMultipleSelection = toClone.visibleMultipleSelection;
        this.additionalButtonsByPK = toClone.additionalButtonsByPK;
        this.additionalButtonsForEachItem = toClone.additionalButtonsForEachItem;
        this.visibleExportList = toClone.visibleExportList;
        this.actionsRenderer = toClone.actionsRenderer;
        this.additionalInfoForActionsRendererByPK = toClone.additionalInfoForActionsRendererByPK;
    }

    public BaseFilterForm() {
        super();
    }

    public int getActionsRenderer() {
        return actionsRenderer;
    }

    public void setActionsRenderer(int actionsRenderer) {
        this.actionsRenderer = actionsRenderer;
    }

    public Map<Object, Object> getAdditionalInfoForActionsRendererByPK() {
        return additionalInfoForActionsRendererByPK;
    }

    public void setAdditionalInfoForActionsRendererByPK(Map<Object, Object> additionalInfoForActionsRendererByPK) {
        this.additionalInfoForActionsRendererByPK = additionalInfoForActionsRendererByPK;
    }

    public void addAdditionalInfoForActionsRendererByPK(Object pk, Object info) {

        if (info != null) {
            if (this.additionalInfoForActionsRendererByPK.get(pk) != null) {
                log.warn("Esta intentant afegir un camp info de ActionsRenderer " + " a una PK que ja el conté !!!!",
                        new Exception());
            }
            this.additionalInfoForActionsRendererByPK.put(pk, info);
        }

    }

    public boolean isVisibleOrderBy() {
        return visibleOrderBy;
    }

    public void setVisibleOrderBy(boolean visibleOrderBy) {
        this.visibleOrderBy = visibleOrderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isOrderAsc() {
        return orderAsc;
    }

    public void setOrderAsc(boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

    public boolean isVisibleFilterBy() {
        return visibleFilterBy;
    }

    public void setVisibleFilterBy(boolean visibleFilterBy) {
        this.visibleFilterBy = visibleFilterBy;
    }

    public boolean isVisibleGroupBy() {
        return visibleGroupBy;
    }

    public void setVisibleGroupBy(boolean visibleGroupBy) {
        this.visibleGroupBy = visibleGroupBy;
    }

    public String getGroupBy() {
        if (groupBy == null || groupBy.trim().length() == 0) {
            return null;
        }
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int[] getAllItemsPerPage() {
        return allItemsPerPage;
    }

    public void setAllItemsPerPage(int[] allItemsPerPage) {
        this.allItemsPerPage = allItemsPerPage;
    }

    public List<Field<?>> getGroupByFields() {
        return groupByFields;
    }

    public void setGroupByFields(List<Field<?>> groupByFields) {
        this.groupByFields = groupByFields;
    }

    public void addGroupByField(Field<?> field) {
        if (field == null) {
            return;
        }
        if (this.groupByFields == null) {
            this.groupByFields = new ArrayList<Field<?>>();
        }
        this.groupByFields.add(field);
    }

    public boolean isGroupByField(Field<?> field) {
        return this.groupByFields.contains(field);
    }

    public boolean isFilterByField(Field<?> field) {
        return this.filterByFields.contains(field);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String get(Field<?> field) {
        return getTableModelName() + "." + field.javaName;
    }

    public boolean isEditButtonVisible() {
        return editButtonVisible;
    }

    public void setEditButtonVisible(boolean editButtonVisible) {
        this.editButtonVisible = editButtonVisible;
    }

    public boolean isAddButtonVisible() {
        return addButtonVisible;
    }

    public void setAddButtonVisible(boolean addButtonVisible) {
        this.addButtonVisible = addButtonVisible;
    }

    public String[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    public boolean isVisibleMultipleSelection() {
        return visibleMultipleSelection;
    }

    public void setVisibleMultipleSelection(boolean visibleMultipleSelection) {
        this.visibleMultipleSelection = visibleMultipleSelection;
    }

    public boolean isDeleteSelectedButtonVisible() {
        return deleteSelectedButtonVisible;
    }

    public void setDeleteSelectedButtonVisible(boolean deleteSelectedButtonVisible) {
        this.deleteSelectedButtonVisible = deleteSelectedButtonVisible;
    }

    public List<Field<?>> getFilterByFields() {
        return filterByFields;
    }

    public void setFilterByFields(List<Field<?>> filterByFields) {
        this.filterByFields = filterByFields;
    }

    public void addFilterByField(Field<?> field) {
        if (field == null) {
            return;
        }
        if (this.filterByFields == null) {
            this.filterByFields = new ArrayList<Field<?>>();
        }
        this.filterByFields.add(field);
    }

    public Map<Object, ArrayList<AdditionalButton>> getAdditionalButtonsByPK() {
        return additionalButtonsByPK;
    }

    public void setAdditionalButtonsByPK(Map<Object, ArrayList<AdditionalButton>> additionalButtonsByPK) {
        this.additionalButtonsByPK = additionalButtonsByPK;
    }

    public void addAdditionalButtonByPK(Object pk, AdditionalButton additionalButton) {

        if (additionalButton != null && pk != null) {
            ArrayList<AdditionalButton> list = this.additionalButtonsByPK.get(pk);
            if (list == null) {
                list = new ArrayList<AdditionalButton>();
                this.additionalButtonsByPK.put(pk, list);
            } else {
                if (list.contains(additionalButton)) {
                    log.warn("Esta intentant afegir un camp AdditionalButton" + " a una PK que ja el conté !!!!",
                            new Exception());
                }
            }
            list.add(additionalButton);
        }

    }

    public boolean isVisibleExportList() {
        return visibleExportList;
    }

    public void setVisibleExportList(boolean visibleExportList) {
        this.visibleExportList = visibleExportList;
    }

    public void addAdditionalButtonForEachItem(AdditionalButton additionalButton) {

        if (additionalButton != null) {
            if (this.additionalButtonsForEachItem.contains(additionalButton)) {
                log.warn("Esta intentant afegir un camp AdditionalButtonsForEachItem"
                        + " a una llista que ja el conté !!!!", new Exception());
            }
            this.additionalButtonsForEachItem.add(additionalButton);
        }
    }

    public List<AdditionalButton> getAdditionalButtonsForEachItem() {
        return additionalButtonsForEachItem;
    }

    public void setAdditionalButtonsForEachItem(List<AdditionalButton> additionalButtonsForEachItem) {
        this.additionalButtonsForEachItem = additionalButtonsForEachItem;
    }

    // ======= Abstract Methods

    public abstract String getTableModelName();

    public abstract OrderBy[] getDefaultOrderBy();

    public abstract List<Field<?>> getDefaultGroupByFields();

    public abstract List<Field<?>> getDefaultFilterByFields();

}
