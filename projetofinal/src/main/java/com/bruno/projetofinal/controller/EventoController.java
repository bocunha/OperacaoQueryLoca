package com.bruno.projetofinal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.projetofinal.dao.EventoDAO;
import com.bruno.projetofinal.dto.VolumeAlarmes;
import com.bruno.projetofinal.model.Evento;

@RestController
public class EventoController {

	@Autowired
	EventoDAO dao;
	VolumeAlarmes dto;

	@GetMapping("/eventos")
	public ArrayList<Evento> recuperarTodosCrescente() {
		ArrayList<Evento> lista;
		lista = (ArrayList<Evento>) dao.findByOrderByDataAsc();
		return lista;
	}

	@GetMapping("/eventos/decresc")
	public ArrayList<Evento> recuperarTodosDecrescente() {
		ArrayList<Evento> lista;
		lista = (ArrayList<Evento>) dao.findByOrderByDataDesc();
		return lista;
	}

	@GetMapping("/eventos/{id}")
	public Evento recuperarPorId(@PathVariable int id) {
		Evento evento = dao.findById(id).orElse(null);
		return evento;
	}

	@GetMapping("/eventos/alarmes")
	public ArrayList<VolumeAlarmes> recuperarTodosNome() {
		ArrayList<VolumeAlarmes> top = dao.getAllWithName();
		return top;
	}

	@GetMapping("/eventos/alarmes/top")
	public ArrayList<VolumeAlarmes> recuperarTopNome() {
		ArrayList<VolumeAlarmes> top = dao.getTopWithName();
		return top;
	}

	@GetMapping("/eventos/alarmes/janeiro")
	public ArrayList<VolumeAlarmes> recuperarJaneiro() {
		try {
			Date inicio = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020");
			Date fim = new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2020");
			return dao.getAllWithNameWithPeriod(inicio, fim);
		} catch (Exception ex) {
			return null;
		}
	}

	@GetMapping("/eventos/alarmes/manual")
	public ArrayList<VolumeAlarmes> recuperarManual() {
		ArrayList<Evento> lista;
		ArrayList<VolumeAlarmes> ret;

		lista = (ArrayList<Evento>) dao.findByOrderByDataDesc();
		ret = new ArrayList<VolumeAlarmes>();
		long count_1 = 0, count_2 = 0, count_3 = 0, count_4 = 0, count_5 = 0;
		String nome_1="",nome_2="",nome_3="",nome_4="",nome_5="";
		for (Evento e : lista) {
			switch (e.getAlarme().getId()) {
			case 1:
				count_1++;
				nome_1 = e.getAlarme().getNome();
				break;
			case 2:
				count_2++;
				nome_2 = e.getAlarme().getNome();
				break;
			case 3:
				count_3++;
				nome_3 = e.getAlarme().getNome();
				break;
			case 4:
				count_4++;
				nome_4 = e.getAlarme().getNome();
				break;
			case 5:
				count_5++;
				nome_5 = e.getAlarme().getNome();
				break;
			}
			
		}

		ret.add(new VolumeAlarmes(1, nome_1, count_1));
		ret.add(new VolumeAlarmes(2, nome_2, count_2));
		ret.add(new VolumeAlarmes(3, nome_3, count_3));
		ret.add(new VolumeAlarmes(4, nome_4, count_4));
		ret.add(new VolumeAlarmes(5, nome_5, count_5));
		
		return ret;
	}
}
