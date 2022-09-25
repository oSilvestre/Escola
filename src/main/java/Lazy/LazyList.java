package Lazy;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import Model.Entidade;
import inteface.Dao;

public class LazyList <T extends Entidade>extends LazyDataModel<T>{
	
	private static final long serialVersionUID = 1L;
	public T t;
	private Dao<T> dao;

    public LazyList(T t, Dao<T> dao) {
        System.out.println("init");
        this.t = t;
        this.dao = dao;
    }

    @SuppressWarnings("unchecked")
	@Override
    public T getRowData(String rowKey) {
        for (Object customer : this.getWrappedData()) {
            if (((Entidade)customer).getId() == Long.parseLong(rowKey)) {
                return  (T) customer;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(T customer) {
        return String.valueOf(((Entidade) customer).getId());
    }

//    @Override
//    public int count(Map filterBy) {
//        return (int) datasource.stream()
//                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
//                .count();
//    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<T> load(int offset, int pageSize, Map sortBy, Map filterBy) {
    	
    	System.out.println("load");
    	setRowCount(dao.total(t));
    	String sortField = null;
    	int sortOrder = 0;
    	
    	if(sortBy.containsKey("id")) {
    		sortField = ((SortMeta)sortBy.get("id")).getField();
    		sortOrder = ((SortMeta)sortBy.get("id")).getOrder().equals(SortOrder.DESCENDING) ? 0 : 1;
    	}
    	
		if(getRowCount() > 0)
			return dao.buscar(t, offset, pageSize, sortField, sortOrder);
		return null;
    }
}
