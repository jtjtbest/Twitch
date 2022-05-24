package com.laioffer.twitch.dao;

import com.laioffer.twitch.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;

@Repository
public class RegisterDao {

    @Autowired
    private SessionFactory sessionFactory; // 把sessionFactory注册成了Bean
    // session stored on heap

    public boolean register(User user){
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user); // sql insert into db
            // 这里可以加其他操作
            session.getTransaction().commit(); // 执行上面的session操作
        } catch (PersistenceException | IllegalStateException ex) {

            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) {
                session.close(); // close the session after done
            }
        }
        return true;
    }
}
