package cl.zpricing.avant.web.form;

import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Grupo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.MascaraAreaGrupo;

public class GruposForm {
	private MascaraAreaGrupo[][] mascarasAreasGrupos;
	private List<Grupo> grupos;
	private Mascara mascara;
	private Complejo complejo;

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Mascara getMascara() {
		return mascara;
	}

	public void setMascara(Mascara mascara) {
		this.mascara = mascara;
	}

	public Complejo getComplejo() {
		return complejo;
	}

	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}

	public MascaraAreaGrupo[][] getMascarasAreasGrupos() {
		return mascarasAreasGrupos;
	}

	public void setMascarasAreasGrupos(MascaraAreaGrupo[][] mascarasAreasGrupos) {
		this.mascarasAreasGrupos = mascarasAreasGrupos;
	}
}
