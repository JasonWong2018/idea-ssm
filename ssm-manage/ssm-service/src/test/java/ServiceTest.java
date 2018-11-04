import com.ssm.common.utils.FastJsonUtil;
import com.ssm.user.domain.SysUser;
import com.ssm.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-service.xml",
        "classpath*:spring/applicationContext-dao.xml"})
public class ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        SysUser zhangsan = userService.findUserByUsername("zhangsan");
        System.out.println(zhangsan.getId());
    }

}
