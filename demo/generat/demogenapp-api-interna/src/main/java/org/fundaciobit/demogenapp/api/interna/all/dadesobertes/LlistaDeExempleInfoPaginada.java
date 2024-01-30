package org.fundaciobit.demogenapp.api.interna.all.dadesobertes;

import java.util.List;

import org.fundaciobit.pluginsib.utils.rest.OpenDataPagination;


public class LlistaDeExempleInfoPaginada extends OpenDataPagination<ExempleInfo> {

    public LlistaDeExempleInfoPaginada() {
        super();
    }

    public LlistaDeExempleInfoPaginada(List<ExempleInfo> data, int page, int pagesize, int totalpages, int totalcount,
            String nextUrl, String dateDownload) {
        super(data, page, pagesize, totalpages, totalcount, nextUrl, dateDownload);
    }

}
