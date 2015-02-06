package com.lyka.iams;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import connection.H2Connection;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class App {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection conn=null;
	private JTable table;
	public App() {
		initialize();
		conn = H2Connection.h2DbConnection();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 639, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "save clicked!");
			}
		});
		btnSave.setBounds(242, 349, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnShowDbContents = new JButton("Show DB Contents");
		btnShowDbContents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="select * from entry";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(buildTableModel(rs));

					rs.close();
					pst.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		btnShowDbContents.setBounds(50, 349, 132, 23);
		frame.getContentPane().add(btnShowDbContents);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(69, 27, 397, 242);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
	
	//TODO:Move to other class
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException{
		ResultSetMetaData metadata = rs.getMetaData();
		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metadata.getColumnCount();
		for(int column = 1; column <= columnCount; column++){
			columnNames.add(metadata.getColumnName(column));
		}
		
		// table data
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while(rs.next()){
			Vector<Object> vector = new Vector<Object>();
			for(int columnIndex = 1; columnIndex <= columnCount;columnIndex++){
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		
		return new DefaultTableModel(data,columnNames);
	}
}
