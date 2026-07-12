package com.utp.sistemafuneraria.recursos.dao;

import java.beans.Statement;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.utp.sistemafuneraria.recursos.Sala;

public class SalaDAOImpl implements SalaDAO {
    
    private final DataSource dataSource;

    public SalaDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Sala> listar() {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM Sala WHERE fechaEliminacion IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                salas.add(mapearFila(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar salas: " + e.getMessage(), e);
        }

        return salas;
    }

    @Override
    public Optional<Sala> buscarPorId(Integer id) {
        
        String sql = "SELECT * FROM Sala WHERE idSala = ? AND fechaEliminacion IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearFila(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sala con id " + id + ": " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public Sala insertar(Sala sala) {
        
        String sql = "INSERT INTO Sala (nombreSala, capacidad, ubicacion, estado, disponibilidad, fechaCreacion, idEmpleadoCreador) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sala.getNombreSala());
            stmt.setInt(2, sala.getCapacidad());
            stmt.setString(3, sala.getUbicacion());
            stmt.setString(4, sala.getEstado());
            stmt.setString(5, sala.getDisponibilidad());
            stmt.setTimestamp(6, Timestamp.valueOf(sala.getFechaCreacion()));
            stmt.setInt(7, sala.getIdEmpleadoCreador());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sala.setIdSala(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar sala: " + e.getMessage(), e);
        }

        return sala;

    }

    @Override
    public Sala actualizar(Sala sala) {
        
        String sql = "UPDATE Sala SET nombreSala = ?, capacidad = ?, ubicacion = ?, estado = ?, " +
                     "disponibilidad = ?, fechaModificacion = ?, idEmpleadoModificador = ? WHERE idSala = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNombreSala());
            stmt.setInt(2, sala.getCapacidad());
            stmt.setString(3, sala.getUbicacion());
            stmt.setString(4, sala.getEstado());
            stmt.setString(5, sala.getDisponibilidad());
            stmt.setTimestamp(6, Timestamp.valueOf(sala.getFechaModificacion()));
            stmt.setInt(7, sala.getIdEmpleadoModificador());
            stmt.setInt(8, sala.getIdSala());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar sala: " + e.getMessage(), e);
        }

        return sala;

    }

    @Override
    public void eliminar(Integer id) {
   
        String sql = "UPDATE Sala SET fechaEliminacion = ? WHERE idSala = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar sala con id " + id + ": " + e.getMessage(), e);
        }
    }

    // Convierte una fila del ResultSet en un objeto Sala
    private Sala mapearFila(ResultSet rs) throws SQLException {
        Sala sala = new Sala();
        sala.setIdSala(rs.getInt("idSala"));
        sala.setNombreSala(rs.getString("nombreSala"));
        sala.setCapacidad(rs.getInt("capacidad"));
        sala.setUbicacion(rs.getString("ubicacion"));
        sala.setEstado(rs.getString("estado"));
        sala.setDisponibilidad(rs.getString("disponibilidad"));

        if (rs.getTimestamp("fechaCreacion") != null)
            sala.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
        if (rs.getTimestamp("fechaModificacion") != null)
            sala.setFechaModificacion(rs.getTimestamp("fechaModificacion").toLocalDateTime());
        if (rs.getTimestamp("fechaEliminacion") != null)
            sala.setFechaEliminacion(rs.getTimestamp("fechaEliminacion").toLocalDateTime());

        sala.setIdEmpleadoCreador(rs.getInt("idEmpleadoCreador"));

        int modificador = rs.getInt("idEmpleadoModificador");
        if (!rs.wasNull()) sala.setIdEmpleadoModificador(modificador);

        return sala;
    }

}
