package logan.common.base.test;

import logan.common.base.utils.FileHandler;
import logan.common.base.utils.orm.GenSqlEntity;
import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JTest {
Logger logger= LoggerFactory.getLogger(JTest.class);
    @Test
    public void sit() throws Exception{
       String s="1002^300000.00^FAILED^20160927143038^20160927145001";
       String[] st=s.split("\\|");
       String[] ss=st[0].split("\\^");
    System.err.println(System.currentTimeMillis()+s);
        synchronized(s){
            s.wait(1000);
        }
        System.err.println(System.currentTimeMillis()+s);
       try {
           int s2=1/0;
       }catch(Exception e) {
           logger.error("sd[{}]", e);
       }

    }

    @Test
    public void TestReflectSql() {
        try {
            List<Class<?>> classes = ClassUtils.getAllSuperclasses(Class.forName("logan.common.base.test.vo.FeeInfo" ));

            for (Class clas : classes) {
                String st = GenSqlEntity.exec(clas);
                FileHandler.writeFile("D:", "initsql.sql", st);
                System.err.println(st);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
