package org.test.hrdept.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.test.hrdept.dao.ProfessionDao;
import org.test.hrdept.domain.Profession;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Repository("professionDao")
public class ProfessionDaoImpl implements ProfessionDao {

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
        return query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profession> selectAllProfessions() {
        session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Profession").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Profession selectProfessionByName(String name) {
        session = sessionFactory.getCurrentSession();
        Query<Profession> query = session.createQuery("from Profession where name = :name");
        query.setParameter("name", name);
        return query.getSingleResult();
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
