package net.erasmatov.crudapp.repository.jdbc;

import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.SkillRepository;
import net.erasmatov.crudapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    private Transaction transaction = null;

    @Override
    public List<Skill> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Skill", Skill.class).list();
        }
    }


    @Override
    public Skill getById(Integer id) {
        Skill skill = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            skill = session.get(Skill.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return skill;
    }


    @Override
    public void deleteById(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Skill skill = session.get(Skill.class, id);
            skill.setStatus(Status.DELETED);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public Skill save(Skill skill) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(skill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return skill;
    }


    @Override
    public Skill update(Skill skill) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(skill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return skill;
    }

}
