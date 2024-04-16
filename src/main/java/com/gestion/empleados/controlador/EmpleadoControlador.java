package com.gestion.empleados.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.excepciones.ResourcesNotFoundException;
import com.gestion.empleados.modelo.Empleado;
import com.gestion.empleados.repositorio.EmpleadoRepositorio;



@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class EmpleadoControlador {

	@Autowired
	private EmpleadoRepositorio empleadoRepositorio;

	//Listar empleados
	@GetMapping("/empleados")
	public List<Empleado> listarTodosLosEmpleados() {
		return empleadoRepositorio.findAll();
	}
	
	//Guardar empleados
	@PostMapping("/empleados")
	public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
		return empleadoRepositorio.save(empleado);
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable Long id){
		Empleado empleado = empleadoRepositorio.findById(id)
				.orElseThrow(() -> new ResourcesNotFoundException("Employees Not Found"));
		return ResponseEntity.ok().body(empleado);
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, 
			@RequestBody Empleado detallesEmpleado){
		
		Empleado empleado = empleadoRepositorio.findById(id)
				.orElseThrow(() -> new ResourcesNotFoundException("Employees Not Found"));


		empleado.setNombre(detallesEmpleado.getNombre());
		empleado.setApellido(detallesEmpleado.getApellido());
		
		Empleado empleadoActualizado = empleadoRepositorio.save(empleado);
		
		return ResponseEntity.ok(empleadoActualizado);
	}
	
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<Empleado> eliminarEmpleado(@PathVariable Long id){
		Empleado empleado = empleadoRepositorio.findById(id)
				.orElseThrow(() -> new ResourcesNotFoundException("Employees Not Found"));
		
		empleadoRepositorio.deleteById(id);
		
		return ResponseEntity.ok().body(empleado);
	}
}
