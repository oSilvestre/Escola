package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import Dao.ProdutoDao;
import Model.Produto;

@FacesConverter("produtoConverter")
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Produto a = new Produto();
		try {
			if(arg2 != null && !arg2.equals("null"))
				a = new ProdutoDao().selecionar(Long.parseLong(arg2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 instanceof Produto )
			return ((Produto) arg2).getId()+"";
		else
			return "";
	}

}
