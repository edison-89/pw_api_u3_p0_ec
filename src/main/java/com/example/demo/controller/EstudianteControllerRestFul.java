package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.service.IEstudianteService;
import com.example.demo.service.IMateriaService;
import com.example.demo.service.to.EstudianteTO;
import com.example.demo.service.to.MateriaTO;

//API: por el proyecto java

//Servicio->Controller: Clase controller
@RestController // Servicio
@RequestMapping(path = "/estudiantes")
public class EstudianteControllerRestFul {

	@Autowired
	private IEstudianteService estudianteService;

	@Autowired
	private IMateriaService iMateriaService;

	// Metodos: capacidades

	// Path Variable
	// http:pokemon.com/API/v1/jugadores/pokemon/consultar/3
	// ....../consultar/3
	// ....../consultar/{id}

	@GetMapping(path = "/{id}", produces = "application/xml")
	public ResponseEntity<Estudiante> consultar(@PathVariable Integer id) {
		// 240: grupo satisfactorias
		// 240: Recurso Estudiante encontrado satisfactoriamente
		Estudiante estu = this.estudianteService.buscar(id);
		// 200 OK
		// 401 autenticación
		// Contrato de la API (1. documento pdf, Swagger.io)
		return ResponseEntity.status(HttpStatus.OK).body(estu);
	}
	// @PathVariable registro especifico
	// http://localhost:8082/API/v1.0/Matricula/estudiantes/consultar

	// Filtral un conjunto/lista de datos RequestParam
	// http://localhost:8082/API/v1.0/Matricula/estudiantes/consultarTodos?genero=M&edad=10&apellido=cayambe
	// http://localhost:8082/API/v1.0/Matricula/estudiantes/{cedula} GET

	// http://localhost:8082/API/v1.0/Matricula/estudiantes GET
	@GetMapping(path = "/tmp", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<List<Estudiante>> consultarTodos(
			@RequestParam(required = false, defaultValue = "M") String genero) {
		List<Estudiante> lista = this.estudianteService.buscarTodos(genero);
		HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.add("mesaje_242", "Lista consultada de manera satisfactoria");
		cabeceras.add("mensaje_info", "El sistema va estar en mantenimiento el fin de semana");

		return new ResponseEntity<>(lista, cabeceras, 242);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EstudianteTO>> consultarTodosHateoas() {
		List<EstudianteTO> lista = this.estudianteService.buscarTodosTO();

		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}

	// http://localhost:8082/API/v1.0/Matricula/estudiantes GET
	// http://localhost:8082/API/v1.0/Matricula/estudiantes/1 GET
	// http://localhost:8082/API/v1.0/Matricula/estudiantes/1/materias GET
	@GetMapping(path = "/{id}/materias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MateriaTO>> consultarMateriasPorId(@PathVariable Integer id) {
		List<MateriaTO> lista = this.iMateriaService.buscarPorIdEstudiante(id);
		System.out.println(lista);
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}

	@PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
	public void guardar(@RequestBody Estudiante estudiante) {
		this.estudianteService.guardar(estudiante);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void actualizar(@RequestBody Estudiante estudiante, @PathVariable Integer id) {
		estudiante.setId(id);
		this.estudianteService.actualizar(estudiante);
	}

	// http://localhost:8082/API/v1.0/Matricula/estudiantes/actualizarParcial
	@PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void actualizarParcial(@RequestBody Estudiante estudiante, @PathVariable Integer id) {
		this.estudianteService.actualizarParcial(estudiante.getApellido(), estudiante.getNombre(), id);
	}

	@DeleteMapping(path = "/{id}")
	public void borrar(@PathVariable Integer id) {
		this.estudianteService.borrar(id);
	}
}
