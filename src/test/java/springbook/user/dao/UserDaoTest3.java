package springbook.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest3 {
    private final UserDao dao;

    UserDaoTest3() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.dao = context.getBean("userDao", UserDao.class);
    }

    @BeforeEach
    void eachSetup() throws ClassNotFoundException, SQLException {
        User user = new User();
        user.setId("sa_lee");
        user.setName("이상국");
        user.setPassword("pass");
        dao.add(user);
    }

    @AfterEach
    void eachCleanup() throws ClassNotFoundException, SQLException {
        dao.clean();
    }

    @Test
    @DisplayName("계정 추가 테스트")
    void addTest() throws ClassNotFoundException, SQLException {
        User user = new User();
        user.setId("sa_lee2");
        user.setName("이상국2");
        user.setPassword("pass2");

        int affectedRowCount = dao.add(user);
        assertEquals(affectedRowCount, 1);
    }

    @Test
    @DisplayName("계정 조회 테스트")
    void getTest() throws ClassNotFoundException, SQLException {
        User user = dao.get("sa_lee");
        assertEquals(user.getId(), "sa_lee");
    }
}
