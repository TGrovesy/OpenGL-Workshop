package com.grovesy;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import static com.jogamp.opengl.GL2ES3.GL_COLOR;

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
import com.jogamp.opengl.util.FPSAnimator;

public class Task5 extends JFrame implements GLEventListener {

	private GLCanvas canvas;

	private int[] vertexArrayObject = new int[1];
	private int renderingProgram;
	
	//Animation stuff
	private float x = 0.0f; //pos of our shape
	private float inc = 0.01f; //offset for moving the shape

	public Task5() {
		setTitle("OpenGL");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		
		add(canvas);
		setVisible(true);
		
		FPSAnimator animtr = new FPSAnimator(canvas, 60); //60FPS
		animtr.start();
	}

	@Override
	public void display(GLAutoDrawable draw) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(renderingProgram);

		FloatBuffer buffer = Buffers.newDirectFloatBuffer(new float[] { 0.f, 0.f, 0.f, 1.f });
		gl.glClearBufferfv(GL_COLOR, 0, buffer);

		
		x+= inc;
		if(x >1.f) inc = -0.01f;
		if(x < -1.f) inc = 0.01f;
		int offsetLoc = gl.glGetUniformLocation(renderingProgram, "offset");
		gl.glProgramUniform1f(renderingProgram, offsetLoc, x);
		
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
		String[] vShaderSource = readShaderSource("Task 5/vertex.shader");
		String[] fShaderSource = readShaderSource("Task 5/fragment.shader");
		
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
		new Task5();
	}

}
