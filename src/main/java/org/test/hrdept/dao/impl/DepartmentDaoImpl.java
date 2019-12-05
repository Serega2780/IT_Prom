package org.test.hrdept.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.domain.Department;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {

    private SessionFactory sessionFactory;
    private Session session;
//
//    public SessionFactory getSessionFactory() {
//
//        return sessionFactory;
//    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void insertDepartment(Department department) {
        session = sessionFactory.getCurrentSession();
        session.persist(department);
        session.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public Department selectDepartmentById(int id) {
        session = sessionFactory.getCurrentSession();
        Query<Department> query = session.createQuery("from Department where id = :id");
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> selectAllDepartments() {
        session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Department").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Department selectDepartmentByName(String name) {
        session = sessionFactory.getCurrentSession();
        Query<Department> query = session.createQuery("from Department where name = :name");
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public boolean deleteDepartment(int id) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Department WHERE id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public void updateDepartment(Department department) {
        session = sessionFactory.getCurrentSession();
        session.update(department);
        session.flush();
    }
}
