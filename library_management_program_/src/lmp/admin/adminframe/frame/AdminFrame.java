package lmp.admin.adminframe.frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import lmp.admin.adminframe.button.MenuButton;
import lmp.admin.adminframe.button.OptionButton;
import lmp.admin.adminframe.button.listener.MenuButtonListener;
import lmp.admin.adminframe.button.listener.OptionButtonListener;
import lmp.admin.adminframe.frame.listener.AdminFrameWindowListener;
import lmp.admin.adminframe.panel.HomePanel;
import lmp.admin.adminframe.panel.MenuButtonPanel;
import lmp.admin.adminframe.panel.MenuCardPanel;
import lmp.admin.db.dao.SeatUseDetailDao;
import lmp.admin.db.vo.SeatUseDetailVO;
import lmp.admin.menu.book.BookMgmt;
import lmp.admin.menu.checkin_out.Member_Searching_Panel;
import lmp.admin.menu.employees.EmployeesMgmt;
import lmp.admin.menu.member.MemberMgmt;
import lmp.admin.menu.readingroom.ReadingRoomPanel;
import lmp.members.db.dao.ThemeDao;
import lmp.members.db.vo.ThemeVO;
import lmp.members.memberframe.label.ClockLabel;
import lmp.util.theme.Theme;

public class AdminFrame extends JFrame {

	JPanel panel = new JPanel();
	JScrollPane sp = new JScrollPane();
	
	private static MenuButtonPanel menuButtonPanel;
	private static MenuCardPanel menuCardPanel;
	
	private static MenuButton bookMgmtBtn;
	private static MenuButton checkInOutBtn;
	private static MenuButton readingroomBtn;
	private static MenuButton memberMgmtBTn;
	private static MenuButton employeeMgmtBtn;
	
	private static HomePanel homePanel;
	private static BookMgmt bookPanel;
	private static Member_Searching_Panel memberSearchPanel;
	private static ReadingRoomPanel readingroomPanel;
	private static MemberMgmt memberPanel;
	private static EmployeesMgmt employeePanel;
	
	private static ClockLabel clockLabel;
	
	private static OptionButton homeButton;
	private static OptionButton setupButton;
	
	private static ThemeDao themeDao;
	private static Theme theme = new Theme();
	
	public AdminFrame() throws SQLException {
		
		setLayout(null);
		setBounds(300, 100, 1200, 800);
		setTitle("????????? ??????");
		AdminFrame adminFrame = this;
		themeDao = new ThemeDao();
		
		// ?????? ?????? ????????? ?????? ??????
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				SeatUseDetailDao sDao = new SeatUseDetailDao();
				ArrayList<SeatUseDetailVO> sVo = new ArrayList<>();
				try {
					sVo.addAll(sDao.get());
					
					for (SeatUseDetailVO seat : sVo) {
						sDao.update(seat.getUse_id());
					}
				} catch (SQLException e) {}
			}
		};
		timer.schedule(task, date.getTime());
		
		
		menuButtonPanel = new MenuButtonPanel();
		menuCardPanel = new MenuCardPanel();
		
		homePanel = new HomePanel();
		bookPanel = new BookMgmt();
		memberSearchPanel = new Member_Searching_Panel();
		readingroomPanel = new ReadingRoomPanel();
		memberPanel = new MemberMgmt();
		employeePanel = new EmployeesMgmt();
		
		bookMgmtBtn = new MenuButton("book");
		checkInOutBtn = new MenuButton("barcode");
		readingroomBtn = new MenuButton("readingroom");
		memberMgmtBTn = new MenuButton("member");
		employeeMgmtBtn = new MenuButton("employee");
		
		homeButton = new OptionButton("home");
		setupButton = new OptionButton("setup");
		
		clockLabel = new ClockLabel();
		
		bookMgmtBtn.addActionListener(new MenuButtonListener(this));
		checkInOutBtn.addActionListener(new MenuButtonListener(this));
		readingroomBtn.addActionListener(new MenuButtonListener(this));
		memberMgmtBTn.addActionListener(new MenuButtonListener(this));
		employeeMgmtBtn.addActionListener(new MenuButtonListener(this));
		
		homeButton.addActionListener(new OptionButtonListener(this));
		setupButton.addActionListener(new OptionButtonListener(this));
		
		menuButtonPanel.add(bookMgmtBtn);
		menuButtonPanel.add(checkInOutBtn);
		menuButtonPanel.add(readingroomBtn);
		menuButtonPanel.add(memberMgmtBTn);
		menuButtonPanel.add(employeeMgmtBtn);
		
		menuCardPanel.add("??? ??????", homePanel);
		menuCardPanel.add("????????????", bookPanel);
		menuCardPanel.add("??????/?????? ??????", memberSearchPanel);
		menuCardPanel.add("????????? ??????", readingroomPanel);
		menuCardPanel.add("????????????", memberPanel);
		menuCardPanel.add("????????????", employeePanel);
		
		panel.add(clockLabel);
		panel.add(homeButton);
		panel.add(setupButton);
		panel.add(menuButtonPanel);
		panel.add(menuCardPanel);
		initialize();
		
		addWindowListener(new AdminFrameWindowListener());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(sp);
		setContentPane(sp);
		setVisible(true);
		
	}
	
	// ?????? ????????? ??? ?????? ?????? ????????? ??????
	public void initialize() throws SQLException {
		ThemeVO getTheme = themeDao.getTheme();
		theme.setTheme(getTheme.getName());
		
		Color sub1Color = theme.getSub1Color();
		
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(1900, 1000));
		panel.setBackground(theme.getMainColor());
		bookPanel.setBackground(sub1Color);
		memberSearchPanel.setBackground(sub1Color);
		readingroomPanel.setBackground(sub1Color);
//		readingroomPanel.getSeatListPanel().setBackground(sub1Color);
//		readingroomPanel.getSeatListPanel().setBorder(new LineBorder(sub1Color, 10));
//		readingroomPanel.getUsageListPanel().getUsageListScrollPane().setBackground(sub1Color);
		memberPanel.setBackground(sub1Color);
		employeePanel.setBackground(sub1Color);
		homePanel.setLabel(theme.getHomeImage());
		
		sp.setViewportView(panel);
		sp.getVerticalScrollBar().setUnitIncrement(16);
	}
	
	// ?????? ?????? ??? ?????? ?????????
	public static JButton getButton(String text) {
		 return new JButton() {
			 {
				setHorizontalTextPosition(CENTER);
				setVerticalTextPosition(BOTTOM);
				setForeground(Color.WHITE);
				setFont(new Font("?????? ???????????? Regular", Font.BOLD, 15));
				setText(text);
				if (!getText().equals("")) {
					setToolTipText(text);
				}
				setBorderPainted(false);
				setFocusPainted(false);
				setContentAreaFilled(false);
				addMouseListener(new MouseAdapter() {
					// ????????? ????????? ????????? ????????? ??????
					@Override
					public void mouseEntered(MouseEvent e) {
						Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
						setCursor(cursor);
					}
					// ???????????? ????????? ?????? ????????? ??????
					@Override
					public void mouseExited(MouseEvent e) {
						Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
						setCursor(cursor);
					}
				});
			 }
		 };
	}
	
	public static JTable getTable(DefaultTableModel model) {
		JTable table = new JTable(model);
		
		table.setFont(new Font("?????? ???????????? Regular", Font.PLAIN, 15));
		table.setRowHeight(25);
		// ????????? ?????? ?????? ????????? ??????
		table.getTableHeader().setReorderingAllowed(false);
		// ??????????????? ????????? ?????? ???????????? ??????
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		return table;
	}
	
	public MenuCardPanel getMenuCardPanel() {
		return menuCardPanel;
	}
	
}
