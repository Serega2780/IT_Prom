package org.test.hrdept.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.test.hrdept.dao.ProfessionDao;
import org.test.hrdept.domain.Profession;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.logging.Logger;

@Transactional
@Repository("professionDao")
public class ProfessionDaoImpl implements ProfessionDao {

    Logger logger = Logger.getLogger("ProfessionDaoImpl");

    private SessionFactory sessionFactory;
    private Session session;

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void insertProfession(Profession Profession) {
        session = sessionFactory.getCurrentSession();
        session.persist(Profession);
        session.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public Profession selectProfessionById(int id) {
        session = sessionFactory.getCurrentSession();
        Query<Profession> query = session.createQuery("from Profession where id = :id");
        query.setParameter("id", id);
        Profession profession = null;
        try {
            profession = query.getSingleResult();
        } catch (NoResultException e) {
            logger.severe("A NoResultException during select Profession by id !!! ");
        }
        return profession;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profession> selectAllProfessions() {
        session = sessionFactory.getCurrentSession();
        List<Profession> professionList = null;
        try {
            professionList = session.createQuery("FROM Profession").list();
        } catch (NoResultException e) {
            logger.severe("A NoResultException during select all Professions !!! ");
        }
        return professionList;
    }

    @Override
    @Transactional(readOnly = true)
    public Profession selectProfessionByName(String name) {
        session = sessionFactory.getCurrentSession();
        Query<Profession> query = session.createQuery("from Profession where name = :name");
        query.setParameter("name", name);
        Profession profession = null;
        try {
            profession = query.getSingleResult();
        } catch (NoResultException e) {
            logger.severe("A NoResultException during select Profession by name !!! ");
        }
        return profession;
    }

    @Override
    public boolean deleteProfession(int id) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Profession WHERE id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public void updateProfession(Profession Profession) {
        session = sessionFactory.getCurrentSession();
        session.update(Profession);
        session.flush();
    }
}
