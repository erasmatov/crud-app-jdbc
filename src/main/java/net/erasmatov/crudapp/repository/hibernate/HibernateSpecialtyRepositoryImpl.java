package net.erasmatov.crudapp.repository.hibernate;

import net.erasmatov.crudapp.model.Specialty;
import net.erasmatov.crudapp.model.Status;
import net.erasmatov.crudapp.repository.SpecialtyRepository;
import net.erasmatov.crudapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {

    private Transaction transaction = null;

    @Override
    public List<Specialty> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Specialty", Specialty.class).list();
        }
    }


    @Override
    public Specialty getById(Integer id) {
        Specialty specialty = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            specialty = session.get(Specialty.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return specialty;
    }


    @Override
    public void deleteById(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Specialty specialty = session.get(Specialty.class, id);
            specialty.setStatus(Status.DELETED);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public Specialty save(Specialty specialty) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(specialty);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return specialty;
    }


    @Override
    public Specialty update(Specialty specialty) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(specialty);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return specialty;
    }

}
