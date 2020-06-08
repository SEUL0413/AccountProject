//package AP;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//public class ViewDAO extends JFrame implements ActionListener{
//	
//	JTextField searchY = new JTextField(5);
//	JTextField searchM = new JTextField(3);
//	JLabel name = new JLabel("ACCOUNT PROGRAM");
//	JLabel team = new JLabel("봉재성, 민경우, 최가슬");
//	JButton search = new JButton("검색");
//	JPanel pa1 = new JPanel();
//	JPanel pa2 = new JPanel();
//	JPanel pa3 = new JPanel();
//	JPanel pa4 = new JPanel();
//	String[] dayOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
//	JButton[] calBtn = new JButton[49];
//	
//	Calendar cal = Calendar.getInstance();
//	int year = cal.get(Calendar.YEAR);
//	int month = cal.get(Calendar.MONTH)+1; //month는 0이 1월 11이 12월이다
//	int date = cal.get(Calendar.DATE);	
//	int lastDay = cal.getActualMaximum(Calendar.DATE);
//	int cnt = 1;
//	
//	ArrayList<VO> vo = new ArrayList<VO>();
//	String writeDate;
//	String type = null;
//	String item = null;
//	String memo = null;
//	int money;
//	
//	public ViewDAO() {
//		
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		
//	}
//}
