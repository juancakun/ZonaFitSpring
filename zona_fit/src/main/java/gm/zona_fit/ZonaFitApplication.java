package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.repositorio.ClienteRepositorio;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger =
			LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando application");
		//Levantar la fabrica de sping
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Terminando application");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		mostrarClientes();
		var consola = new Scanner(System.in);
		var salir = false ;
		while (!salir) {
			try {
				var opciones = opcionesAplicacion(consola);
				salir = ejecutarOpciones(consola, opciones);
				logger.info(nl);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	private int opcionesAplicacion(Scanner consola) {
		logger.info("""
                \n*** Aplicación Zona Fit (Gym) ***
                Menu:
                1. Agregar Cliente
                2. Modificar Cliente
                3. Eliminar Cliente
                4. Buscar Cliente por Id
                5. Mostrar clientes
                6. Salir
                Elige una opción:\s""");
		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion) {
		var salir = false;
		switch (opcion) {
			case 1 -> agregarClientes(consola);
			case 2 -> modificarClientes(consola);
			case 3 -> eliminarClientes(consola);
			case 4 -> buscarClienteID(consola);
			case 5 -> mostrarClientes();
			case 6 -> salir = true;
			default -> logger.info(nl+"Opción invalida"+nl);
		}
		return salir;
	}

	private void agregarClientes (Scanner consola) {
		logger.info(nl+"Agregando Cliente"+nl);
		logger.info(nl+"Ingrese el nombre del Cliente: ");
		var nombre = consola.nextLine();
		logger.info(nl+"Ingrese el apellido del Cliente: ");
		var apellido = consola.nextLine();
		logger.info(nl+"Ingrese la membresia del Cliente: ");
		var membresia = Integer.parseInt(consola.nextLine());
		Cliente cliente = new Cliente();
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setMembresia(membresia);
		clienteServicio.guardarCliente(cliente);
		logger.info(nl+"Cliente agregado correctamente: " + cliente);
	}

	private void mostrarClientes() {
		logger.info(nl+"*** Clientes listados ***"+nl);
		List<Cliente> clientes = clienteServicio.listarClientes();
		clientes.forEach(cliente -> logger.info(cliente.toString()+nl));
	}

	private void modificarClientes(Scanner consola) {
		logger.info(nl+"--- Modificando Cliente ---"+nl);
		logger.info(nl+"Ingrese el id del Cliente: ");
		int idCliente = Integer.parseInt(consola.nextLine());
		Cliente cliente = new Cliente();
		cliente = clienteServicio.buscarClientePorId(idCliente);
		if (cliente != null) {
			logger.info(nl+"Cliente seleccionado: " + cliente);
			logger.info(nl+"Ingrese el nombre del Cliente: ");
			String nombre = consola.nextLine();
			logger.info(nl+"Ingrese el apellido del Cliente: ");
			String apellido = consola.nextLine();
			logger.info(nl+"Ingrese la membresia del Cliente: ");
			var membresia = Integer.parseInt(consola.nextLine());
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setMembresia(membresia);
			clienteServicio.guardarCliente(cliente);
			logger.info(nl+"Cliente modificado correctamente: " + cliente);
		}

	}

	private	void  eliminarClientes(Scanner consola) {
		mostrarClientes();
		logger.info(nl+"--- Eliminando Cliente ---");
		logger.info(nl+"Ingrese el id del Cliente: ");
		int idCliente = Integer.parseInt(consola.nextLine());
		Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
		if (cliente != null) {
			logger.info("Seguro de eliminar a cliente (s/n): ");
			String respuesta = consola.nextLine();
			if (respuesta.equalsIgnoreCase("s")) {
				clienteServicio.eliminarCliente(cliente);
				logger.info(nl+"Cliente eliminado correctamente");
			}

		}
	}

	private void buscarClienteID(Scanner consola) {
		logger.info(nl+"Buscando Cliente por id"+nl);
		logger.info(nl+"Ingrese el id del Cliente: ");
		var idCliente = Integer.parseInt(consola.nextLine());
		logger.info(nl+"Este es el cliente encontrado: ");
		Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
		logger.info(nl+cliente.toString());
	}

}
