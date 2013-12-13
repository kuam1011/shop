package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;

import de.shop.bestellverwaltung.domain.Lieferant;
import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Dependent
@Log
public class LieferantServiceLogImplementierung implements Serializable, LieferantService {
	private static final long serialVersionUID = -2706034162174866009L;

	@Override
	public List<Lieferant> findAllLieferanten() {
		return Mock.findAllLieferanten();
	}

	@Override
	public Lieferant findLieferantById(Long id) {
		return Mock.findLieferantById(id);
	}

	@Override
	public Lieferant createLieferant(Lieferant lieferant) {
		return Mock.createLieferant(lieferant);
	}

	@Override
	public void updateLieferant(Lieferant lieferant) {
		Mock.updateLieferant(lieferant);
	}

	@Override
	public void deleteLieferant(Long liefernantId) {
		Mock.deleteLieferant(liefernantId);
	}	
}
