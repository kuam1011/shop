package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;

import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Dependent
@Log
public class RechnungServiceLogImplementierung implements Serializable, RechnungService {
	private static final long serialVersionUID = -8096383774616786753L;
	
	@Override
	public List<Rechnung> findAllRechnungen() {
		return Mock.findAllRechnungen();
	}

	@Override
	public Rechnung findRechnungById(Long id) {
		return Mock.findRechnungById(id);
	}

	@Override
	public Rechnung createRechnung(Rechnung rechnung) {
		return Mock.createRechnung(rechnung);
	}

	@Override
	public void updateRechnung(Rechnung rechnung) {
		Mock.updateRechnung(rechnung);
	}

	@Override
	public void deleteRechnung(Long liefernantId) {
		Mock.deleteRechnung(liefernantId);
	}
}
