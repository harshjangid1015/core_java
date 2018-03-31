package cs4120;

import java.util.ArrayList;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
/**
 * 2. (50 points) Q4_Mulario
Write a GUI program using JavaFX. The idea of the program is to allow the user to control a
circle on the screen. When the user presses an arrow key, the circle is to move in that direction.
The program is to have small circles at random locations and of random colors on the screen.
As the user moves their circle over a smaller circle, the smaller circle is absorbed into the user's
circle, increasing the size of the user's circle. The program is to have text fields for the user to
set the color of their circle by specifying red, green, and blue values, each in the range of 0 -
255. Also have a check box for whether to display text on the circle indicating the size of the
circle.*/

public class Q4_Mulario extends Application{
	Label lblStatus = new Label("");
	private ArrayList<Circle> nodes = new ArrayList<>();
	Pane pane = new Pane();
	Circle c = new Circle();
	TextField tfr = new TextField();
	TextField tfg = new TextField();
	TextField tfb = new TextField();
	HBox hBox = new HBox();
	Button colButton = new Button("set color");
	CheckBox cb = new CheckBox("Display Radius");

	@Override
	public void start(Stage primaryStage) {
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-border-color: blue");
//		StackPane stackPane = new StackPane();
//		stackPane.setStyle("-fx-border-color: green");
//		Pane pane = new Pane();
		pane.setStyle("-fx-border-color: red");
//		stackPane.getChildren().add(pane);
		
		
		
		borderPane.setTop(hBox);
		borderPane.setCenter(pane);
//		borderPane.setBottom(lblStatus);
		
		
//		hBox.getChildren().add(tf);
		
//		hBox.getChildren().add(colButton);
//		RadioButton rb = new RadioButton("Display Radius");
		
		
		cb.setOnAction(e -> {
			if(cb.isSelected()) {
				hBox.getChildren().add(lblStatus);
			}else {
				hBox.getChildren().remove(lblStatus);
			}
		});
		colButton.setOnAction(ev -> {
			int r = Integer.parseInt(tfr.getText());
			int g = Integer.parseInt(tfg.getText());
			int b = Integer.parseInt(tfb.getText());
			//c.setFill(Color.rgb(Double.parseDouble(tfr.getText()), Double.parseDouble(tfg.getText()), Double.parseDouble(tfb.getText()));
			c.setFill(Color.rgb(r, g, b));
			pane.requestFocus();
		});
		
				
		hBox.getChildren().addAll(tfr,tfg,tfb, colButton, cb);
		
		
		for(int i=0; i<20;i++) {
			Circle cir = new Circle();
			cir.setRadius(10+(Math.random()*40));
			cir.setCenterX(50+Math.random()*1150);
			cir.setCenterY(50+Math.random()*850);
			cir.setStroke(Color.RED);
			cir.setFill(Color.color(Math.random(), Math.random(), Math.random()));
			nodes.add(cir);
			pane.getChildren().add(cir);			
		}
				

//		Circle c = new Circle();
		c.setRadius(50);
		c.setCenterX(50+Math.random()*1150);
		c.setCenterY(50+Math.random()*850);
		c.setStroke(Color.BLACK);
		c.setFill(Color.GREEN);
		
		pane.getChildren().add(c);
				
		pane.setOnKeyPressed(e -> keyHandler(e, c));

		Scene scene = new Scene(borderPane, 1200, 900);
		primaryStage.setTitle("Mulario");
		primaryStage.setScene(scene);
		primaryStage.show();
		pane.requestFocus();
	
		tfr.setFocusTraversable(false);
		tfg.setFocusTraversable(false);
		tfb.setFocusTraversable(false);
		cb.setFocusTraversable(false);
		colButton.setFocusTraversable(false);
		hBox.setFocusTraversable(false);
	}
	
	
	public void keyHandler(KeyEvent e, Circle c) {
		switch(e.getCode()) {
		case UP:
//			pane.setStyle("-fx-border-color: red");
			pane.requestFocus();
			tfr.setFocusTraversable(false);
			tfg.setFocusTraversable(false);
			tfb.setFocusTraversable(false);
			cb.setFocusTraversable(false);
			colButton.setFocusTraversable(false);
			hBox.setFocusTraversable(false);
			c.setCenterX(c.getCenterX());
			c.setCenterY(c.getCenterY()-10);
			checkCircleIntersection(c);
			
			break;
		case DOWN:
			c.setCenterX(c.getCenterX());
			c.setCenterY(c.getCenterY()+10);
			checkCircleIntersection(c);
			break;
		case LEFT:
			c.setCenterX(c.getCenterX()-10);
			c.setCenterY(c.getCenterY());
			checkCircleIntersection(c);
			break;
		case RIGHT:
			c.setCenterX(c.getCenterX()+10);
			c.setCenterY(c.getCenterY());
			checkCircleIntersection(c);
			break;
		default:
			break;

		}
		
	}
	
	public void eatCircle(Circle c, Circle cir) {		
		Circle mainCircle = (Circle)c;
		Circle otherCircle = (Circle)cir;
		mainCircle.setRadius(mainCircle.getRadius() + 5);
		double newRadius = mainCircle.getRadius()+5;
		lblStatus.setText(String.format(" %02X", (int)newRadius));
//		lblStatus = "mainCircle.setRadius(mainCircle.getRadius() + 5)";
//		Platform.runLater(() ->{
//			nodes.remove(otherCircle);
//			pane.getChildren().remove(otherCircle);
//		});
		nodes.remove(otherCircle);
		pane.getChildren().remove(otherCircle);
	}
	
	public void checkCircleIntersection(Circle chk) {
		boolean collisionDetected = false;
		for (Circle static_circle : nodes) {
			if(static_circle != chk) {
				Shape intersect = Circle.intersect(chk, static_circle);
				if(intersect.getBoundsInLocal().getWidth() != -1) {
					collisionDetected = true;
				}
				if(collisionDetected) {
					eatCircle(chk, static_circle);
				}
			}			
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
