package com.grovesy;

import static com.jogamp.opengl.GL4.*;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class Task4 extends JFrame implements GLEventListener {

	private GLCanvas canvas;

	private int[] vertexArrayObject = new int[1];
	private int renderingProgram;

	public Task4() {
		setTitle("OpenGL");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		add(canvas);
		setVisible(true);
	}

	@Override
	public void display(GLAutoDrawable draw) {
		GL4 gl = (GL4) GLContext.getCurrentGL();

		FloatBuffer buffer = Buffers.newDirectFloatBuffer(new float[] { 0.f, 0.f, 0.f, 1.f });
		gl.glClearBufferfv(GL_COLOR, 0, buffer);

		gl.glUseProgram(renderingProgram);
		gl.glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	@Override
	public void dispose(GLAutoDrawable draw) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable draw) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		renderingProgram = createShaderProgram();
		gl.glGenVertexArrays(vertexArrayObject.length, vertexArrayObject, 0);
		gl.glBindVertexArray(vertexArrayObject[0]);
		
	}

	private int createShaderProgram() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		String[] vShaderSource = readShaderSource("Task 4/vertex.shader");
		String[] fShaderSource = readShaderSource("Task 4/fragment.shader");
		
		int vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
		gl.glShaderSource(vertexShader, vShaderSource.length, vShaderSource, null, 0); 
		gl.glCompileShader(vertexShader);
		
		int fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragmentShader, fShaderSource.length, fShaderSource, null, 0); 
		gl.glCompileShader(fragmentShader);
		
		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vertexShader);
		gl.glAttachShader(vfprogram, fragmentShader);
		gl.glLinkProgram(vfprogram);
		
		gl.glDeleteShader(vertexShader);
		gl.glDeleteShader(fragmentShader);  
		return vfprogram;
	}

	
	private String[] readShaderSource(String filename) {
		Vector<String> lines = new Vector<String>();
		Scanner sc;
		try {
			sc = new Scanner(new File(filename));
		}catch(IOException e) {
			System.out.println("Error Reading File!");
			return null;
		}
		
		while(sc.hasNext()) {
			lines.addElement(sc.nextLine());
		}
		
		String[] program = new String[lines.size()];
		for(int i = 0; i < lines.size(); i++) {
			program[i] = (String) lines.elementAt(i) + "\n";
		}
		
		return program;
	}
	
	
	@Override
	public void reshape(GLAutoDrawable draw, int x, int y, int width, int height) {

	}

	public static void main(String[] args) {
		new Task4();
	}

}
