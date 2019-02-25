package com.grovesy;

import static com.jogamp.opengl.GL.GL_POINTS;
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

public class Main extends JFrame implements GLEventListener {

	private GLCanvas canvas;

	private int[] vertexArrayObject = new int[1];
	private int renderingProgram;

	public Main() {
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
		gl.glPointSize(30f);
		gl.glDrawArrays(GL_POINTS, 0, 1);
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
		String[] vShaderSource = { "#version 430 \n", "void main(void)\n",
				"{gl_Position = vec4(0.0,0.0,0.0,1.0);}\n", };
		String[] fShaderSource = { "#version 430 \n", "out vec4 colour; \n", "void main(void)\n",
				"{colour = vec4(0.0, 0.0, 1.0, 1.0);}", };
		
		int vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
		gl.glShaderSource(vertexShader, 3, vShaderSource, null, 0); //NOTE THE 3 lines of code we have matches our vShaderSource!
		gl.glCompileShader(vertexShader);
		
		int fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragmentShader, 4, fShaderSource, null, 0); //NOTE THE 4 lines of code we have matches our fShaderSource!
		gl.glCompileShader(fragmentShader);
		
		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vertexShader);
		gl.glAttachShader(vfprogram, fragmentShader);
		gl.glLinkProgram(vfprogram);
		
		gl.glDeleteShader(vertexShader);
		gl.glDeleteShader(fragmentShader);
		return vfprogram;
	}

	
	
	@Override
	public void reshape(GLAutoDrawable draw, int x, int y, int width, int height) {

	}

	public static void main(String[] args) {
		new Main();
	}

}
