package AP;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

class DrawingPiePanel extends JPanel {
	int[] g_draw = null;
	int[] arc = new int[5];
	int total = 0;
	
//	식비 ■ : 40%
	
public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		//값이 입력되지않았으면 return;
		g_draw = DAO.itemSum(ApProject.yearMonth, ApProject.itemCombo);

		for(int i = 0; i < g_draw.length; i++) {
			total += g_draw[i];
			System.out.println(g_draw[i]);
			System.out.println(total);
		}
		for(int i = 0; i < g_draw.length; i++) {
			arc[i] = (int) (360.0 * g_draw[i] / total);
		}
		//전체 합을 구한다

		// 전체에서의 비중을 구함.
		//arc5 = 전체 - (arc1+arc2+arc3+arc4)로 구함
	
		g.setColor(new Color(249, 244, 191));//색상지정
		g.fillArc(50, 20, 200, 200, 0, arc[0]);//(x축,y축,반지름,반지름,시작각,끝각) - 원호를 그림
		g.setColor(new Color(249, 202, 191));//색상지정
		g.fillArc(50, 20, 200, 200, arc[0], arc[1]);//(x축,y축,반지름,반지름,시작각,끝각) - 원호를 그림
		g.setColor(new Color(171, 205, 236));//색상지정
		g.fillArc(50, 20, 200, 200, arc[0] + arc[1], arc[2]);//(x축,y축,반지름,반지름,시작각,끝각) - 원호를 그림
		g.setColor(new Color(186, 223, 199));//색상지정
		g.fillArc(50, 20, 200, 200, arc[0] + arc[1] + arc[2], arc[3]);//(x축,y축,반지름,반지름,시작각,끝각) - 원호를 그림
		g.setColor(new Color(209, 194, 227));//색상지정		
		g.fillArc(50, 20, 200, 200, arc[0] + arc[1] + arc[2] + arc[3], 360 - (arc[1] + arc[2] + arc[3] +arc[0]));//(x축,y축,반지름,반지름,시작각,끝각) - 원호를 그림
		g.setColor(Color.BLACK);//색상지정
		g.setFont(new Font("굴림체", Font.PLAIN, 12));//폰트 지정
		g.drawString("   식비: 노랑", 300, 150);//범례(legend)
		g.drawString(" 생필품: 빨강", 300, 170);//범례(legend)
		g.drawString(" 교통비: 파랑", 300, 190);//범례(legend)
		g.drawString(" 문화비: 초록", 300, 210);//범례(legend)
		g.drawString(" 통신비: 보라 ", 300, 230);//범례(legend)
	}
}