package springbook.user.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@DirtiesContext
public class UserDaoTest {
    @Autowired
    private UserDao dao;

    private HashMap<String, User> testUsers;

    @Before
    public void setup() {
//        System.out.println(this.context);
//        System.out.println(this);
//        this.dao = context.getBean("userDao", UserDao.class);
//        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "password", true);
//        dao.setDataSource(dataSource);

        testUsers = new HashMap<>();
        testUsers.put("user1", new User("user1", "user1", "user1"));
        testUsers.put("user2", new User("user2", "user2", "user2"));
        testUsers.put("user3", new User("user3", "user3", "user3"));
    }

    @Test
    // 유저 추가/조회 테스트
    public void addAndGet() throws SQLException {
        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.add(testUsers.get("user1"));
        dao.add(testUsers.get("user2"));
        assertEquals(2, dao.getCount());

        User dbUser1 = dao.get(testUsers.get("user1").getId());
        assertEquals(testUsers.get("user1").getName(), dbUser1.getName());
        assertEquals(testUsers.get("user1").getPassword(), dbUser1.getPassword());

        User dbUser2 = dao.get(testUsers.get("user2").getId());
        assertEquals(testUsers.get("user2").getName(), dbUser2.getName());
        assertEquals(testUsers.get("user2").getPassword(), dbUser2.getPassword());
    }

    @Test
    // 유저 카운팅 테스트
    public void count() throws SQLException {
        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.add(testUsers.get("user1"));
        assertEquals(1, dao.getCount());

        dao.add(testUsers.get("user2"));
        assertEquals(2, dao.getCount());

        dao.add(testUsers.get("user3"));
        assertEquals(3, dao.getCount());
    }

    @Test
    // 존재하지 않는 ID 조회시 발생하는 예외 테스트
    public void getUserFailure() throws SQLException {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });
    }
}


