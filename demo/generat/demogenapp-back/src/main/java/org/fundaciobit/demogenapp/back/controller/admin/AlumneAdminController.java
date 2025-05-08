package org.fundaciobit.demogenapp.back.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fundaciobit.demogenapp.back.controller.webdb.AlumneController;
import org.fundaciobit.demogenapp.back.form.webdb.AlumneFilterForm;
import org.fundaciobit.demogenapp.back.form.webdb.AlumneForm;

import org.fundaciobit.demogenapp.back.utils.Tab;
import org.fundaciobit.demogenapp.model.entity.Alumne;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.CustomField;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.GroupByItem;
import org.fundaciobit.genapp.common.query.GroupByValueItem;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@MenuOption(labelCode = "alumne.alumne.plural", order = 25, group = Tab.MENU_ADMIN)
@Controller
@RequestMapping(value = "/admin/alumne")
@SessionAttributes(types = { AlumneForm.class, AlumneFilterForm.class })
@Tile(name = "alumneListAdmin", type = TileType.WEBDB_LIST, extendsTile = Tab.MENU_ADMIN)
@Tile(name = "alumneFormAdmin", type = TileType.WEBDB_FORM, extendsTile = Tab.MENU_ADMIN)
public class AlumneAdminController extends AlumneController {

    public static final CustomField VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO = new CustomField(
            "Despertador_Esta_Definit_O_No");

    @Override
    public AlumneFilterForm getAlumneFilterForm(Integer pagina, ModelAndView mav, HttpServletRequest request)
            throws I18NException {

        AlumneFilterForm form = super.getAlumneFilterForm(pagina, mav, request);

        if (form.isNou()) {

            form.setGroupByFields(form.getDefaultFilterByFields());

            form.getGroupByFields().add(VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO);

            
        }

        return form;

    }

    @Override
    public Map<Field<?>, GroupByItem> fillReferencesForList(AlumneFilterForm filterForm, HttpServletRequest request,
            ModelAndView mav, List<Alumne> list, List<GroupByItem> groupItems) throws I18NException {

        Map<Field<?>, GroupByItem> groupByItemsMap = super.fillReferencesForList(filterForm, request, mav, list,
                groupItems);

        {
            boolean selectedField = VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO.getJavaName().equals(filterForm.getGroupBy());
            
            if (selectedField) {
                filterForm.setVisibleGroupBy(true);
            }

            List<GroupByValueItem> values = new ArrayList<GroupByValueItem>();

            long countDataNull = this.alumneEjb.count(DESPERTADOR.isNull());
            boolean selectedFalse = selectedField && "false".equals(filterForm.getGroupValue());
            values.add(new GroupByValueItem(VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO, "=No Tenen Desperatador", "false", selectedFalse,
                    countDataNull));

            long countDataNotNull = this.alumneEjb.count(DESPERTADOR.isNotNull());
            boolean selectedTrue = selectedField && "true".equals(filterForm.getGroupValue());
            values.add(new GroupByValueItem(VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO, "=Si Tenen Despertador", "true", selectedTrue,
                    countDataNotNull));
            String label = "=Tenen Despertador Definit";
            
            String value = VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO.getJavaName();
            GroupByItem items = new GroupByItem(VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO, label, value, selectedField, values);
            groupItems.add(items);
            groupByItemsMap.put(VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO, items);

        }

        return groupByItemsMap;

    }

    @Override
    public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {

        AlumneFilterForm filterForm = (AlumneFilterForm) request.getSession()
                .getAttribute(getSessionAttributeFilterForm());

        log.info("Group by Field ==> " + filterForm.getGroupBy());
        log.info("Group Value ==> " + filterForm.getGroupValue());

        if (VIRTUAL_FIELD_DESPERATDOR_ESTA_DEFINIT_O_NO.getJavaName().equals(filterForm.getGroupBy())) {

            if ("true".equals(filterForm.getGroupValue())) {
                return DESPERTADOR.isNotNull();
            } else if ("false".equals(filterForm.getGroupValue())) {
                return DESPERTADOR.isNull();
            } else {
                log.warn("Valor per filterBY no reconegut[" + filterForm.getGroupBy() + "]: "
                        + filterForm.getGroupValue());
            }
        }

        return null;
    }

}