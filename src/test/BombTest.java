package test;

import com.nenu.mine.Bomb;
import com.nenu.mine.MineClient;
import org.junit.Test;

/**
 * BombTest
 *
 * @author Daydreamer
 * @date 2018/6/22 13:31
 */
public class BombTest {

    private static Bomb bomb;

    @Test
    public void rectangleTest(){
        MineClient mc = new MineClient(200, 200, 10);
        Bomb bomb = new Bomb(20, 40, 9, mc);
        System.out.println(bomb.getRec().toString());
    }
}
