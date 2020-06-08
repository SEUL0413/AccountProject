package AP;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

//		총 3개의 뷰
//		1. 전체적인 캘린더 뷰
//		2. 일일 내역 확인 및 입력 뷰
//		3. 1건의 내역 수정 및 삭제 뷰
//		유튜브 켈린더 예시 : https://www.youtube.com/watch?v=S1_50iPHEJo
/* GUI는 JAVA Swing으로 구현하되, 문제 있을 시 카톡으로 의견 조율.
 * Ⅰ. 전체적인 캘린더 뷰 
 
		1. JFrame 윈도우 화면 구축
		2. JFrame은 BorderLayout으로 (사진 참조)
		3. 상단 패널 BorderLayout 
			3-1. 프로그램명(WEST)
			3-2. 날짜 입력칸(CENTER)
			3-3. 제작자명(EAST)
		4. 중간 패널 위 캘린더 GridLayout
			4-1. 버튼 달기 (배열 이용)
			4-2. 버튼에 텍스트 설정 (html)
			//	final String htmlText = "<html>" + i  + "<br/> + <span style =\color: green;\"> deposit " + sumDeposit 
 			* + "/span>" + <br/> <span syle = /"color :red; /">expends " + sumWithdaw + </span></html>";
			//	i는 날짜
		5. 하단 패널
			5-1. 월별 총수입/총지출(JLabel, EAST)
			5-2. 나중에 완성되었을 때 그래프 등 기능 추가를 위해 나머지는 비워두기
	
	=========================
	
	Ⅱ. 일일 내역 확인 및 입력 뷰

		1. JFrame 윈도우 화면 구축
		2. JFrame은 BorderLayout으로 (사진 참조)
		3. 상단 해당 날짜 표시 (JLabel) 
		4. 중간 영역 패널
			4-1. 내역 Panel - JLabel (한줄씩) -> 클릭 가능하게
			>> 참조 : http://www.java2s.com/Tutorials/Java/Swing/JLabel/Add_mouse_click_listener_to_JLabel_in_Java.htm
			=>> 스크롤 기능이 가능한가 확인해야함
			4-2. 지출 및 수입 (JRadioButton)
			4-3. 항목 콤보박스 
			4-4. 금액 텍스트필드
			4-5. 메모 텍스트필드
		5.  하단 영역 패널
			5-1. 추가 버튼
		
	=========================
	
	Ⅲ. 1건의 내역 수정 및 삭제 뷰 : 2번 뷰에서 4-1.의 내역 중 1건 클릭시 팝업
	
		1. JFrame 윈도우 화면 구축
		2. JFrame은 BorderLayout으로 (사진 참조)
		3. 상단 해당 날짜 표시 (JLabel) 
		4. 중간 영역 패널
			4-1. 내역 Panel - JLabel (한줄씩) -> 클릭 가능하게
			>> 참조 : http://www.java2s.com/Tutorials/Java/Swing/JLabel/Add_mouse_click_listener_to_JLabel_in_Java.htm
			=>> 스크롤 기능이 가능한가 확인해야함
			4-2. 지출 및 수입 (JRadioButton)
			4-3. 항목 콤보박스 
			4-4. 금액 텍스트필드
			4-5. 메모 텍스트필드
		5.  하단 영역 패널
			5-1. 수정, 삭제 버튼
			
	=========================
	
	 * Ⅱ + Ⅲ

		1. JFrame 윈도우 화면 구축
		2. JFrame은 BorderLayout으로 (사진 참조)
		3. 상단 해당 날짜 표시 (JLabel) 
		4. 중간 영역 패널
			4-1. 내역 Panel - JLabel (한줄씩) -> 클릭 가능하게
			>> 참조 : http://www.java2s.com/Tutorials/Java/Swing/JLabel/Add_mouse_click_listener_to_JLabel_in_Java.htm
			=>> 스크롤 기능이 가능한가 확인해야함
			
			* 뿌려줘야 할 내용들 (DAO에서 구현을 해야하는 기능들)
				4-1-1. 내역 클릭 시 내용이 각 항목 및 텍스트필드 안에 다시 보임
				4-1-2. 추가 혹은 수정 버튼 클릭 시, 기입 내용이 내역 목록에 표시됨.
				4-1-3. 삭제 버튼 클릭 시, 목록에서 삭제되며 텍스트필드 안의 내용이 모두 초기화. 
				
			4-2. 지출 및 수입 (JRadioButton)
			4-3. 항목 콤보박스 
			4-4. 금액 텍스트필드
			4-5. 메모 텍스트필드
		5.  하단 영역 패널
			5-1. 추가, 수정, 삭제 버튼
			
*/

public class ApProject extends JFrame implements ActionListener {

//	======캘린더 뷰======
	
//	민경우:	처음에 뷰단을 가장 자세하게 계획하고 코딩을 시작했는데 생각만큼 계획대로 되지 않았고 바꿔야 하는 부분이 많았다.
//			변수를 받는 부분을 전부 전역변수로 다시 올려야 했다.
	
	// 캘린더 뷰 맨 상단에 표시할 프로그램명과 팀원
	JLabel name = new JLabel("ACCOUNT PROGRAM                    ");
	JLabel team = new JLabel("               봉재성, 민경우, 최가슬");

	// 캘린더 검색 및 출력에 필요한 GUI
	JTextField searchY = new JTextField(5);
	JTextField searchM = new JTextField(3);
	JButton search = new JButton("검색");
	String[] dayOfWeek = { "일", "월", "화", "수", "목", "금", "토" };
	JButton[] calBtn = new JButton[49];
	
	// 캘린더 출력에 필요한 멤버 변수 (Calendar 객체 사용)
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1; // month는 0이 1월 11이 12월이다
	int cnt = 1;
	int cnt2;
	
//	월별 그래프, 일별 소비내역 출력을 위한 변수
	int[] t_sum = null;
	int[] i_sum = null;
	static String yearMonth = null;
//	====== 세부내역 뷰 ======
	
	// 세부내역 뷰 상단에 보여줄 테이블 
	String[] column = { "날짜", "구분", "항목", "금액", "메모" };
	DefaultTableModel model = new DefaultTableModel(column, 0);
	JTable table = new JTable(model);
	
	// 지출, 수입 라디오버튼	
	JRadioButton typeBtn1 = new JRadioButton("지출");
	JRadioButton typeBtn2 = new JRadioButton("수입");
	
	// 항목 콤보 박스 (식비, 교통비, ...)
	static String[] itemCombo = {"식비", "생필품", "교통비", "문화비", "통신비"};
	JComboBox combo = new JComboBox(itemCombo);
	
	// 머니필드 
	JTextField moneyField = new JTextField(10);
	JTextArea memoField = new JTextArea();
	
	// 추가, 수정, 삭제 버튼
	JButton addBtn = new JButton("추가");
	JButton updateBtn = new JButton("수정");
	JButton deleteBtn = new JButton("삭제");
	
	// 내역 입력에 필요한 변수
	VO vo = new VO();
	String writeDate = "";
	String type = "";
	String item = "";
	String memo = "";
	int money = 0;
	String[] p_field = new String[5];

//	가장 첫 화면인 캘린더를 보여주는 메소드
	
	public void calView() {
		JFrame window1 = new JFrame();
		JPanel nPanel = new JPanel();
		JPanel cPanel = new JPanel();
		JPanel sPanel = new JPanel();
		
		setTitle("Account Program");
		setBounds(400, 400, 800, 600);
		getContentPane().setBackground(new Color(249, 238, 236));
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		nPanel.setBounds(0, 15, 800, 50);
		nPanel.setLayout(new FlowLayout());
		nPanel.setBackground(new Color(249, 238, 236));
		
//		검색 연도와 월을 입력받는 칸을 만들고 기본적으로 올해의 이번 달을 출력하도록 함.
//		마우스로 클릭하면 입력할 수 있게 비워짐
		
		nPanel.add(name);
		name.setFont(new Font("D2Coding", Font.BOLD, 15));
		nPanel.add(searchY);
		searchY.setText(year + "");
		searchY.setFont(new Font("D2Coding", Font.PLAIN, 20));
		searchY.setHorizontalAlignment(JTextField.RIGHT);
		searchY.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                searchY.setText("");
            }
        });
		
		nPanel.add(new JLabel("년"));
		nPanel.add(searchM);
		searchM.setText(month + "");
		searchM.setFont(new Font("D2Coding", Font.BOLD, 20));
		searchM.setHorizontalAlignment(JTextField.RIGHT);
		searchM.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                searchM.setText("");
            }
        });
		
		nPanel.add(new JLabel("월"));
		nPanel.add(team);
		team.setFont(new Font("D2Coding", Font.PLAIN, 15));
		add(nPanel, BorderLayout.NORTH);
		
//		캘린더를 JButton에 출력하기 위한 코드
		
		cPanel.setBounds(120, 78, 550, 400);
		cPanel.setBackground(new Color(249, 238, 236));
		cPanel.setLayout(new GridLayout(7, 7));
//		연도와 월을 입력받는 JTextField에 액션리스너를 걸어 Enter를 누르면 캘린더가 바뀌게 함
		searchY.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("연도 입력됨");
				cnt = 1;
				year = Integer.parseInt(searchY.getText());
				month = Integer.parseInt(searchM.getText());
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DATE, 1);
				
				for (int i = 0; i < calBtn.length; i++) {
					cPanel.remove(calBtn[i]);
				}
				calPrint(cPanel);
				add(cPanel);
			}
		});
		
		searchM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("월 입력됨");
				cnt = 1;
				year = Integer.parseInt(searchY.getText());
				month = Integer.parseInt(searchM.getText());
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DATE, 1);
				
				for (int i = 0; i < calBtn.length; i++) {
					cPanel.remove(calBtn[i]);
				}
				
				calPrint(cPanel);
				add(cPanel);
			}
		});
		
		i_sum = DAO.itemSum(yearMonth, itemCombo);
		for(int i = 0; i < i_sum.length; i++) {
			System.out.println(i_sum[i]);
		}
		
		t_sum = DAO.typeSum(writeDate);
		for(int i = 0; i < t_sum.length; i++) {
			System.out.println(t_sum[i]);
		}
		
		calPrint(cPanel);
		add(cPanel);
		
		sPanel.setBounds(0, 505, 800, 50);
		sPanel.setLayout(new FlowLayout());
		sPanel.setBackground(new Color(249, 238, 236));
		
		JButton graph = new JButton("월별 그래프");
		graph.setBackground(Color.white);
		graph.setFont(new Font("D2Coding", Font.PLAIN, 15));

		sPanel.add(graph);
		add(sPanel);

		
		graph.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				graphview(month);
			}
		});
	
		setVisible(true);
	}

//	캘린더를 버튼 위에 출력해 주는 코드
	
	public void calPrint(JPanel cPanel) {
		for (int i = 0; i < 49; i++) {
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH)+1;
			cal.set(Calendar.DATE, 1);
			int startDay = cal.get(Calendar.DAY_OF_WEEK);
			int lastDay = cal.getActualMaximum(Calendar.DATE);
			calBtn[i] = new JButton();
			calBtn[i].setBackground(Color.white);
			calBtn[i].setFont(new Font("D2Coding", Font.PLAIN, 12));
//			일요일부터 토요일까지 요일을 첫 줄에 출력
			if (i < dayOfWeek.length) {
				calBtn[i].setText(dayOfWeek[i]);
				calBtn[i].setEnabled(false);
			}
//			그 달의 1일이 있는 요일 전까지를 빈 칸으로 출력하고 비활성화		
			if (i >= dayOfWeek.length && i < startDay + 6) {
				calBtn[i].setEnabled(false);
			}
//			그 달의 1일부터 마지막 일까지 숫자에 맞추어 출력
			if (i >= startDay + 6 && i < lastDay + startDay + 6) {
				int temp = cnt;
				calBtn[i].setText(cnt + "");
				calBtn[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String m = null, d = null;
						if (month < 10) {
							m = "0"+String.valueOf(month);
						}else {
							m = String.valueOf(month);
						}
						if (temp < 10) {
							d = "0"+String.valueOf(temp);
						}else {
							d = String.valueOf(temp);
						}
						writeDate = (year+"") + m + d;
						yearMonth = (year+"") + m;
						dayView(temp);
					}
				});
//				토요일은 파란색으로, 일요일은 빨간색으로 출력
				if(i % 7 == 6) {
					calBtn[i].setForeground(Color.BLUE);
				}
				if(i % 7 == 0) {
					calBtn[i].setForeground(Color.RED);
				}
				cnt++;
			}
//			그 달의 마지막 일부터 버튼 배열이 끝날 때까지 빈 칸으로 출력
			if (i >= lastDay + 13) {
				calBtn[i].setEnabled(false);
			}
			cPanel.add(calBtn[i]);
			
//			검색 시 다시 캘린더를 바꾸어 주어야 하므로 revalidate()를 사용한다.
			cPanel.revalidate();
			
//			오늘 날짜를 강조해준다.
			Calendar tempCal = Calendar.getInstance();
			if(year==tempCal.get(Calendar.YEAR) && 
					month==tempCal.get(Calendar.MONTH)+1 &&
					calBtn[i].getText().equals(String.valueOf(tempCal.get(Calendar.DATE)))) {
				calBtn[i].setBackground(new Color(249, 238, 236));
			}
		}
	}

//	캘린더에서 날짜를 눌렀을 때 나오는 팝업창으로, 추가/수정/삭제/목록보기가 가능
	
	public void dayView(int temp) {
//		해당 날짜를 temp로 받아 팝업창 가장 위에 출력함 
		cnt2 = temp;
		JFrame window2 = new JFrame();
		window2.setBounds(600, 400, 400, 600);
		window2.getContentPane().setBackground(new Color(249, 238, 236));

		window2.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel dayLabel = new JLabel(year + "년 " + month + "월 " + cnt2 + "일 ");
		dayLabel.setFont(new Font("D2Coding", Font.BOLD, 27));
		dayLabel.setBounds(85, 15, 250, 40);
		window2.add(dayLabel);
		
//		전역변수로 선언해준 테이블에 스크롤을 추가
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(40, 70, 305, 140);
		scroll.getViewport().setBackground(Color.white);
		window2.add(scroll);
		table.setFont(new Font("D2Coding", Font.PLAIN, 12));


//		라디오버튼을 그룹으로 설정
		ButtonGroup btnGroup = new ButtonGroup();
		
		btnGroup.add(typeBtn1);
		btnGroup.add(typeBtn2);

		typeBtn1.setBounds(100, 220, 80, 30);
		typeBtn1.setBackground(new Color(249, 238, 236));
		typeBtn1.setFont(new Font("D2Coding", Font.PLAIN, 12));
		typeBtn2.setBounds(240, 220, 80, 30);
		typeBtn2.setBackground(new Color(249, 238, 236));
		typeBtn2.setFont(new Font("D2Coding", Font.PLAIN, 12));
		window2.add(typeBtn1);
		window2.add(typeBtn2);

//		콤보박스에서 선택된 아이템을 전역변수 item에 대입
		JLabel itemLabel = new JLabel("항목");
		itemLabel.setBounds(40, 270, 30, 30);
		itemLabel.setFont(new Font("D2Coding", Font.PLAIN, 12));
		combo.setBounds(80, 270, 255, 30);
		combo.setBackground(Color.white);
		combo.setFont(new Font("D2Coding", Font.PLAIN, 12));
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				item = (String) combo.getSelectedItem();
			}
		});
		window2.add(itemLabel);
		window2.add(combo);

//		금액과 메모 JTextField를 추가하고 금액은 오른쪽으로 정렬 
		
		JLabel moneyLabel = new JLabel("금액");
		moneyLabel.setFont(new Font("D2Coding", Font.PLAIN, 12));
		JLabel memoLabel = new JLabel("메모");
		memoLabel.setFont(new Font("D2Coding", Font.PLAIN, 12));
		
		moneyLabel.setBounds(40, 310, 30, 30);
		moneyField.setBounds(80, 310, 255, 30);
		moneyField.setFont(new Font("D2Coding", Font.PLAIN, 14));
		moneyField.setHorizontalAlignment(JTextField.RIGHT);
		window2.add(moneyLabel);
		window2.add(moneyField);
		
		memoLabel.setBounds(40, 360, 30, 30);
		memoField.setBounds(80, 360, 253, 100);
		window2.add(memoLabel);
		window2.add(memoField);

//		추가, 수정, 삭제 버튼 추가하고 각 버튼에 액션리스너를 걸어준다.
		addBtn.setBounds(60, 480, 70, 50);
		addBtn.setBackground(Color.white);
		updateBtn.setBounds(160, 480, 70, 50);
		updateBtn.setBackground(Color.white);
		deleteBtn.setBounds(260, 480, 70, 50);
		deleteBtn.setBackground(Color.white);

		window2.add(addBtn);
		window2.add(updateBtn);
		window2.add(deleteBtn);

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

		window2.setVisible(true);
		
//		dayView() 화면을 기본값으로 세팅하여 보여주는 view() 메소드를 호출한다.
		view();
		
//		
	
		
//		내역 중 1건 클릭 시 내역을 각 필드에 다시 뿌려주는 setField() 메소드를 호출한다.
		setField();
		
	}
	
	// dayView() 화면을 기본값으로 세팅하여 보여주는 메소드
		public void view() {
			// 지출 버튼을 기본값으로 세팅
			typeBtn1.setSelected(true);
			
			// 지출 버튼이 눌리면 콤보박스를 활성화하고, 수입 버튼이 눌리면 콤보박스를 비활성화
			typeBtn1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					combo.setEnabled(true);
				}
			});
			typeBtn2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					combo.setEnabled(false);
				}
			});
			
//			콤보박스의 0번째 인덱스 값을 기본값으로 세팅
			combo.setSelectedIndex(0);

//			금액과 메모 JTextField를 비워줌
			moneyField.setText("");
			memoField.setText("");

//			날짜에 따른 데이터를 테이블로 보여주는 메소드
			ArrayList<VO> list = DAO.select(writeDate);
			
//			JTable에 데이터를 추가할 때 기존에 추가한 모든 데이터를 제거하지 않으면 기존 데이터 뒤에
//			새로 추가한 데이터가 보이기 때문에 기존의 데이터를 모두 제거한 후 추가해야 한다.
//			=> 마지막 데이터부터 제거하는 것이 편리하다.
//			getRowCount() : JTable 디자인에 사용한 DefaultTableModel 클래스 객체(model)에 저장된 데이터의 개수를 얻어온다.
			
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
//				removeRow(row) : JTable 디자인 에 사용한 DefaultTableModel 클래스 객체에서 넣어준 row번째 데이터를 제거한다.
				model.removeRow(i);
			}
			// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)

			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

			// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정

			renderer.setHorizontalAlignment(SwingConstants.CENTER);

			// 정렬할 테이블의 ColumnModel을 가져옴

			TableColumnModel t = table.getColumnModel();

			// 반복문을 이용하여 테이블을 가운데 정렬로 지정

			
				t.getColumn(0).setCellRenderer(renderer);
				t.getColumn(1).setCellRenderer(renderer);
				t.getColumn(2).setCellRenderer(renderer);
				t.getColumn(4).setCellRenderer(renderer);
				
			
//			테이블에 저장된 데이터가 5개의 필드를 가지고 있으므로, 5개의 데이터를 저장할 수 있는 문자열 배열을 만들고
//			ArrayList에 저장된 데이터를 넣어준다.
			String[] rowData = new String[5];
//			JTable에 ArrayList에 저장한 데이터의 개수만큼 반복하며 데이터를 넣어준다.
			for (VO data : list) {
				rowData[0] = data.getWriteDate();
				rowData[1] = data.getType();
				rowData[2] = data.getItem();
				rowData[3] = data.getMoney() + "";
				rowData[4] = data.getMemo();
//					JTable의 디자인에 사용한 DefaultTableModel 클래스 객체에 addRow() 메소드로 데이터를 넣어준다.
				model.addRow(rowData);
			}
		}
		

	// 추가, 수정, 삭제 버튼을 눌렀을 때 실행할 내용을 작성
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "추가":
//			dayView()에 있는 필드의 값을 한 번에 모아서 vo 객체로 받아 리턴하는 getField() 메소드 호출
			getField();
//			DAO 클래스의 insert(vo) 메소드 호출
			DAO.insert(vo);
//			입력 후 화면 초기화
			view();
			break;
		case "수정":
			getField();
//			DAO 클래스의 update(vo, p_field) 메소드 호출
			DAO.update(vo, p_field);
			view();
			break;
		case "삭제":
			getField();
//			DAO 클래스의 delete(vo) 메소드 호출
			DAO.delete(vo);
			view();
			break;
		}
	}

	// dayView()에 있는 필드의 값을 한 번에 모아서 vo 객체로 받아 리턴하는 메소드
	public VO getField() {
		if(typeBtn1.isSelected()) {
			type = typeBtn1.getText();
		}else {
			type = typeBtn2.getText();
		}
		money = Integer.parseInt(moneyField.getText().trim());
		memo = memoField.getText().trim();
		vo.setWriteDate(writeDate);
		vo.setType(type);
		vo.setItem(item);
		vo.setMoney(money);
		vo.setMemo(memo);
		return vo;
	}
	
	// 테이블에서 클릭한 내용을 dayView() 화면에 뿌려주고 각 필드의 내용을 전역변수로 받아주는 메소드
	public String[] setField() {
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
//				내역을 보여주는 테이블의 2번째 값(1번째 인덱스)이 "지출"일때 "지출" 버튼이 선택되도록, "수입"일때는 "수입" 버튼이 선택되도록 함
				if (table.getValueAt(row, 1).equals("지출")){
					typeBtn1.setSelected(true);
				}else {
					typeBtn2.setSelected(true);
				}
//				내역을 보여주는 테이블의 3번째 값(2번째 인덱스)이 콤보박스의 몇 번째 값과 일치하는지 확인하여
//				일치하는 값을 콤보박스의 기본값으로 세팅
				for (int i = 0; i < itemCombo.length; i++) {
					if (table.getValueAt(row, 2).equals(itemCombo[i])) {
						combo.setSelectedItem(itemCombo[i]);
					}
				}
//				내역을 보여주는 테이블의 4, 5번째 값(3, 4번째 인덱스)을 각각 금액과 메모 JTextField에 세팅
				moneyField.setText((String) table.getValueAt(row, 3));
				memoField.setText((String) table.getValueAt(row, 4));
				
//				새로 입력된 내역을 String 배열로 받아줌 >> update() 메소드를 호출할 때 인수로 넘겨줌
				p_field[0] = (String) table.getValueAt(row, 0);
				p_field[1] = (String) table.getValueAt(row, 1);
				p_field[2] = (String) table.getValueAt(row, 2);
				p_field[3] = (String) table.getValueAt(row, 3);
				p_field[4] = (String) table.getValueAt(row, 4);
			}
			
		});
		return null;
	}
	
	
	 private void graphview(int month) {
	     
         JFrame window3 = new JFrame("이번달 항목 비율 그래프");
         window3.setBounds(600, 400, 400, 600);
         window3.setPreferredSize(new Dimension(500, 350));
          Container contentPane = window3.getContentPane();
          DrawingPiePanel drawingPanel = new DrawingPiePanel();
          contentPane.add(drawingPanel, BorderLayout.CENTER);
          JPanel controlPanel = new JPanel();
          JTextField text1 = new JTextField(3);
          JTextField text2 = new JTextField(3);
          JTextField text3 = new JTextField(3);
          JTextField text4 = new JTextField(3);
        JTextField text5 = new JTextField(3);
          JButton button = new JButton("그래프 그리기");
          //항목 
          controlPanel.add(new JLabel("식비"));
          controlPanel.add(text1);
          controlPanel.add(new JLabel("교통비"));
          controlPanel.add(text2);
          controlPanel.add(new JLabel("생필품"));
          controlPanel.add(text3);
          controlPanel.add(new JLabel("문화비"));
          controlPanel.add(text4);
          controlPanel.add(new JLabel("통신비"));
          controlPanel.add(text5);
          
          controlPanel.add(button);
          //컨테이너에 컴포넌트 그룹 부착
          contentPane.add(controlPanel,BorderLayout.SOUTH);

          window3.pack();
          window3.setVisible(true);
          view();
	 }

	public static void main(String[] args) {
//		객체를 생성하고 첫 화면을 보여주는 메소드 호출
		ApProject ap = new ApProject();
		ap.calView();

	}

}
