package org.test.hrdept.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.domain.Department;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import java.util.List;
import java.util.logging.Logger;

@Transactional
@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {

    private SessionFactory sessionFactory;
    private Session session;

    Logger logger = Logger.getLogger("DepartmentDaoImpl");

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
        Department department = null;
        try {
            department = query.getSingleResult();
        } catch (NoResultException e) {
            logger.severe("A NoResultException during select Department by id!!! ");
        }
        return department;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> selectAllDepartments() {
        session = sessionFactory.getCurrentSession();
        List<Department> departmentList = null;
        try {
            departmentList = session.createQuery("FROM Department").list();
        } catch (NoResultException e) {
            logger.severe("A NoResultException during select all Departments !!! ");
        }
        return departmentList;
    }

    @Override
    @Transactional(readOnly = true)
    public Department selectDepartmentByName(String name) {
        session = sessionFactory.getCurrentSession();
        Query<Department> query = session.createQuery("FROM Department where name = :name");
        query.setParameter("name", name);
        Department department = null;
        try {
            department = query.getSingleResult();
        } catch (NoResultException e) {
            logger.severe("A NoResultException during select Department by name!!! ");
        }
        return department;
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
