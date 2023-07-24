package net.erasmatov.crudapp.repository.jdbc;

import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.SkillRepository;
import net.erasmatov.crudapp.utils.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    private Skill mapResultSetToSkill(ResultSet resultSet) {

        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Status status = Status.valueOf(resultSet.getString("status"));

            return new Skill(id, name, status);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Skill> getAll() {

        final String SQL_GET_ALL_SKILLS = "SELECT * FROM skills;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_GET_ALL_SKILLS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Skill> skillList = new ArrayList<>();

            while (resultSet.next()) {
                skillList.add(mapResultSetToSkill(resultSet));
            }
            return skillList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    @Override
    public Skill getById(Integer id) {

        final String SQL_GET_SKILL_BY_ID = "SELECT * FROM skills WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_GET_SKILL_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Skill skill = new Skill();

            if (resultSet.next()) {
                skill = mapResultSetToSkill(resultSet);
            }
            return skill;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    @Override
    public void deleteById(Integer id) {

        final String SQL_UPDATE_SKILL_STATUS = "UPDATE skills SET status = 'DELETED' WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_UPDATE_SKILL_STATUS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public Skill save(Skill skill) {

        final String SQL_SAVE_SKILL = "INSERT INTO skills (name, status) values (?, ?);";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKeys(SQL_SAVE_SKILL)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setString(2, skill.getStatus().toString());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating skill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    skill.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating skill failed, no ID obtained.");
                }
            }
            return skill;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Skill update(Skill skill) {

        final String SQL_UPDATE_SKILL = "UPDATE skills SET name = ?, status = ? WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SQL_UPDATE_SKILL)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setString(2, skill.getStatus().toString());
            preparedStatement.setInt(3, skill.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return skill;
    }

}
