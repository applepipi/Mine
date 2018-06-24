package com.nenu.mine;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class MyMouseListener extends MouseAdapter {
	private MineClient mc;
	private int colNum;
	private int rowNum;
	private boolean isFirstClick;
	private ArrayList<Bomb> bombList = new ArrayList<Bomb>();
	boolean[] vis ;
	public MyMouseListener() {
		super();
	}

	public MyMouseListener(MineClient mc) {
		super();
		this.mc = mc;
		colNum = mc.getColNum();
		rowNum = mc.getRowNum();
		vis = new boolean[colNum * rowNum];
		bombList = mc.getBombList();
		this.isFirstClick=mc.isFirstClick();
	}
	/*
	 * ����¼�
	 * ������µ���������  ��ô��ʾ��ǰλ�õĵ���
	 * ������µ�����Ҽ�  ��ô��ǵ�ǰλ��
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (mc.getGameState().equals("lose")) {
			return;
		}
		int x = e.getX();
		int y = e.getY();
		Rectangle rec = new Rectangle(x, y, 1, 1);
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (Bomb bomb : bombList) {
				if (rec.intersects(bomb.getRec())) {
					if (bomb.getHide() == 9) {
						mc.setGameState("lose");
					} else {
						if (bomb.getHide() == 0) {
							increasePoint(bombList.indexOf(bomb));
						}
						bomb.setWhat(bomb.getHide());
					}

				}
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			for (Bomb bomb : bombList) {
				if (rec.intersects(bomb.getRec())) {
					if(bomb.getWhat()!=bomb.getHide()){
						if(bomb.getWhat()==13){
							bomb.setWhat(11);
						}
						else if(bomb.getWhat()==11){
							bomb.setWhat(12);
						}
						else if(bomb.getWhat()==12){
							bomb.setWhat(13);
						}
					}
				}
			}
		}

	}

	//�Զ���������ֱ����������
	private void increasePoint(int index) {
		if (vis[index])
			return;
		vis[index] = true;
		boolean edgeU = false, edgeD = false;
		if ((index + 1) % (colNum) != 0)
			edgeU = true;
		if (index % (colNum) != 0)
			edgeD = true;
		if (judgeLimit(index - 1) && edgeD) {
			Bomb bomb = bombList.get(index - 1);
			setVis(bomb, index - 1);
		}

		if (judgeLimit(index + 1) && edgeU) {
			Bomb bomb = bombList.get(index + 1);
			setVis(bomb, index + 1);
		}

		if (judgeLimit(index - colNum)) {
			Bomb bomb = bombList.get(index - colNum);
			setVis(bomb, index - colNum);
		}

		if (judgeLimit(index + colNum)) {
			Bomb bomb = bombList.get(index + colNum);
			setVis(bomb, index + colNum);
		}

		if (judgeLimit(index - colNum + 1) && edgeU) {
			Bomb bomb = bombList.get(index - colNum + 1);
			setVis(bomb, index - colNum + 1);
		}

		if (judgeLimit(index - colNum - 1) && edgeD) {
			Bomb bomb = bombList.get(index - colNum - 1);
			setVis(bomb, index - colNum - 1);
		}

		if (judgeLimit(index + colNum + 1) && edgeU) {
			Bomb bomb = bombList.get(index + colNum + 1);
			setVis(bomb, index + colNum + 1);
		}

		if (judgeLimit(index + colNum - 1) && edgeD) {
			Bomb bomb = bombList.get(index + colNum - 1);
			setVis(bomb, index + colNum - 1);
		}

	}
	//�жϱ߽�
	private boolean judgeLimit(int i) {
		if (i >= 0 && i < bombList.size())
			return true;
		return false;
	}
	//��ʾĳλ��
	public void setVis(Bomb bomb, int index) {
		if (bomb.getWhat() == bomb.getHide() && bomb.getWhat() != 0)
			return;
		if (bomb.getHide() >= 0 && bomb.getHide() <= 8 && bomb.getHide() != 9) {
			bomb.setWhat(bomb.getHide());
			if (bomb.getWhat() == 0)
				increasePoint(index);
		} else {
			increasePoint(index);
		}
	}
	/*
	 * ������������ͬʱ��ʼ��ʼ����ͼ
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (mc.getGameState().equals("lose")) {
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (isFirstClick) {
				isFirstClick = false;
				mc.setFirstClick(false);
				initBomb(e);
				checkBomb();
			}
		}
	}

	private void checkBomb() {

		for (Bomb bomb : bombList) {
			int x = bombList.indexOf(bomb);
			//edgeU edgeD�߽�״ֵ̬
			boolean edgeU = false, edgeD = false;
			if ((x + 1) % (colNum) != 0)
				edgeU = true;
			if (x % (colNum) != 0)
				edgeD = true;
			if (bomb.getHide() != 9) {
				if (judge(x - 1) && edgeD)
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x + 1) && edgeU)
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x - colNum))
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x + colNum))
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x - colNum + 1) && edgeU)
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x - colNum - 1) && edgeD)
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x + colNum + 1) && edgeU)
					bomb.setHide(bomb.getHide() + 1);
				if (judge(x + colNum - 1) && edgeD)
					bomb.setHide(bomb.getHide() + 1);
			}
		}
	}
	//�ж�ĳλ���Ƿ��ǵ���
	private boolean judge(int x) {
		if (x >= 0 && x < bombList.size()) {
			if (bombList.get(x).getHide() == 9)
				return true;
		}
		return false;
	}
	/*
	 * ��ʼ������
	 */
	private void initBomb(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Rectangle rec = new Rectangle(x, y, 1, 1);
		Bomb bombTemp=new Bomb();
		int what=0;
		//Ϊ�˱����һ�µ���Ĳ��ǵ��� ������������Ϊ���ף���ʼ��������ɺ� �ڻָ�ԭ��
		for (Bomb bomb : bombList) {
			if(rec.intersects(bomb.getRec())){
				what=bomb.getHide();
				bombTemp=bomb;
				bomb.setHide(9);
				break;
			}
		}
		//ʹ�������  ��ʼ����ͼ
		Random r = new Random();
		for (int i = 0; i < mc.getMineNum(); i++) {
			while (true) {
				int index = r.nextInt(bombList.size());
				if (bombList.get(index).getHide() != 9) {
					bombList.get(index).setHide(9);
					break;
				}
			}
		}
		bombTemp.setHide(what);
	}
}