package com.lthorup.prolog;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {

	private JPanel      contentPane;
	private Prolog      prolog;
	private Console     console;
	private JEditorPane consoleView;
	private JEditorPane editorView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 689, 668);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton consultButton = new JButton("Consult");
		consultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prolog.loadProgram(editorView.getText());
			}
		});
		consultButton.setBounds(19, 17, 117, 29);
		contentPane.add(consultButton);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setBounds(19, 58, 658, 570);
		contentPane.add(splitPane);
		
		JScrollPane editorScrollPane = new JScrollPane();
		splitPane.setLeftComponent(editorScrollPane);
		
		editorView = new JEditorPane();
		editorView.setCaretColor(Color.WHITE);
		editorView.setBackground(Color.BLACK);
		editorView.setForeground(Color.GREEN);
		editorScrollPane.setViewportView(editorView);
		
		JScrollPane consoleScrollPane = new JScrollPane();
		splitPane.setRightComponent(consoleScrollPane);
		
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
		
		consoleView.setText("> ");
		consoleView.setForeground(Color.GREEN);
		consoleView.setBackground(Color.BLACK);
		consoleView.setCaretColor(Color.WHITE);
		consoleScrollPane.setViewportView(consoleView);
		prolog = new Prolog();
		console = new Console(consoleView);
		prolog.setConsole(console);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
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
		saveButton.setBounds(136, 17, 117, 29);
		contentPane.add(saveButton);
		
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		loadButton.setBounds(255, 17, 117, 29);
		contentPane.add(loadButton);
		
		JButton abortButton = new JButton("Abort");
		abortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prolog.abort();
			}
		});
		abortButton.setBounds(373, 17, 117, 29);
		contentPane.add(abortButton);
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
