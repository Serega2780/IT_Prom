package org.test.hrdept.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.test.hrdept.dao.EmployeeDao;
import org.test.hrdept.domain.Employee;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private SessionFactory sessionFactory;
    private Session session;

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void insertEmployee(Employee Employee) {
        session = sessionFactory.getCurrentSession();
        session.persist(Employee);
        session.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee selectEmployeeById(int id) {
        session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createQuery("from Employee where id = :id");
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> selectAllEmployees() {
        session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Employee").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee selectEmployeeByName(String name) {
        session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createQuery("from Employee where name = :name");
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public boolean deleteEmployee(int id) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Employee WHERE id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public void updateEmployee(Employee Employee) {
        session = sessionFactory.getCurrentSession();
        session.update(Employee);
        session.flush();
    }
}
