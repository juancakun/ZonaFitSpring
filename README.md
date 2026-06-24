# Zona Fit

Aplicación de consola desarrollada con Spring Boot para la administración de clientes de un gimnasio.

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok
* SLF4J

## Arquitectura

El proyecto está organizado utilizando una arquitectura por capas:

```text
src/main/java
│
├── modelo
│   └── Cliente.java
│
├── repositorio
│   └── ClienteRepositorio.java
│
├── servicio
│   ├── IClienteServicio.java
│   └── ClienteServicio.java
│
└── ZonaFitApplication.java
```

## Funcionalidades

* Listar clientes registrados.
* Buscar cliente por ID.
* Agregar nuevos clientes.
* Modificar clientes existentes.
* Eliminar clientes.
* Persistencia de datos mediante JPA.

## Base de datos

Tabla utilizada:

```sql
CREATE TABLE cliente(
    idcliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    membresia INT NOT NULL
);
```

## Configuración

Modificar el archivo:

```properties
application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/zona_fit_db
spring.datasource.username=root
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Ejecución

Clonar el repositorio:

```bash
git clone <url-del-repositorio>
```

Ingresar al proyecto:

```bash
cd zona-fit
```

Compilar:

```bash
mvn clean install
```

Ejecutar:

```bash
mvn spring-boot:run
```

## Menú principal

```text
1. Agregar Cliente
2. Modificar Cliente
3. Eliminar Cliente
4. Buscar Cliente por Id
5. Mostrar Clientes
6. Salir
```

## Objetivos de aprendizaje

Este proyecto fue desarrollado para practicar:

* Spring Boot.
* Inyección de dependencias.
* Spring Data JPA.
* Hibernate.
* Patrón Repository.
* Arquitectura por capas.
* Integración con MySQL.
* Operaciones CRUD.
* Uso de Lombok.
* Aplicaciones de consola con CommandLineRunner.

```
```
