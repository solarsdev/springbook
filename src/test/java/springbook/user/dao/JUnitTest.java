package springbook.user.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class JUnitTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    UserDao dao;

    static Set<JUnitTest> testObject = new HashSet<>();
    static ApplicationContext contextObject = null;

    @Test(expected = org.springframework.beans.factory.NoSuchBeanDefinitionException.class)
    public void test() {
        context.getBean("unknown_bean");
    }

    @Test
    public void singletonTest() {
        assertThat(dao).isEqualTo(context.getBean("userDao", UserDao.class));

    }
}
