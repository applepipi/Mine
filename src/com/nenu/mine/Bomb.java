package com.nenu.mine;

import com.nenu.mine.MineClient;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * ��Ϸ�����еĸ��ӡ�
 * 1.��ʼ����Ϸ��ʱ��Ҫ��֤��һ�µ���Ĳ��ǵ���
 * 2.���������0���հף�ʱ�Զ�����
 * �����
 * �ڽ������֮ǰ ����Ҫ����֪������������ĸ�����
 * ����һ�£����ǿ���ͨ��getx() ,gety()������õ�ǰ�������꣬Ȼ������Ŀ�͸���Ϊ1
 * newһ�����ζ���
 * Ȼ��ͨ��API�ṩ��intersects�����ж����������Ƿ��ཻ��Ҳ����������ĵ���λ��
 * 1.Ϊ�˱�֤��һ�ε��ʱ����������ף�������mousePressed(MouseEvent e)�����г�ʼ����ͼ���ѵ�ǰ���
 *  �����λ������Ϊ���ף��������ʼ����׺��ٰѵ�ǰλ�ûָ�Ĭ��
 * 2.���Ӧ�������ѵ��˰ɡ���Ȼ �����ѧ���㷨�е����� ��ô��̫����
 * DFS��BFS���С��ӵ�ǰλ�ó����������İ˸������������ ֱ���������ֽ�����ǰ�ݹ�
 */
public class Bomb {
	/**
	 * x����
	 */
	private int x;
	/**
	 * y����
	 */
	private int y;
	/**
	 * ��ǰ��������������
	 */
	private int what;
	/**
	 * ��ǰ����ʵ������
	 */
	private int hide = 0;
	/**
	 * ���ӿ�width
	 */
	private int w = 19;
	/**
	 * ���ӳ�height
	 */
	private int h = 19;
	/**
	 * �ͻ��˶���
	 */
	private MineClient mc;
	/**
	 * ���߰�
	 */
	private Toolkit tk = Toolkit.getDefaultToolkit();
	/**
	 * ������ʾ����
	 */
	private Image bomb = tk.getImage("Image/bomb.jpg");
	/**
	 * ��ɫ�������ף���ʾ�����ˣ���Ϸʧ��
	 */
	private Image bomb0 = tk.getImage("Image/bomb0.jpg");
	/**
	 * ��Χû���ף���8����������
	 */
	private Image zeroBomb = tk.getImage("Image/0.jpg");
	/**
	 * ��Χ1����
	 */
	private Image oneBomb = tk.getImage("Image/1.jpg");
	/**
	 * ��Χ2����
	 */
	private Image twoBomb = tk.getImage("Image/2.jpg");
	/**
	 * ��Χ3����
	 */
	private Image threeBomb = tk.getImage("Image/3.jpg");
	/**
	 * ��Χ4����
	 */
	private Image fourBomb = tk.getImage("Image/4.jpg");
	/**
	 * ��Χ5����
	 */
	private Image fiveBomb = tk.getImage("Image/5.jpg");
	/**
	 * ��Χ6����
	 */
	private Image sixBomb = tk.getImage("Image/6.jpg");
	/**
	 * ��Χ7����
	 */
	private Image severnBomb = tk.getImage("Image/7.jpg");
	/**
	 * ��Χ8����
	 */
	private Image eightBomb = tk.getImage("Image/8.jpg");
	/**
	 * �Ҽ�����һ�α��Ϊ��
	 */
	private Image flag = tk.getImage("Image/flag.jpg");
	/**
	 * �Ҽ��������α���ʺ�
	 */
	private Image flag2 = tk.getImage("Image/flag2.jpg");
	/**
	 * ������δ�㿪���ף��Ҽ���������Ҳ�ָ�����
	 */
	private Image bg = tk.getImage("Image/s.jpg");
	/**
	 * �޲ι��췽��
	 */
	public Bomb() {
		super();
	}

	/**
	 * �вι��췽��
	 *
	 * @param x ��ʾ���ӵ�x����
	 * @param y ��ʾ���ӵ�y����
	 * @param what ��ʾ��ǰ������ʾ������
	 * @param mc �ͻ��˶���
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
	 * ������ ����
	 * ״̬0-8��ʾ��Χ�м�����
	 * ״̬9��ʾ�����صĵ���
	 * ״̬10��ʾ�����еĵ���
	 * ״̬11��ʾ�Ҽ�����һ��
	 * ״̬12��ʾ�Ҽ���������
	 * ״̬13��ʾδ���������Ҽ���������
	 *
	 * @param g ͼ�����
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
	 * ͨ������ռ��� Rectangle �������Ϸ��ĵ� (x,y)����Ⱥ͸߶ȿ��Զ����������
	 * ���ص�ǰ���׵ľ���
	 *
	 * @return û�з���ֵ
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, w, h);
	}
}
