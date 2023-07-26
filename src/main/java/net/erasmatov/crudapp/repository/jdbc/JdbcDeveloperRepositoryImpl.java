package net.erasmatov.crudapp.repository.jdbc;

import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.DeveloperRepository;
import net.erasmatov.crudapp.utils.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private void removeSkillsForUpdateDeveloper(Integer developerId) {
        final String SQL_REMOVE_SKILLS_BY_ID_DEVELOPER =
                "DELETE FROM developers_skills WHERE developer_id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatement(SQL_REMOVE_SKILLS_BY_ID_DEVELOPER)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Skill> saveSkillsForDeveloper(Integer developerId, List<Skill> skillList) {
        final String SQL_SAVE_DEVELOPER_SKILLS =
                "INSERT INTO developers_skills (developer_id, skill_id) values (?, ?)";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatement(SQL_SAVE_DEVELOPER_SKILLS)) {
            for (Skill skill : skillList) {
                preparedStatement.setInt(1, developerId);
                preparedStatement.setInt(2, skill.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skillList;
    }

    private Developer mapResultSetToDeveloper(ResultSet resultSet) {
        try {
            int developer_id = resultSet.getInt("developer_id");
            String developer_first_name = resultSet.getString("developer_first_name");
            String developer_last_name = resultSet.getString("developer_last_name");
            Status developer_status = Status.valueOf(resultSet.getString("developer_status"));

            List<Skill> skills = new ArrayList<>();
            Skill skill = new Skill();
            Integer skill_id = resultSet.getInt("skill_id");
            String skill_name = resultSet.getString("skill_name");
            String skill_status = resultSet.getString("skill_status");

            if (skill_name != null && skill_status != null) {
                skill.setId(skill_id);
                skill.setName(skill_name);
                skill.setStatus(Status.valueOf(skill_status));
                skills.add(skill);
            }

            Specialty specialty = new Specialty(
                    resultSet.getInt("specialty_id"),
                    resultSet.getString("specialty_name"),
                    Status.valueOf(resultSet.getString("specialty_status")));

            return new Developer(
                    developer_id,
                    developer_first_name,
                    developer_last_name,
                    skills, specialty,
                    developer_status);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Developer> getAll() {
        final String SQL_GET_ALL_DEVELOPERS =
                "SELECT dev.id AS developer_id, dev.first_name AS developer_first_name, dev.last_name AS developer_last_name, dev.status AS developer_status, " +
                        "spec.id AS specialty_id, spec.name AS specialty_name, spec.status AS specialty_status, " +
                        "sk.id AS skill_id, sk.name AS skill_name, sk.status AS skill_status " +
                        "FROM developers dev " +
                        "LEFT JOIN specialties spec ON spec.id = dev.specialty_id " +
                        "LEFT JOIN developers_skills dev_sk ON dev_sk.developer_id = dev.id " +
                        "LEFT JOIN skills sk ON sk.id = dev_sk.skill_id;";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatement(SQL_GET_ALL_DEVELOPERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, Developer> developersMap = new HashMap<>();

            while (resultSet.next()) {
                Developer developer = mapResultSetToDeveloper(resultSet);

                if (developersMap.containsKey(developer.getId())) {
                    Developer currentDeveloper = developersMap.get(developer.getId());
                    List<Skill> developerSkills = currentDeveloper.getSkills();
                    developerSkills.add(developer.getSkills().get(0));
                    currentDeveloper.setSkills(developerSkills);

                    developersMap.put(currentDeveloper.getId(), currentDeveloper);
                } else {
                    developersMap.put(developer.getId(), developer);
                }
            }
            return new ArrayList<>(developersMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Developer getById(Integer id) {
        final String SQL_GET_DEVELOPER_BY_ID =
                "SELECT dev.id AS developer_id, dev.first_name AS developer_first_name, dev.last_name AS developer_last_name, dev.status AS developer_status, " +
                        "spec.id AS specialty_id, spec.name AS specialty_name, spec.status AS specialty_status, " +
                        "sk.id AS skill_id, sk.name AS skill_name, sk.status AS skill_status " +
                        "FROM developers dev " +
                        "LEFT JOIN specialties spec ON spec.id = dev.specialty_id " +
                        "LEFT JOIN developers_skills dev_sk ON dev_sk.developer_id = dev.id " +
                        "LEFT JOIN skills sk ON sk.id = dev_sk.skill_id " +
                        "WHERE dev.id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatement(SQL_GET_DEVELOPER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Developer developer = new Developer();

            if (resultSet.next()) {
                developer = mapResultSetToDeveloper(resultSet);
            }
            return developer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        final String SQL_UPDATE_DEVELOPER_STATUS =
                "UPDATE developers SET status = 'DELETED' WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatement(SQL_UPDATE_DEVELOPER_STATUS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Developer save(Developer developer) {
        final String SQL_SAVE_DEVELOPER =
                "INSERT INTO developers (first_name, last_name, status, specialty_id) values (?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatementWithKeys(SQL_SAVE_DEVELOPER)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3, developer.getStatus().toString());
            preparedStatement.setInt(4, developer.getSpecialty().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating developer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    developer.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating developer failed, no ID obtained.");
                }
            }
            saveSkillsForDeveloper(developer.getId(), developer.getSkills());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        final String SQL_UPDATE_DEVELOPER =
                "UPDATE developers SET first_name = ?, last_name = ?, status = ?, specialty_id = ? " +
                        "WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtil.getPreparedStatement(SQL_UPDATE_DEVELOPER)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3, developer.getStatus().toString());
            preparedStatement.setInt(4, developer.getSpecialty().getId());
            preparedStatement.setInt(5, developer.getId());
            preparedStatement.executeUpdate();
            removeSkillsForUpdateDeveloper(developer.getId());
            saveSkillsForDeveloper(developer.getId(), developer.getSkills());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

}
