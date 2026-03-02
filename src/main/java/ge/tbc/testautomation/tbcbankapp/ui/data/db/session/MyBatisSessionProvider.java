package ge.tbc.testautomation.tbcbankapp.ui.data.db.session;

import org.apache.groovy.internal.util.Function;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisSessionProvider {

    private static final SqlSessionFactory FACTORY;

    static {
        try (InputStream config = Resources.getResourceAsStream("MyBatisConfig.xml")) {
            FACTORY = new SqlSessionFactoryBuilder().build(config);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to initialise MyBatis: " + e.getMessage());
        }
    }

    public static <M, R> R execute(Class<M> mapperClass, Function<M, R> action) {
        try (SqlSession session = FACTORY.openSession()) {
            return action.apply(session.getMapper(mapperClass));
        }
    }
}
