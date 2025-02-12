package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.exception.UserNotFoundException;
import web.model.User;


import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

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
        try {
            User user = userDao.getUserById(id);
            return user;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
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
        try {
            User user = userDao.getUserById(id);
            userDao.deleteUser(id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

    }
}
