package com.lthorup.prolog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class TheFrame extends JFrame {
	
	static final long serialVersionUID = 1;

	private JPanel contentPane;
	private JEditorPane editorView;
	private JEditorPane consoleView;
	private Prolog      prolog;
	private Console     console;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TheFrame frame = new TheFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TheFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 709, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Prolog Files", new String[] {"pro"}));
				if (fileChooser.showOpenDialog(editorView) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  try {
					  editorView.setText(readFile(file.getAbsolutePath()));
					  setTitle(file.getName());
				  }
				  catch(Exception error) {}
				  setTitle(file.getName());
				}
			}
		});
		toolBar.add(btnOpen);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Prolog Files", new String[] {"pro"}));
				if (fileChooser.showSaveDialog(editorView) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  try {
					  FileWriter f = new FileWriter(file.getAbsolutePath());
					  f.write(editorView.getText());
					  f.close();
				  }
				  catch(Exception error) {}
				}
			}
		});
		toolBar.add(btnSave);
		
		JButton btnConsult = new JButton("Consult");
		btnConsult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prolog.loadProgram(editorView.getText());
			}
		});
		toolBar.add(btnConsult);
		
		JButton btnAbort = new JButton("Abort");
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prolog.abort();
			}
		});
		toolBar.add(btnAbort);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		editorView = new JEditorPane();
		editorView.setFont(new Font("Arial", Font.PLAIN, 13));
		editorView.setCaretColor(Color.WHITE);
		editorView.setForeground(Color.GREEN);
		editorView.setBackground(Color.BLACK);
		scrollPane.setViewportView(editorView);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		consoleView = new JEditorPane();
		consoleView.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					String query = console.readLine();
					prolog.runQuery(query);
				}
			}
		});
		consoleView.setFont(new Font("Arial", Font.PLAIN, 13));
		consoleView.setCaretColor(Color.WHITE);
		consoleView.setForeground(Color.GREEN);
		consoleView.setBackground(Color.BLACK);
		scrollPane_1.setViewportView(consoleView);
		
		prolog = new Prolog();
		console = new Console(consoleView);
		prolog.setConsole(console);
	}

	String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
