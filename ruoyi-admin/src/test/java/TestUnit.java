import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.entity.lucene.PageLucene;
import com.ruoyi.system.service.impl.LuceneServiceImpl;
import com.ruoyi.system.util.QMusicUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/11 21:58
 * @Version 520.1314
 */

@SpringBootTest(classes = RuoYiApplication.class)
public class TestUnit {

    @Autowired
    private LuceneServiceImpl luceneService;

    @Test
    public void test(){
        List<Map> music = QMusicUtil.parseMusic("7975452023");
        System.out.println(music.toString());
    }

    @Test
    public void testLucene(){
        new LuceneServiceImpl().searchIndex(new PageLucene(1,10,0L,"使用",null));
    }
    @Test
    public void testLucene1(){

    }
}
