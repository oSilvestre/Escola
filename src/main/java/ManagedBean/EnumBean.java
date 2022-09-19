package ManagedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import Enumered.TurnoEnum;

@ManagedBean
@SessionScoped
public class EnumBean {

	public TurnoEnum[] getTurnoEnum() {
		return TurnoEnum.values();
	}
}
