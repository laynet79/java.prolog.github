package com.lthorup.prolog;

import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;

public class Console {

	private JEditorPane editor;
	
	public Console(JEditorPane editor) {
		this.editor = editor;
	}
	
	public void print(final String msg) {
		if (editor == null)
			System.out.printf(msg);
		else {
			SwingUtilities.invokeLater(new Runnable(){
			    public void run(){
					editor.setText(editor.getText() + msg);
					editor.setCaretPosition(editor.getText().length());
			    }
			}); 			
		}
	}
	
	public String readLine() {
		if (editor == null)
			return null;
		String text = editor.getText();
		int end = editor.getSelectionStart();
		int start = end-2;
		if (start < 0)
			start = 0;		
		while (start > 0 && text.charAt(start) != KeyEvent.VK_ENTER)
			start--;
		String line = text.substring(start, end);
		start = 0;
		end = line.length();
		char c = line.charAt(start);
		while (start < end && (c == '\n' || c == '\r' || c == '>')) {
			start++;
			c = line.charAt(start);
		}
		line = line.substring(start);
		return line;
	}
}
