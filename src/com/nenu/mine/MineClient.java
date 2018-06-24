package com.nenu.mine;

import com.nenu.mine.Bomb;
import com.nenu.mine.MyMouseListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * 客户端界面。
 *
 * 首先说一下扫雷规则
 * 1.数字是几就表示此数字位置邻接的八个方向有几个地雷
 * 2.鼠标右键单击一次标记此位置为地雷（插上一个小旗），再单击一次标记为？
 * 3.鼠标左键单击某位置
 * 如果这个位置上的数字为1-8 即显示
 * 如果为数字为0（即空白） 自动延伸八个方向直到遇见数字（1-8）为止
 * 如果为地雷，游戏失败
 * 4.当地图上的所有数字都点击完毕 游戏胜利
 * 首先是MineClient类，初始化地雷，有刷新线程，画图等等
 */

public class MineClient extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * 屏幕高度
	 */
	private int screenHeight;
	/**
	 * 图片宽度
	 */
	private int imgWidth = 20;
	/**
	 * 图片高度
	 */
	private int imgHeight = 20;
	/**
	 * 地图的行数
	 */
	private int rowNum = 0;
	/**
	 * 地图的列数
	 */
	private int colNum = 0;
	/**
	 * 地雷总数
	 */
	private int mineNum = 99;
	/**
	 * 计时器
	 */
	private int timer = 0;
	/**
	 * 游戏时间
	 */
	private int time = 0;
	/**
	 * 未扫雷个数
	 */
	private int restMine;
	/**
	 * 不是雷个数
	 */
	private int notMine;
	private MyPanel myPanel;
	/**
	 * 当前游戏状态
	 */
	private String gameState = "start";
	/**
	 * 第一次点击
	 */
	private boolean firstClick = true;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem lowLevel;
	private JMenuItem midLevel;
	private JMenuItem heightLevel;
	private JMenuItem restart;
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Image icon = tk.getImage("Image/icon.jpg");
	/**
	 * 地图集合
	 */
	private ArrayList<Bomb> bombList = new ArrayList<Bomb>();

	/**
	 * MineClient有参构造方法
	 *
	 * @param screenWidth 屏幕宽度
	 * @param screenHeight 屏幕高度
	 * @param mineNum 雷数
	 */
	public MineClient(int screenWidth, int screenHeight, int mineNum) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.mineNum = mineNum;
		//初始化菜单栏
		initMenu();
		setTitle("扫雷");
		setIconImage(icon);
		setSize(screenWidth, screenHeight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		initList();
		myPanel = new MyPanel();
		myPanel.setBackground(Color.BLACK);
		add(myPanel);
		//鼠标事件
		myPanel.addMouseListener(new MyMouseListener(this));
		new updateThread().start();
	}

	/**
	 * 初始化菜单栏
	 */
	private void initMenu() {
		menu = new JMenu("参数设置");
		menuBar = new JMenuBar();
		lowLevel = new JMenuItem("初级（10个雷）");
		midLevel = new JMenuItem("中级（44个雷）");
		heightLevel = new JMenuItem("高级（99个雷）");
		restart = new JMenuItem("重新开始");
		lowLevel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();// 释放由此 Window、其子组件及其拥有的所有子组件所使用的所有本机屏幕资源。
				new MineClient(225, 305, 10);
			}
		});
		midLevel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new MineClient(380, 460, 44);
			}
		});
		heightLevel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new MineClient(660, 460, 99);
			}
		});
		restart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new MineClient(screenWidth, screenHeight, mineNum);
			}
		});
		menu.add(restart);
		menu.add(new JSeparator());
		menu.add(lowLevel);
		menu.add(midLevel);
		menu.add(heightLevel);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	public boolean isFirstClick() {
		return firstClick;
	}

	public void setFirstClick(boolean firstClick) {
		this.firstClick = firstClick;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public MyPanel getMyPanel() {
		return myPanel;
	}

	public void setMyPanel(MyPanel myPanel) {
		this.myPanel = myPanel;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public ArrayList<Bomb> getBombList() {
		return bombList;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getMineNum() {
		return mineNum;
	}

	/**
	 * 创建地图
	 */
	private void initList() {
		for (int i = imgWidth; i < this.getWidth() - 2 * imgWidth; i += imgWidth) {
			for (int j = imgWidth; j < this.getHeight() - 6 * imgWidth; j += imgHeight) {
				rowNum = rowNum > i / imgWidth ? rowNum : i / imgWidth;
				colNum = colNum > j / imgWidth ? colNum : j / imgWidth;
				Bomb bomb = new Bomb(i, j, 13, this);
				bombList.add(bomb);
			}
		}
	}

	public static void main(String[] args) {
		new MineClient(225, 305, 10);

	}

	/**
	 * 自定义Panel
	 */
	public class MyPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		/**
		 * 画图
		 *
		 * @param g Graphics对象
		 */
		public void paint(Graphics g) {
			super.paintComponent(g);
			restMine = mineNum;
			notMine = 0;
			//画地雷 数字
			for (Bomb bomb : bombList) {
				bomb.draw(g);
				if (bomb.getWhat() == 11)
					restMine--;//未扫雷
				if (bomb.getWhat() >= 0 && bomb.getWhat() <= 8)
					notMine++;//不是雷
			}
			//游戏失败
			if (gameState.equals("lose")) {
				for (Bomb bomb : bombList) {
					if (bomb.getHide() == 9) {
						bomb.setWhat(bomb.getHide());
					}
				}
				Font font = new Font("微软雅黑", Font.BOLD, 20);
				g.setFont(font);
				g.setColor(new Color(255, 0, 255));
				g.drawString("GAME OVER!!", this.getWidth() / 2 - 80,
						this.getHeight() / 2);
			}
			//画当前游戏进行时间  和未扫的地雷数目
			drawTimeAndMineNum(g);

			//取得游戏胜利
			if (!gameState.equals("lose") && notMine + mineNum == colNum * rowNum) {
				gameState = "win";
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image img = tk.getImage("Image/win.jpg");
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
				Font font1 = new Font("华文行楷", Font.BOLD, 40);
				g.setFont(font1);
				g.setColor(new Color(248, 29, 56));
				g.drawString("YOU WIN!!!", this.getWidth() / 2 - 100, 30);
			}
		}

		/**
		 * 显示已用时与未扫雷情况
		 *
		 * @param g graphics对象
		 */
		private void drawTimeAndMineNum(Graphics g) {
			Font font = new Font("微软雅黑", Font.BOLD, 15);
			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("已用时：" + time + " 秒", 0, this.getHeight() - 20);
			g.drawString("未扫雷：" + restMine + " 个", this.getWidth() - 100, this.getHeight() - 20);

		}
	}

	/**
	 * 屏幕每隔100ms刷新一次
	 */
	public class updateThread extends Thread {
		public void run() {

			while (true) {
				repaint();
				if (!firstClick) {
					timer += 100;
					if (timer == 1000) {
						timer = 0;
						time++;
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
