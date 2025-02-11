package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.exception.UserNotFoundException;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        try {
            return em.createQuery("select u from User u where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (EntityNotFoundException | NoResultException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
}
