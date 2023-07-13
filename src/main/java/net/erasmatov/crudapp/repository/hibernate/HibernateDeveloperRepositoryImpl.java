package net.erasmatov.crudapp.repository.hibernate;

import net.erasmatov.crudapp.model.Developer;
import net.erasmatov.crudapp.model.Skill;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.DeveloperRepository;
import net.erasmatov.crudapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;


/*
TODO:
        Вопрос про локальные переменные, можно ли сделать их переменными класса? На что влияет и какое отличие?
        Вопрос про удаление Skill'ов у Developer!
*/


public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {

    Transaction transaction = null;

    @Override
    public List<Developer> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Developer", Developer.class).list();
        }
    }


    @Override
    public Developer getById(Integer id) {
        Developer developer = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            developer = session.get(Developer.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return developer;
    }


    @Override
    public void deleteById(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Developer developer = session.get(Developer.class, id);
            developer.setStatus(Status.DELETED);
            Set<Skill> removedSkills = developer.getSkills();

            removedSkills.clear();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public Developer save(Developer developer) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(developer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return developer;
    }


    @Override
    public Developer update(Developer developer) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(developer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return developer;
    }

}
