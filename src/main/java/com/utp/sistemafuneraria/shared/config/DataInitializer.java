package com.utp.sistemafuneraria.shared.config;

import com.utp.sistemafuneraria.personal.Personal;
import com.utp.sistemafuneraria.personal.PersonalRepository;
import com.utp.sistemafuneraria.usuario.Usuario;
import com.utp.sistemafuneraria.usuario.UsuarioRepository;
import com.utp.sistemafuneraria.recursos.Vehiculo;
import com.utp.sistemafuneraria.recursos.VehiculoRepository;
import com.utp.sistemafuneraria.producto.Producto;
import com.utp.sistemafuneraria.producto.ProductoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Profile("h2")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PersonalRepository personalRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            System.out.println("Initializing Database Seed Data for H2...");

            // 1. Create Initial Administrator Personal
            Personal personal = new Personal();
            personal.setNombres("Admin");
            personal.setApellidos("Sistema");
            personal.setCargo("Administrador");
            personal.setEstado("ACTIVO");
            personal.setFechaCreacion(LocalDateTime.now());
            personal.setIdUsuarioCreacion(1); // temporary placeholder
            personal = personalRepository.save(personal);

            // 2. Create Initial Administrator Usuario
            Usuario usuario = new Usuario();
            usuario.setIdPersonal(personal.getIdPersonal());
            usuario.setEmail("admin@blancaflor.com");
            usuario.setPassword(passwordEncoder.encode("admin123"));
            usuario.setRol("ADMIN");
            usuario.setEstado(true);
            usuario.setFechaCreacion(LocalDateTime.now());
            usuario.setIdUsuarioCreacion(1); // self-referencing placeholder
            usuario = usuarioRepository.save(usuario);

            // Update Personal to reference the actual user created
            personal.setIdUsuarioCreacion(usuario.getIdUsuario());
            personalRepository.save(personal);

            // Update Usuario self-reference
            usuario.setIdUsuarioCreacion(usuario.getIdUsuario());
            usuarioRepository.save(usuario);

            // 3. Seed Salas (using JDBC template since it is not JPA mapped in repositories)
            jdbcTemplate.execute("INSERT INTO Sala (nombreSala, capacidad, ubicacion, estado, fechaCreacion, idUsuarioCreacion) " +
                    "VALUES ('Sala Celestial', 50, 'Piso 1 - Ala A', 'Disponible', CURRENT_TIMESTAMP(), " + usuario.getIdUsuario() + ")");
            jdbcTemplate.execute("INSERT INTO Sala (nombreSala, capacidad, ubicacion, estado, fechaCreacion, idUsuarioCreacion) " +
                    "VALUES ('Sala Eterna', 80, 'Piso 1 - Ala B', 'Disponible', CURRENT_TIMESTAMP(), " + usuario.getIdUsuario() + ")");
            jdbcTemplate.execute("INSERT INTO Sala (nombreSala, capacidad, ubicacion, estado, fechaCreacion, idUsuarioCreacion) " +
                    "VALUES ('Sala Consuelo', 30, 'Piso 2 - Ala C', 'Mantenimiento', CURRENT_TIMESTAMP(), " + usuario.getIdUsuario() + ")");

            // 4. Seed Vehiculos
            Vehiculo v1 = new Vehiculo();
            v1.setPlaca("FGN-120");
            v1.setMarca("Mercedes-Benz");
            v1.setModelo("Cortejo E-Class");
            v1.setCapacidad(2);
            v1.setEstado("Disponible");
            v1.setFechaCreacion(LocalDateTime.now());
            v1.setIdUsuarioCreacion(usuario.getIdUsuario());
            vehiculoRepository.save(v1);

            Vehiculo v2 = new Vehiculo();
            v2.setPlaca("AJX-948");
            v2.setMarca("Ford");
            v2.setModelo("Transit Acompañantes");
            v2.setCapacidad(15);
            v2.setEstado("Disponible");
            v2.setFechaCreacion(LocalDateTime.now());
            v2.setIdUsuarioCreacion(usuario.getIdUsuario());
            vehiculoRepository.save(v2);

            // 5. Seed Productos
            Producto p1 = new Producto();
            p1.setNombre("Ataúd Roble Premium");
            p1.setCategoria("ATAUD");
            p1.setMaterial("Madera de Roble");
            p1.setColor("Marrón Caoba");
            p1.setPrecio(new BigDecimal("3500.00"));
            p1.setStock(10);
            p1.setEstado("Activo");
            p1.setFechaCreacion(LocalDateTime.now());
            p1.setIdUsuarioCreacion(usuario.getIdUsuario());
            productoRepository.save(p1);

            Producto p2 = new Producto();
            p2.setNombre("Urna Metálica Clásica");
            p2.setCategoria("URNA");
            p2.setMaterial("Acero Inoxidable");
            p2.setColor("Plata");
            p2.setPrecio(new BigDecimal("850.00"));
            p2.setStock(25);
            p2.setEstado("Activo");
            p2.setFechaCreacion(LocalDateTime.now());
            p2.setIdUsuarioCreacion(usuario.getIdUsuario());
            productoRepository.save(p2);

            Producto p3 = new Producto();
            p3.setNombre("Arreglo Floral Eterna Paz");
            p3.setCategoria("FLORES");
            p3.setMaterial("Rosas y Lirios Blancos");
            p3.setColor("Blanco");
            p3.setPrecio(new BigDecimal("150.00"));
            p3.setStock(50);
            p3.setEstado("Activo");
            p3.setFechaCreacion(LocalDateTime.now());
            p3.setIdUsuarioCreacion(usuario.getIdUsuario());
            productoRepository.save(p3);

            System.out.println("=================================================");
            System.out.println("H2 DATABASE SEEDING COMPLETED SUCCESSFULLY!");
            System.out.println("Admin User account created:");
            System.out.println("  Email: admin@blancaflor.com");
            System.out.println("  Password: admin123");
            System.out.println("=================================================");
        }
    }
}
