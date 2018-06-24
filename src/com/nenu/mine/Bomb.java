package com.nenu.mine;

import com.nenu.mine.MineClient;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 游戏窗口中的格子。
 * 1.开始玩游戏的时候要保证第一下点击的不是地雷
 * 2.点击到数字0（空白）时自动延伸
 * 解决：
 * 在解决问题之前 我们要首先知道鼠标点击的是哪个地雷
 * 解释一下，我们可以通过getx() ,gety()方法获得当前鼠标的坐标，然后把鼠标的宽和高认为1
 * new一个矩形对象
 * 然后通过API提供的intersects方法判断两个矩形是否相交，也就是鼠标点击的地雷位置
 * 1.为了保证第一次点击时点击不到地雷，我们在mousePressed(MouseEvent e)方法中初始化地图，把当前鼠标
 *  点击的位置设置为地雷，当随机初始完地雷后再把当前位置恢复默认
 * 2.这个应该是最难的了吧。当然 如果你学过算法中的搜索 那么就太简单了
 * DFS和BFS都行。从当前位置出发，向它的八个方向进行深搜 直到遇到数字结束当前递归
 */
public class Bomb {
	/**
	 * x坐标
	 */
	private int x;
	/**
	 * y坐标
	 */
	private int y;
	/**
	 * 当前格子所画的内容
	 */
	private int what;
	/**
	 * 当前格子实际内容
	 */
	private int hide = 0;
	/**
	 * 格子宽width
	 */
	private int w = 19;
	/**
	 * 格子长height
	 */
	private int h = 19;
	/**
	 * 客户端对象
	 */
	private MineClient mc;
	/**
	 * 工具包
	 */
	private Toolkit tk = Toolkit.getDefaultToolkit();
	/**
	 * 正常显示的雷
	 */
	private Image bomb = tk.getImage("Image/bomb.jpg");
	/**
	 * 红色背景的雷，表示踩中了，游戏失败
	 */
	private Image bomb0 = tk.getImage("Image/bomb0.jpg");
	/**
	 * 周围没有雷，向8个方向延伸
	 */
	private Image zeroBomb = tk.getImage("Image/0.jpg");
	/**
	 * 周围1个雷
	 */
	private Image oneBomb = tk.getImage("Image/1.jpg");
	/**
	 * 周围2个雷
	 */
	private Image twoBomb = tk.getImage("Image/2.jpg");
	/**
	 * 周围3个雷
	 */
	private Image threeBomb = tk.getImage("Image/3.jpg");
	/**
	 * 周围4个雷
	 */
	private Image fourBomb = tk.getImage("Image/4.jpg");
	/**
	 * 周围5个雷
	 */
	private Image fiveBomb = tk.getImage("Image/5.jpg");
	/**
	 * 周围6个雷
	 */
	private Image sixBomb = tk.getImage("Image/6.jpg");
	/**
	 * 周围7个雷
	 */
	private Image severnBomb = tk.getImage("Image/7.jpg");
	/**
	 * 周围8个雷
	 */
	private Image eightBomb = tk.getImage("Image/8.jpg");
	/**
	 * 右键单击一次标记为雷
	 */
	private Image flag = tk.getImage("Image/flag.jpg");
	/**
	 * 右键单击两次变成问号
	 */
	private Image flag2 = tk.getImage("Image/flag2.jpg");
	/**
	 * 正常的未点开的雷，右键单击三次也恢复正常
	 */
	private Image bg = tk.getImage("Image/s.jpg");
	/**
	 * 无参构造方法
	 */
	public Bomb() {
		super();
	}

	/**
	 * 有参构造方法
	 *
	 * @param x 表示格子的x坐标
	 * @param y 表示格子的y坐标
	 * @param what 表示当前格子显示的内容
	 * @param mc 客户端对象
	 */
	public Bomb(int x, int y, int what, MineClient mc) {
		super();
		this.x = x;
		this.y = y;
		this.what = what;
		this.mc = mc;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}

	/**
	 * 画地雷 数字
	 * 状态0-8表示周围有几个雷
	 * 状态9表示是隐藏的地雷
	 * 状态10表示被点中的地雷
	 * 状态11表示右键单击一次
	 * 状态12表示右键单击两次
	 * 状态13表示未被单击或右键单击三次
	 *
	 * @param g 图像对象
	 */
	public void draw(Graphics g) {
		switch (what) {
			case 0:
				g.drawImage(zeroBomb, x, y, w, h, mc);
				break;
			case 1:
				g.drawImage(oneBomb, x, y, w, h, mc);
				break;
			case 2:
				g.drawImage(twoBomb, x, y, w, h, mc);
				break;
			case 3:
				g.drawImage(threeBomb, x, y, w, h, mc);
				break;
			case 4:
				g.drawImage(fourBomb, x, y, w, h, mc);
				break;
			case 5:
				g.drawImage(fiveBomb, x, y, w, h, mc);
				break;
			case 6:
				g.drawImage(sixBomb, x, y, w, h, mc);
				break;
			case 7:
				g.drawImage(severnBomb, x, y, w, h, mc);
				break;
			case 8:
				g.drawImage(eightBomb, x, y, w, h, mc);
				break;
			case 9:
				g.drawImage(bomb, x, y, w, h, mc);
				break;
			case 10:
				g.drawImage(bomb0, x, y, w, h, mc);
				break;
			case 11:
				g.drawImage(flag, x, y, w, h, mc);
				break;
			case 12:
				g.drawImage(flag2, x, y, w, h, mc);
				break;
			case 13:
				g.drawImage(bg, x, y, w, h, mc);
				break;
		}
	}

	/**
	 * 通过坐标空间中 Rectangle 对象左上方的点 (x,y)、宽度和高度可以定义这个区域。
	 * 返回当前地雷的矩形
	 *
	 * @return 没有返回值
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, w, h);
	}
}
