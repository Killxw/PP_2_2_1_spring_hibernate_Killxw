package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    public Optional<User> findUser(String model, int series) {
        String hql = "SELECT u FROM User u WHERE u.car.model = :model AND u.car.series = :series";
        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);

        User result = query.uniqueResult();
        return Optional.ofNullable(result);
    }
}
