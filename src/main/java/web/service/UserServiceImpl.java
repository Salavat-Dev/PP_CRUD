package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.exception.UserNotFoundException;
import web.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userDao.deleteUser(id);
    }
}
