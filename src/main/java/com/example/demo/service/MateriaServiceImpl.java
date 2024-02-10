package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IMateriaRespository;
import com.example.demo.repository.modelo.Materia;
import com.example.demo.service.to.MateriaTO;

@Service
public class MateriaServiceImpl implements IMateriaService {

	@Autowired
	private IMateriaRespository iMateriaRespository;

	@Override
	public List<MateriaTO> buscarPorIdEstudiante(Integer id) {
		// TODO Auto-generated method stub
		List<Materia> lista = this.iMateriaRespository.seleccionarPorIdEstudiante(id);

		List<MateriaTO> listaFinal = new ArrayList<>();
		for (Materia mat : lista) {
			listaFinal.add(this.convertir(mat));
		}
		return listaFinal;
	}

	private MateriaTO convertir(Materia materia) {
		MateriaTO mat = new MateriaTO();
		mat.setCreditos(materia.getCreditos());
		mat.setId(materia.getId());
		mat.setNombre(materia.getNombre());
		return mat;
	}

}
