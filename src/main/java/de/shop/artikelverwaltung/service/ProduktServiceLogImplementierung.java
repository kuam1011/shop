package de.shop.artikelverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;

import de.shop.artikelverwaltung.domain.Produkt;
import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Log
@Dependent
public class ProduktServiceLogImplementierung implements Serializable, ProduktService {	

	private static final long serialVersionUID = 1339767144764086140L;
	@Override
	public Produkt findProduktById(Long id) {
		return Mock.findProduktById(id);
	}
	@Override
	public Produkt createProdukt(Produkt produkt) {
		return Mock.createProdukt(produkt);
	}
	@Override
	public List<Produkt> findAllProdukte() {
		return Mock.findAllProdukte();
	}
	@Override
	public void updateProdukt(Produkt produkt) {
		Mock.updateProdukt(produkt);
	}
	@Override
	public void deleteProdukt(Long produktId) {
		Mock.deleteProdukt(produktId);
	}
}
