import com.alibaba.fastjson.JSONObject;
import com.ssm.role.domain.RoleInfo;
import com.ssm.role.mapper.RoleMapper;
import com.ssm.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"spring/applicationContext-dao.xml"})
public class DaoTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test1(){
        User user = new User();
        user.setId(2);
        List<RoleInfo> roleList = roleMapper.getRoleList(user);
        System.out.println(JSONObject.toJSON(roleList));
    }
}
