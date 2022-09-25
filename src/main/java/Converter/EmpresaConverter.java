package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import Dao.EmpresaDao;
import Model.Empresa;

@FacesConverter("empresaConverter")
public class EmpresaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Empresa a = new Empresa();
		try {
			a = new EmpresaDao().selecionar(Long.parseLong(arg2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 instanceof Empresa )
			return ((Empresa) arg2).getId()+"";
		else
			return "";
	}

}
