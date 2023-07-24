package net.erasmatov.crudapp.repository.jdbc;

import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.SpecialtyRepository;
import net.erasmatov.crudapp.utils.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {

    private Specialty mapResultSetToSpecialty(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Status status = Status.valueOf(resultSet.getString("status"));

            return new Specialty(id, name, status);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Specialty> getAll() {

        final String SQL_GET_ALL_SPECIALTIES = "SELECT * FROM specialties;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_GET_ALL_SPECIALTIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Specialty> specialtiesList = new ArrayList<>();

            while (resultSet.next()) {
                specialtiesList.add(mapResultSetToSpecialty(resultSet));
            }
            return specialtiesList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Specialty getById(Integer id) {

        final String SQL_GET_SPECIALTY_BY_ID = "SELECT * FROM specialties WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_GET_SPECIALTY_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Specialty specialty = new Specialty();

            if (resultSet.next()) {
                specialty = mapResultSetToSpecialty(resultSet);
            }
            return specialty;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void deleteById(Integer id) {

        final String SQL_UPDATE_SPECIALTY_STATUS = "UPDATE specialties SET status = 'DELETED' WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_UPDATE_SPECIALTY_STATUS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Specialty save(Specialty specialty) {

        final String SQL_SAVE_SPECIALTY =
                "INSERT INTO specialties (name, status) values (?, ?);";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKeys(SQL_SAVE_SPECIALTY)) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.setString(2, specialty.getStatus().toString());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating specialty failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    specialty.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating specialty failed, no ID obtained.");
                }
            }
            return specialty;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Specialty update(Specialty specialty) {

        final String SQL_UPDATE_SPECIALTY = "UPDATE specialties SET name = ?, status = ? WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_UPDATE_SPECIALTY)) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.setString(2, specialty.getStatus().toString());
            preparedStatement.setInt(3, specialty.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialty;
    }

}
