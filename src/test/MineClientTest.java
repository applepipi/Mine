package test;

import com.nenu.mine.Bomb;
import com.nenu.mine.MineClient;
import org.junit.Test;

import java.util.List;

/**
 * MineClientTest
 * MineClient测试类
 *
 * @author Daydreamer
 *  @date 2018/6/22 14:00
 */
public class MineClientTest {

    @Test
    public void getBombListTest(){
        MineClient mc = new MineClient(225,305,10);
        List<Bomb> list = mc.getBombList();
        for(Bomb b : list){
            System.out.println(b.getRec().toString());
        }
    }
}
