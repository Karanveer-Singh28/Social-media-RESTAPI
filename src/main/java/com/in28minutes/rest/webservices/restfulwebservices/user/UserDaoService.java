package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int  usercount=0;

    static {
        users.add(new User(++usercount, "Karan", LocalDate.now().minusYears(20)));
        users.add(new User(++usercount, "user2", LocalDate.now().minusYears(30)));
    }

    public List<User> findAll()
    {
        return users;
    }

    public User findById(Integer id)
    {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User saveUser(User user)
    {
        user.setId(++usercount);
        users.add(user);
        return user;
    }

    public void deleteById(int id)
    {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}

