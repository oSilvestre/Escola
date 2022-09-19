package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import Dao.AlunoDao;
import Model.Aluno;

@FacesConverter("alunoConverter")
public class AlunoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Aluno a = new Aluno();
		try {
			a = new AlunoDao().selecionar(Long.parseLong(arg2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 instanceof Aluno )
			return ((Aluno) arg2).getId().toString();
		else
			return "";
	}

}
