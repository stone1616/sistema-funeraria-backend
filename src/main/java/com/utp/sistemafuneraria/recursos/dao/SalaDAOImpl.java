package com.utp.sistemafuneraria.recursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.utp.sistemafuneraria.recursos.Sala;

@Repository
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

        String sql = "INSERT INTO Sala (nombreSala, capacidad, ubicacion, estado, fechaCreacion, idUsuarioCreacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sala.getNombreSala());
            stmt.setInt(2, sala.getCapacidad());
            stmt.setString(3, sala.getUbicacion());
            stmt.setString(4, sala.getEstado());
            stmt.setTimestamp(5, Timestamp.valueOf(sala.getFechaCreacion()));
            stmt.setInt(6, sala.getIdUsuarioCreacion());

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
                     "fechaModificacion = ?, idUsuarioModificacion = ? WHERE idSala = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNombreSala());
            stmt.setInt(2, sala.getCapacidad());
            stmt.setString(3, sala.getUbicacion());
            stmt.setString(4, sala.getEstado());
            stmt.setTimestamp(5, Timestamp.valueOf(sala.getFechaModificacion()));
            stmt.setInt(6, sala.getIdUsuarioModificacion());
            stmt.setInt(7, sala.getIdSala());

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

    private Sala mapearFila(ResultSet rs) throws SQLException {
        Sala sala = new Sala();
        sala.setIdSala(rs.getInt("idSala"));
        sala.setNombreSala(rs.getString("nombreSala"));
        sala.setCapacidad(rs.getInt("capacidad"));
        sala.setUbicacion(rs.getString("ubicacion"));
        sala.setEstado(rs.getString("estado"));

        if (rs.getTimestamp("fechaCreacion") != null)
            sala.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
        if (rs.getTimestamp("fechaModificacion") != null)
            sala.setFechaModificacion(rs.getTimestamp("fechaModificacion").toLocalDateTime());
        if (rs.getTimestamp("fechaEliminacion") != null)
            sala.setFechaEliminacion(rs.getTimestamp("fechaEliminacion").toLocalDateTime());

        int creador = rs.getInt("idUsuarioCreacion");
        if (!rs.wasNull()) sala.setIdUsuarioCreacion(creador);

        int modificador = rs.getInt("idUsuarioModificacion");
        if (!rs.wasNull()) sala.setIdUsuarioModificacion(modificador);

        return sala;
    }

}
